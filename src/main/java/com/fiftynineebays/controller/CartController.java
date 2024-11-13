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

import com.fiftynineebays.domain.Cart;
import com.fiftynineebays.dto.CartDTO;
import com.fiftynineebays.dto.CartSearchDTO;
import com.fiftynineebays.dto.CartPageDTO;
import com.fiftynineebays.service.CartService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/cart")
@RestController
public class CartController {

	private final static Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cartService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Cart> getAll() {

		List<Cart> carts = cartService.findAll();
		
		return carts;	
	}

	@GetMapping(value = "/{cartId}")
	@ResponseBody
	public CartDTO getCart(@PathVariable Integer cartId) {
		
		return (cartService.getCartDTOById(cartId));
	}

 	@RequestMapping(value = "/addCart", method = RequestMethod.POST)
	public ResponseEntity<?> addCart(@RequestBody CartDTO cartDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = cartService.addCart(cartDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/carts")
	public ResponseEntity<CartPageDTO> getCarts(CartSearchDTO cartSearchDTO) {
 
		return cartService.getCarts(cartSearchDTO);
	}	

	@RequestMapping(value = "/updateCart", method = RequestMethod.POST)
	public ResponseEntity<?> updateCart(@RequestBody CartDTO cartDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = cartService.updateCart(cartDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
