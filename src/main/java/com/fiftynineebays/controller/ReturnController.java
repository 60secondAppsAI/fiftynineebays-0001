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

import com.fiftynineebays.domain.Return;
import com.fiftynineebays.dto.ReturnDTO;
import com.fiftynineebays.dto.ReturnSearchDTO;
import com.fiftynineebays.dto.ReturnPageDTO;
import com.fiftynineebays.service.ReturnService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/return")
@RestController
public class ReturnController {

	private final static Logger logger = LoggerFactory.getLogger(ReturnController.class);

	@Autowired
	ReturnService returnService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Return> getAll() {

		List<Return> returns = returnService.findAll();
		
		return returns;	
	}

	@GetMapping(value = "/{returnId}")
	@ResponseBody
	public ReturnDTO getReturn(@PathVariable Integer returnId) {
		
		return (returnService.getReturnDTOById(returnId));
	}

 	@RequestMapping(value = "/addReturn", method = RequestMethod.POST)
	public ResponseEntity<?> addReturn(@RequestBody ReturnDTO returnDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = returnService.addReturn(returnDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/returns")
	public ResponseEntity<ReturnPageDTO> getReturns(ReturnSearchDTO returnSearchDTO) {
 
		return returnService.getReturns(returnSearchDTO);
	}	

	@RequestMapping(value = "/updateReturn", method = RequestMethod.POST)
	public ResponseEntity<?> updateReturn(@RequestBody ReturnDTO returnDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = returnService.updateReturn(returnDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
