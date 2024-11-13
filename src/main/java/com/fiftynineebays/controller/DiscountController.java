package com.fiftynineebays.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.fiftynineebays.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.fiftynineebays.domain.Discount;
import com.fiftynineebays.dto.DiscountDTO;
import com.fiftynineebays.dto.DiscountSearchDTO;
import com.fiftynineebays.dto.DiscountPageDTO;
import com.fiftynineebays.service.DiscountService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/discount")
@RestController
public class DiscountController {

	private final static Logger logger = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	DiscountService discountService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Discount> getAll() {

		List<Discount> discounts = discountService.findAll();
		
		return discounts;	
	}

	@GetMapping(value = "/{discountId}")
	@ResponseBody
	public DiscountDTO getDiscount(@PathVariable Integer discountId) {
		
		return (discountService.getDiscountDTOById(discountId));
	}

 	@RequestMapping(value = "/addDiscount", method = RequestMethod.POST)
	public ResponseEntity<?> addDiscount(@RequestBody DiscountDTO discountDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = discountService.addDiscount(discountDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/discounts")
	public ResponseEntity<DiscountPageDTO> getDiscounts(DiscountSearchDTO discountSearchDTO) {
 
		return discountService.getDiscounts(discountSearchDTO);
	}	

	@RequestMapping(value = "/updateDiscount", method = RequestMethod.POST)
	public ResponseEntity<?> updateDiscount(@RequestBody DiscountDTO discountDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = discountService.updateDiscount(discountDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
