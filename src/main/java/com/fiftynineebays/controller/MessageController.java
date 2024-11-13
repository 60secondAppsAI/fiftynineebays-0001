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

import com.fiftynineebays.domain.Message;
import com.fiftynineebays.dto.MessageDTO;
import com.fiftynineebays.dto.MessageSearchDTO;
import com.fiftynineebays.dto.MessagePageDTO;
import com.fiftynineebays.service.MessageService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/message")
@RestController
public class MessageController {

	private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	MessageService messageService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Message> getAll() {

		List<Message> messages = messageService.findAll();
		
		return messages;	
	}

	@GetMapping(value = "/{messageId}")
	@ResponseBody
	public MessageDTO getMessage(@PathVariable Integer messageId) {
		
		return (messageService.getMessageDTOById(messageId));
	}

 	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = messageService.addMessage(messageDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/messages")
	public ResponseEntity<MessagePageDTO> getMessages(MessageSearchDTO messageSearchDTO) {
 
		return messageService.getMessages(messageSearchDTO);
	}	

	@RequestMapping(value = "/updateMessage", method = RequestMethod.POST)
	public ResponseEntity<?> updateMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = messageService.updateMessage(messageDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
