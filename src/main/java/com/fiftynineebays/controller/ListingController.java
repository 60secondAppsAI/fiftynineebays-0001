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

import com.fiftynineebays.domain.Listing;
import com.fiftynineebays.dto.ListingDTO;
import com.fiftynineebays.dto.ListingSearchDTO;
import com.fiftynineebays.dto.ListingPageDTO;
import com.fiftynineebays.service.ListingService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/listing")
@RestController
public class ListingController {

	private final static Logger logger = LoggerFactory.getLogger(ListingController.class);

	@Autowired
	ListingService listingService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Listing> getAll() {

		List<Listing> listings = listingService.findAll();
		
		return listings;	
	}

	@GetMapping(value = "/{listingId}")
	@ResponseBody
	public ListingDTO getListing(@PathVariable Integer listingId) {
		
		return (listingService.getListingDTOById(listingId));
	}

 	@RequestMapping(value = "/addListing", method = RequestMethod.POST)
	public ResponseEntity<?> addListing(@RequestBody ListingDTO listingDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = listingService.addListing(listingDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/listings")
	public ResponseEntity<ListingPageDTO> getListings(ListingSearchDTO listingSearchDTO) {
 
		return listingService.getListings(listingSearchDTO);
	}	

	@RequestMapping(value = "/updateListing", method = RequestMethod.POST)
	public ResponseEntity<?> updateListing(@RequestBody ListingDTO listingDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = listingService.updateListing(listingDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
