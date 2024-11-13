package com.fiftynineebays.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.fiftynineebays.domain.Return;
import com.fiftynineebays.dto.ReturnDTO;
import com.fiftynineebays.dto.ReturnSearchDTO;
import com.fiftynineebays.dto.ReturnPageDTO;
import com.fiftynineebays.dto.ReturnConvertCriteriaDTO;
import com.fiftynineebays.service.GenericService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ReturnService extends GenericService<Return, Integer> {

	List<Return> findAll();

	ResultDTO addReturn(ReturnDTO returnDTO, RequestDTO requestDTO);

	ResultDTO updateReturn(ReturnDTO returnDTO, RequestDTO requestDTO);

    Page<Return> getAllReturns(Pageable pageable);

    Page<Return> getAllReturns(Specification<Return> spec, Pageable pageable);

	ResponseEntity<ReturnPageDTO> getReturns(ReturnSearchDTO returnSearchDTO);
	
	List<ReturnDTO> convertReturnsToReturnDTOs(List<Return> returns, ReturnConvertCriteriaDTO convertCriteria);

	ReturnDTO getReturnDTOById(Integer returnId);







}





