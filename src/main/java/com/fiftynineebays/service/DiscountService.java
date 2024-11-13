package com.fiftynineebays.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.fiftynineebays.domain.Discount;
import com.fiftynineebays.dto.DiscountDTO;
import com.fiftynineebays.dto.DiscountSearchDTO;
import com.fiftynineebays.dto.DiscountPageDTO;
import com.fiftynineebays.dto.DiscountConvertCriteriaDTO;
import com.fiftynineebays.service.GenericService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DiscountService extends GenericService<Discount, Integer> {

	List<Discount> findAll();

	ResultDTO addDiscount(DiscountDTO discountDTO, RequestDTO requestDTO);

	ResultDTO updateDiscount(DiscountDTO discountDTO, RequestDTO requestDTO);

    Page<Discount> getAllDiscounts(Pageable pageable);

    Page<Discount> getAllDiscounts(Specification<Discount> spec, Pageable pageable);

	ResponseEntity<DiscountPageDTO> getDiscounts(DiscountSearchDTO discountSearchDTO);
	
	List<DiscountDTO> convertDiscountsToDiscountDTOs(List<Discount> discounts, DiscountConvertCriteriaDTO convertCriteria);

	DiscountDTO getDiscountDTOById(Integer discountId);







}





