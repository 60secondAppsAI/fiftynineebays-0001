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
import com.fiftynineebays.dao.TransactionDAO;
import com.fiftynineebays.domain.Transaction;
import com.fiftynineebays.dto.TransactionDTO;
import com.fiftynineebays.dto.TransactionSearchDTO;
import com.fiftynineebays.dto.TransactionPageDTO;
import com.fiftynineebays.dto.TransactionConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.TransactionService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class TransactionServiceImpl extends GenericServiceImpl<Transaction, Integer> implements TransactionService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	TransactionDAO transactionDao;

	


	@Override
	public GenericDAO<Transaction, Integer> getDAO() {
		return (GenericDAO<Transaction, Integer>) transactionDao;
	}
	
	public List<Transaction> findAll () {
		List<Transaction> transactions = transactionDao.findAll();
		
		return transactions;	
		
	}

	public ResultDTO addTransaction(TransactionDTO transactionDTO, RequestDTO requestDTO) {

		Transaction transaction = new Transaction();

		transaction.setTransactionId(transactionDTO.getTransactionId());


		transaction.setTransactionDate(transactionDTO.getTransactionDate());


		transaction.setTransactionStatus(transactionDTO.getTransactionStatus());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		transaction = transactionDao.save(transaction);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Transaction> getAllTransactions(Pageable pageable) {
		return transactionDao.findAll(pageable);
	}

	public Page<Transaction> getAllTransactions(Specification<Transaction> spec, Pageable pageable) {
		return transactionDao.findAll(spec, pageable);
	}

	public ResponseEntity<TransactionPageDTO> getTransactions(TransactionSearchDTO transactionSearchDTO) {
	
			Integer transactionId = transactionSearchDTO.getTransactionId(); 
   			String transactionStatus = transactionSearchDTO.getTransactionStatus(); 
 			String sortBy = transactionSearchDTO.getSortBy();
			String sortOrder = transactionSearchDTO.getSortOrder();
			String searchQuery = transactionSearchDTO.getSearchQuery();
			Integer page = transactionSearchDTO.getPage();
			Integer size = transactionSearchDTO.getSize();

	        Specification<Transaction> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, transactionId, "transactionId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, transactionStatus, "transactionStatus"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("transactionStatus")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Transaction> transactions = this.getAllTransactions(spec, pageable);
		
		//System.out.println(String.valueOf(transactions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(transactions.getTotalPages()));
		
		List<Transaction> transactionsList = transactions.getContent();
		
		TransactionConvertCriteriaDTO convertCriteria = new TransactionConvertCriteriaDTO();
		List<TransactionDTO> transactionDTOs = this.convertTransactionsToTransactionDTOs(transactionsList,convertCriteria);
		
		TransactionPageDTO transactionPageDTO = new TransactionPageDTO();
		transactionPageDTO.setTransactions(transactionDTOs);
		transactionPageDTO.setTotalElements(transactions.getTotalElements());
		return ResponseEntity.ok(transactionPageDTO);
	}

	public List<TransactionDTO> convertTransactionsToTransactionDTOs(List<Transaction> transactions, TransactionConvertCriteriaDTO convertCriteria) {
		
		List<TransactionDTO> transactionDTOs = new ArrayList<TransactionDTO>();
		
		for (Transaction transaction : transactions) {
			transactionDTOs.add(convertTransactionToTransactionDTO(transaction,convertCriteria));
		}
		
		return transactionDTOs;

	}
	
	public TransactionDTO convertTransactionToTransactionDTO(Transaction transaction, TransactionConvertCriteriaDTO convertCriteria) {
		
		TransactionDTO transactionDTO = new TransactionDTO();
		
		transactionDTO.setTransactionId(transaction.getTransactionId());

	
		transactionDTO.setTransactionDate(transaction.getTransactionDate());

	
		transactionDTO.setTransactionStatus(transaction.getTransactionStatus());

	

		
		return transactionDTO;
	}

	public ResultDTO updateTransaction(TransactionDTO transactionDTO, RequestDTO requestDTO) {
		
		Transaction transaction = transactionDao.getById(transactionDTO.getTransactionId());

		transaction.setTransactionId(ControllerUtils.setValue(transaction.getTransactionId(), transactionDTO.getTransactionId()));

		transaction.setTransactionDate(ControllerUtils.setValue(transaction.getTransactionDate(), transactionDTO.getTransactionDate()));

		transaction.setTransactionStatus(ControllerUtils.setValue(transaction.getTransactionStatus(), transactionDTO.getTransactionStatus()));



        transaction = transactionDao.save(transaction);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TransactionDTO getTransactionDTOById(Integer transactionId) {
	
		Transaction transaction = transactionDao.getById(transactionId);
			
		
		TransactionConvertCriteriaDTO convertCriteria = new TransactionConvertCriteriaDTO();
		return(this.convertTransactionToTransactionDTO(transaction,convertCriteria));
	}







}
