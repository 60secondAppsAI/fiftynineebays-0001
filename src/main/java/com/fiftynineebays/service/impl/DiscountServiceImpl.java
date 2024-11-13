package com.fiftynineebays.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.service.GenericService;
import com.fiftynineebays.service.impl.GenericServiceImpl;
import com.fiftynineebays.dao.DiscountDAO;
import com.fiftynineebays.domain.Discount;
import com.fiftynineebays.dto.DiscountDTO;
import com.fiftynineebays.dto.DiscountSearchDTO;
import com.fiftynineebays.dto.DiscountPageDTO;
import com.fiftynineebays.dto.DiscountConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.DiscountService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class DiscountServiceImpl extends GenericServiceImpl<Discount, Integer> implements DiscountService {

    private final static Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

	@Autowired
	DiscountDAO discountDao;

	


	@Override
	public GenericDAO<Discount, Integer> getDAO() {
		return (GenericDAO<Discount, Integer>) discountDao;
	}
	
	public List<Discount> findAll () {
		List<Discount> discounts = discountDao.findAll();
		
		return discounts;	
		
	}

	public ResultDTO addDiscount(DiscountDTO discountDTO, RequestDTO requestDTO) {

		Discount discount = new Discount();

		discount.setDiscountId(discountDTO.getDiscountId());


		discount.setDiscountAmount(discountDTO.getDiscountAmount());


		discount.setDiscountCode(discountDTO.getDiscountCode());


		discount.setExpirationDate(discountDTO.getExpirationDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		discount = discountDao.save(discount);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Discount> getAllDiscounts(Pageable pageable) {
		return discountDao.findAll(pageable);
	}

	public Page<Discount> getAllDiscounts(Specification<Discount> spec, Pageable pageable) {
		return discountDao.findAll(spec, pageable);
	}

	public ResponseEntity<DiscountPageDTO> getDiscounts(DiscountSearchDTO discountSearchDTO) {
	
			Integer discountId = discountSearchDTO.getDiscountId(); 
  			String discountCode = discountSearchDTO.getDiscountCode(); 
   			String sortBy = discountSearchDTO.getSortBy();
			String sortOrder = discountSearchDTO.getSortOrder();
			String searchQuery = discountSearchDTO.getSearchQuery();
			Integer page = discountSearchDTO.getPage();
			Integer size = discountSearchDTO.getSize();

	        Specification<Discount> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, discountId, "discountId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, discountCode, "discountCode"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("discountCode")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Discount> discounts = this.getAllDiscounts(spec, pageable);
		
		//System.out.println(String.valueOf(discounts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(discounts.getTotalPages()));
		
		List<Discount> discountsList = discounts.getContent();
		
		DiscountConvertCriteriaDTO convertCriteria = new DiscountConvertCriteriaDTO();
		List<DiscountDTO> discountDTOs = this.convertDiscountsToDiscountDTOs(discountsList,convertCriteria);
		
		DiscountPageDTO discountPageDTO = new DiscountPageDTO();
		discountPageDTO.setDiscounts(discountDTOs);
		discountPageDTO.setTotalElements(discounts.getTotalElements());
		return ResponseEntity.ok(discountPageDTO);
	}

	public List<DiscountDTO> convertDiscountsToDiscountDTOs(List<Discount> discounts, DiscountConvertCriteriaDTO convertCriteria) {
		
		List<DiscountDTO> discountDTOs = new ArrayList<DiscountDTO>();
		
		for (Discount discount : discounts) {
			discountDTOs.add(convertDiscountToDiscountDTO(discount,convertCriteria));
		}
		
		return discountDTOs;

	}
	
	public DiscountDTO convertDiscountToDiscountDTO(Discount discount, DiscountConvertCriteriaDTO convertCriteria) {
		
		DiscountDTO discountDTO = new DiscountDTO();
		
		discountDTO.setDiscountId(discount.getDiscountId());

	
		discountDTO.setDiscountAmount(discount.getDiscountAmount());

	
		discountDTO.setDiscountCode(discount.getDiscountCode());

	
		discountDTO.setExpirationDate(discount.getExpirationDate());

	

		
		return discountDTO;
	}

	public ResultDTO updateDiscount(DiscountDTO discountDTO, RequestDTO requestDTO) {
		
		Discount discount = discountDao.getById(discountDTO.getDiscountId());

		discount.setDiscountId(ControllerUtils.setValue(discount.getDiscountId(), discountDTO.getDiscountId()));

		discount.setDiscountAmount(ControllerUtils.setValue(discount.getDiscountAmount(), discountDTO.getDiscountAmount()));

		discount.setDiscountCode(ControllerUtils.setValue(discount.getDiscountCode(), discountDTO.getDiscountCode()));

		discount.setExpirationDate(ControllerUtils.setValue(discount.getExpirationDate(), discountDTO.getExpirationDate()));



        discount = discountDao.save(discount);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DiscountDTO getDiscountDTOById(Integer discountId) {
	
		Discount discount = discountDao.getById(discountId);
			
		
		DiscountConvertCriteriaDTO convertCriteria = new DiscountConvertCriteriaDTO();
		return(this.convertDiscountToDiscountDTO(discount,convertCriteria));
	}







}
