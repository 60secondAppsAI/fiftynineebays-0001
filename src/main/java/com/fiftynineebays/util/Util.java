package com.fiftynineebays.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiftynineebays.dto.common.FilterDTO;
import com.fiftynineebays.dto.common.SortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Utility class to handle common functionalities across the application.
 */
public class Util {

    public static final double R = 6372.8; // In kilometers

    private static final String[] dateFormats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "MM/dd/yyyy"};

    // the logger
    private final static Logger logger = LoggerFactory.getLogger(Util.class);


    /**
     * Captures the date part of a time stamp.
     *
     * @param input the input time stamp.
     * @return the Date part of the time stamp.
     */
    public static Date getDateFromTimeStamp(String input) {

        Date result = null;

        if (input == null || input.isEmpty())
            return result;

        logger.debug("Input date {}", input);

        for (String df : dateFormats) {

            DateFormat format = new SimpleDateFormat(df);

            try {
                result = format.parse(input);
                break;
            } catch (ParseException e) {
                // Ignoring this exception.
            }
        }

        if (result == null)
            logger.error("Could Not parse Date {} using formats {}", input, dateFormats);

        logger.debug("result: {}", result);

        return result;
    }


    /**
     * Converts from one unit to another using the conversion factor
     * and the operation to perform. The default operation is DIVIDE.
     *
     * @param input            the input value to convert.
     * @param conversionFactor the conversion factor.
     * @param operation        the operation to perform. Supported values are DIVIDE and MULTIPLY.
     * @return Integer the rounded result value.
     */
    public static Integer convertUnits(double input, Double conversionFactor,
                                       String operation) {
        Integer result = 0;

        if (operation == null || operation.isEmpty())
            operation = "DIVIDE";

        switch (operation) {
            case "DIVIDE":
                result = (int) Math.round(input / conversionFactor);
                break;
            case "MULTIPLY":
                result = (int) Math.round(input * conversionFactor);
                break;
            default:
                break;
        }

        return result;
    }


    /**
     * Splits an equal and not equal filter into less than and greater than filters.
     *
     * @param filterDTO         the filter dto to split.
     * @param additionalFilters the list of additional filters to add the new filter to.
     */
    public static void splitEqualFilter(FilterDTO filterDTO, List<FilterDTO> additionalFilters) {
        FilterDTO filter = null;

        if (filterDTO.getOperator().equalsIgnoreCase("eq")) {
            filterDTO.setOperator("gte");
            filterDTO.setLogic("and");

            filter = new FilterDTO(null,
                    filterDTO.getField(), "lte",
                    filterDTO.getOriginalValue(), filterDTO.getFilters());

            filter.setConverter(filterDTO.getConverter());
        }

        if (filterDTO.getOperator().equalsIgnoreCase("neq")) {
            filterDTO.setOperator("gt");
            filterDTO.setLogic("or");

            filter = new FilterDTO(null,
                    filterDTO.getField(), "lt",
                    filterDTO.getOriginalValue(), filterDTO.getFilters());

            filter.setConverter(filterDTO.getConverter());

        }

        if (filter != null)
            additionalFilters.add(filter);

    }


    /**
     * Splits an address filter into a city and state filter.
     * The addresses in the incoming filter are represented by <City>, <State>
     *
     * @param filterDTO         the filter dto to split.
     * @param fields            String array of names of model attributes representing the city and state.
     * @param additionalFilters the list of additional filters to add the new filter to.
     */
    public static void splitAddressFilter(FilterDTO filterDTO, String[] fields,
                                          List<FilterDTO> additionalFilters) {
        FilterDTO filter = null;

        String address = filterDTO.getValue();

        if (address == null || address.isEmpty())
            return;

        String[] addressSplit = address.split(",");

        // 0 is city, 1 is state
        filterDTO.setField(fields[0]);
        filterDTO.setValue(addressSplit[0].trim());

        if (addressSplit.length > 1) {
            filter = new FilterDTO(null,
                    fields[1], filterDTO.getOperator(),
                    addressSplit[1].trim(), filterDTO.getFilters());
        }

        if (filter != null)
            additionalFilters.add(filter);

    }


    /**
     * Splits an address sort into a city and state sort.
     *
     * @param sortDTO         the sort dto to split.
     * @param fields          String array of names of model attributes representing the city and state.
     * @param additionalSorts the list of additional sort to add the new sort to.
     */
    public static void splitAddressSort(SortDTO sortDTO, String[] fields,
                                        List<SortDTO> additionalSorts) {
        SortDTO sort = null;

        // 0 is city, 1 is state
        sortDTO.setField(fields[0]);

        sort = new SortDTO(fields[1], sortDTO.getDir());
        additionalSorts.add(sort);

    }

    /**
     * Gets the distance between 2 addresses based on their lat/lng values using the
     * Haversine formula.
     *
     * @param lat1 the latitude of the first address.
     * @param lng1 the longitude of the first address.
     * @param lat2 the latitude of the second address.
     * @param lng2 the longitude of the second address.
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a>
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }


    /**
     * Creates a sub filter by selecting an existing filter from the filter list based
     * on the source field name and updating the field name based on target field name.
     *
     * @param srcFieldName    the source field name.
     * @param targetFieldName the target field name.
     * @param filterList      the filter list to get the source field from.
     * @return FilterDTO the sub filter.
     */
    public static FilterDTO getSubFilter(String srcFieldName, String targetFieldName,
                                         List<FilterDTO> filterList) {
        FilterDTO filterDTO = null;

        if (filterList == null)
            return filterDTO;

        for (FilterDTO filter : filterList) {
            if (filter.getField() != null && filter.getField().equals(srcFieldName)) {
                filterDTO = new FilterDTO(filter.getLogic(), filter.getField(), filter.getOperator(),
                        filter.getValue(), filter.getFilters());
                filterDTO.setField(targetFieldName);

                return filterDTO;
            }

            filterDTO = getSubFilter(srcFieldName, targetFieldName, filter.getFilters());

            if (filterDTO != null)
                break;
        }

        return filterDTO;

    }


    /**
     * Creates a sub sort by selecting an existing sort from the sort list based
     * on the source field name and updating the field name based on target field name.
     *
     * @param srcFieldName    the source field name.
     * @param targetFieldName the target field name.
     * @param sortList        the sort list to get the source field from.
     * @return SortDTO the sub sort.
     */
    public static SortDTO getSubSort(String srcFieldName, String targetFieldName,
                                     List<SortDTO> sortList) {
        SortDTO sortDTO = null;

        if (sortList == null)
            return sortDTO;

        for (SortDTO sort : sortList) {
            if (sort.getField() != null && sort.getField().equals(srcFieldName)) {
                sortDTO = new SortDTO(sort.getField(), sort.getDir());
                sortDTO.setField(targetFieldName);

                break;
            }
        }

        return sortDTO;

    }

    /**
     * Add default time limits on a date field.
     *
     * @param FilterDTO filterDTO the filter to set the time limits on.
     */
    public static void addDefaultTimelimits(FilterDTO filterDTO) {

        if (filterDTO.getOperator() == null)
            return;

        if (filterDTO.getOperator().equals("gte"))
            filterDTO.setValue(filterDTO.getValue() + " 00:00:00");

        if (filterDTO.getOperator().equals("lte"))
            filterDTO.setValue(filterDTO.getValue() + " 23:59:59");
    }


    /**
     * Checks if a filter is present in the filter list or not.
     *
     * @param filterName the filter name.
     * @param filters    the list of filters to search on.
     * @return true/false based on whether filter is present in list or not.
     */
    public static boolean isFilterInList(String filterName, List<FilterDTO> filters) {

        boolean result = false;

        for (FilterDTO filter : filters) {
            if (filter.getFilters() != null && filter.getFilters().size() > 0) {
                result = isFilterInList(filterName, filter.getFilters());
                if (result)
                    return result;
            } else {
                List<FilterDTO> foundFilters = filters.stream().filter(f -> (f.getField() != null &&
                        f.getField().equalsIgnoreCase(filterName))).collect(Collectors.toList());

                if (foundFilters != null && foundFilters.size() > 0)
                    return true;
            }
        }

        return result;
    }


    /**
     * Converts an object to a map.
     *
     * @param object the source object.
     * @return the converted Map.
     */
    public static Map convertObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(object, HashMap.class);

    }


    /**
     * Returns a composite OR filter by combining first and second filters.
     *
     * @param firstFilter  the first filter
     * @param secondFilter the second filter
     * @return the composite OR filter.
     */
    public static FilterDTO getCompositeOrFilter(FilterDTO firstFilter, FilterDTO secondFilter) {
        List<FilterDTO> filters1 = new ArrayList<FilterDTO>();

        // add first filter
        ControllerUtils.addTopLevelLogic(firstFilter.getLogic(), firstFilter.getField(), firstFilter.getOperator(),
                firstFilter.getValue(), firstFilter.getJoinType(), filters1);

        FilterDTO filter1 = new FilterDTO("and", null, null, null, filters1);


        List<FilterDTO> filters2 = new ArrayList<FilterDTO>();

        // add second filter
        ControllerUtils.addTopLevelLogic(secondFilter.getLogic(), secondFilter.getField(), secondFilter.getOperator(),
                secondFilter.getValue(), secondFilter.getJoinType(), filters2);

        FilterDTO filter2 = new FilterDTO("and", null, null, null, filters2);


        List<FilterDTO> resultFilters = new ArrayList<FilterDTO>();
        resultFilters.add(filter1);
        resultFilters.add(filter2);

        FilterDTO resultFilter = new FilterDTO("or", null, null, null, resultFilters);

        return resultFilter;
    }

    public static BigDecimal zeroIfNull(BigDecimal value) {
        value = value == null ? BigDecimal.ZERO : value;
        return value;
    }

    public static Boolean isNotEqual(BigDecimal one, BigDecimal two) {
        one = zeroIfNull(one);
        two = zeroIfNull(two);
        return one.compareTo(two) != 0;
    }

    public static Boolean isGreater(BigDecimal one, BigDecimal two) {
        one = zeroIfNull(one);
        two = zeroIfNull(two);
        return one.compareTo(two) == 1;
    }

    public static Boolean isLess(BigDecimal one, BigDecimal two) {
        one = zeroIfNull(one);
        two = zeroIfNull(two);
        return one.compareTo(two) == -1;
    }

    public static Boolean isEqual(BigDecimal one, BigDecimal two) {
        one = zeroIfNull(one);
        two = zeroIfNull(two);
        return one.compareTo(two) == 0;
    }

}
