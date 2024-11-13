package com.fiftynineebays.util;


import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fiftynineebays.dto.ColumnDTO;
import com.fiftynineebays.dto.common.FilterDTO;
import com.fiftynineebays.dto.common.SortDTO;

/**
 *
 * This class has helper methods generally used by Spring Controllers
 */
public class ControllerUtils {

    private final static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);

	public static List<SortDTO> parseSortParams(HttpServletRequest request) {
		List<SortDTO> sortList = new ArrayList<SortDTO>();

		boolean loop = true;

		for (int i = 0; loop == true; i++) {
			String paramName = "sort[" + i + "][field]";
			String[] fieldValues = request.getParameterValues(paramName);

			if (fieldValues != null && fieldValues.length > 0) {
				paramName = "sort[" + i + "][dir]";
				String[] dirValues = request.getParameterValues(paramName);

				if (dirValues != null) {
					SortDTO sort = new SortDTO(fieldValues[0]);
					sort.setField(fieldValues[0]);
					sort.setDir(dirValues[0]);

					sortList.add(sort);
				}
			} else {
				loop = false;
			}
		}

		return sortList;
	}

	/**
	 * 
	 * This method takes in a request object that includes nested filter parameters with and/or logic.  
	 * It constructions a List<FilterDTO> object that contains all the criteria by level/logic;  
	 * The List<FilterDTO> returned should always contain only 1 FilterDTO element.  criteria are found in the filters element of that object; 
	 * 
	 * */
	public static List<FilterDTO> parseFilterParams(HttpServletRequest request, String paramNameBase) {
		
		//this is the filterDTO we'll return. it's only one, since things get nested.  
		FilterDTO firstFilter = new FilterDTO();

		//the first level sets Logic, and Filters. 
		String logic =  request.getParameter(paramNameBase + "[logic]");
		if(logic == null)
			logic = "and";
		firstFilter.setLogic(logic);
		
		
		//used to break out of the loop once we recognize that we're done with the filters
		boolean loop = true;
		

		//this list keeps track of the filters that we'll add to the FilterDTO;  
		List<FilterDTO> filterList = new ArrayList<FilterDTO>();

		
		//now we'll start looping through the [filters] element and build the filterList.
		for (int i=0; loop == true; i++) {
			
			//first, we'll check if this filter contains more logic (i.e. it's a nested filter)
			String childLogicCheck = request.getParameter(paramNameBase + "[filters][" + i + "][logic]");
			
			//if there's another level of logic, we'll call the current method recursively to build the filterList
			if(childLogicCheck != null && !childLogicCheck.equals("")) {
				
				//recursive call to get filterList;
				List<FilterDTO> childFilterList = parseFilterParams(request, paramNameBase + "[filters][" + i + "]");
				
				//now that we've got the list of filters for this level, add it to the filterList we've been building.
				firstFilter.getFilters().addAll(childFilterList);
			}
			else {  //no more nested filters for this element of the filters[] array, so build the list of filters for this level.
			
				FilterDTO filter = new FilterDTO();  
				
				String field = request.getParameter(paramNameBase + "[filters][" + i + "][field]");
	
				if (field != null) {  //check to make sure we have a value here, otherwise, we're at the end of the array, so we'll break out.
					
					//now we'll build a simple FilterDTO Object.   //don't set logic on this one.  it just contains the filter criteria.  
					String operator = request.getParameter(paramNameBase + "[filters][" + i + "][operator]");
					String value = request.getParameter(paramNameBase + "[filters][" + i + "][value]");
	
					if (field != null && operator != null && value != null) {
	
						filter.setField(field);
						filter.setOperator(operator);
						filter.setValue(value);
	
						filterList.add(filter); //add this filter to the filterList we're building for this level;
					}
				} else {
					loop = false;  //all done with this filters[] array so break out of the loop;
				}
			}
		}//for loop
		
		//now we're done building the filters for this level of the filters[] array, so add the filterList to the first FilterDTO's filter list.
		firstFilter.getFilters().addAll(filterList);

		//get a List we can return from this method.  
		List<FilterDTO> filterDTOList = new ArrayList<>();
		filterDTOList.add(firstFilter);
		
		return filterDTOList;
	}
	

    /**
     * 
     * wraps an existing filterList with AND logic for the given field, operator, value.  
     * 
     * e.g.  given filter = firstName=john OR lastName=john
     *       returned filter = companyId=2 AND (firstName=john OR lastName=john)
     * 
     * */
    public static List<FilterDTO> addTopLevelLogic(String logic, String field, String operator, String value, List<FilterDTO> filterList){
    	
    	List<FilterDTO> filterDTOList = new ArrayList<>();

    	FilterDTO topLevelLogicFilter = new FilterDTO(logic, null, null, null, filterList);

    	FilterDTO topLevelFilter = new FilterDTO(null, field, operator, value, null);
		topLevelLogicFilter.getFilters().add(topLevelFilter);
		
		filterDTOList.add(topLevelLogicFilter);
		
    	return filterDTOList;
    	
    }
    
    
    public static List<FilterDTO> addTopLevelLogic(String logic, String field, String operator, String value, 
    		Optional<String> joinType, List<FilterDTO> filterList){
    	
    	List<FilterDTO> filterDTOList = new ArrayList<>();

    	FilterDTO topLevelLogicFilter = new FilterDTO(logic, null, null, null, filterList);

    	FilterDTO topLevelFilter = new FilterDTO(null, field, operator, value, null);
    	topLevelFilter.setJoinType(joinType);
		topLevelLogicFilter.getFilters().add(topLevelFilter);
		
		filterDTOList.add(topLevelLogicFilter);
		
    	return filterDTOList;
    	
    }
    
    
    /**
     * This parses out the selected columns to export to excel; 
     * 
     * */
	public static List<ColumnDTO> parseSelectedColumnsParams(HttpServletRequest request) {
		List<ColumnDTO> selectedColumnsList = new ArrayList<ColumnDTO>();

		boolean loop = true;

		for (int i = 0; loop == true; i++) {
			String paramName = "selectedColumn[" + i + "][hidden]";
			String[] hiddenValues = request.getParameterValues(paramName);

			if (hiddenValues != null && hiddenValues.length > 0 && hiddenValues[0].equals("false")) {
				ColumnDTO column = new ColumnDTO();
				column.setHidden(hiddenValues[0]);
				
				paramName = "selectedColumn[" + i + "][title]";
				String[] titleValues = request.getParameterValues(paramName);
				if (titleValues != null)
					column.setTitle(titleValues[0]);

				paramName = "selectedColumn[" + i + "][field]";
				String[] fieldValues = request.getParameterValues(paramName);
				if (fieldValues != null)
					column.setField(fieldValues[0]);

				selectedColumnsList.add(column);
			
			} else {
				loop = false;
			}
		}

		return selectedColumnsList;
	}

	public static ResponseEntity<byte[]> getExcelFileResponseEntity(String key, String fileName, HttpSession session) {
 		
    	logger.debug("Attempting to download " + fileName + " with UUID: " + key);
 		
    	byte[] csvFile = null;
 		
 		csvFile = (byte[]) session.getAttribute(key);	// get the file from session using the key
 		
 		HttpHeaders header = new HttpHeaders();
 		header.set("charset", "utf-8");
 		header.setContentType(MediaType.valueOf("text/csv"));
 		
 		if(csvFile == null) {
 			logger.debug("Could not find File with UUID: "+ key + " Not Found");
 			header.setContentLength(0);
 		}
 		else {
 			logger.debug("Found file with UUID: "+ key);
 			header.setContentLength(csvFile.length);
 		}
 		
 		header.set("Content-disposition", "attachment; filename=" + fileName);
 		
		return new ResponseEntity<byte[]>(csvFile, header, HttpStatus.OK);
	}
	
    public static Specification andIfNecessary(Specification spec, Integer fieldValue, String fieldName) {
   		if (fieldValue != null) {
	        spec = spec.and((root, query, cb) -> cb.equal(root.get(fieldName), fieldValue));
	    }
   		return spec;
    }
    
    public static Specification andIfNecessary(Specification spec, String fieldValue, String fieldName) {
		if (fieldValue != null && !false) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(fieldName)), "%" + fieldValue.toLowerCase() + "%"));
		}
   		return spec;
    }

    public static Double setValue(Double currentValue, Double newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}

    public static Integer setValue(Integer currentValue, Integer newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}
	
	public static Date setValue(Date currentValue, Date newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}

	public static Timestamp setValue(Timestamp currentValue, Timestamp newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}

	public static Year setValue(Year currentValue, Year newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}

	public static String setValue(String currentValue, String newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}

	public static Boolean setValue(Boolean currentValue, Boolean newValue) {
		if (currentValue != null) {
			if (!currentValue.equals(newValue)) {
				//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
				return newValue;
			}
		}
		//call AFTER creating a list of auditDTO's to add, auditService.add(project.DOMAIN_CLASS_ID, project.getProjectId(), projectDTO.getProjectId());
		return newValue;
	}
}