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
import com.fiftynineebays.dao.BidDAO;
import com.fiftynineebays.domain.Bid;
import com.fiftynineebays.dto.BidDTO;
import com.fiftynineebays.dto.BidSearchDTO;
import com.fiftynineebays.dto.BidPageDTO;
import com.fiftynineebays.dto.BidConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.BidService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class BidServiceImpl extends GenericServiceImpl<Bid, Integer> implements BidService {

    private final static Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);

	@Autowired
	BidDAO bidDao;

	


	@Override
	public GenericDAO<Bid, Integer> getDAO() {
		return (GenericDAO<Bid, Integer>) bidDao;
	}
	
	public List<Bid> findAll () {
		List<Bid> bids = bidDao.findAll();
		
		return bids;	
		
	}

	public ResultDTO addBid(BidDTO bidDTO, RequestDTO requestDTO) {

		Bid bid = new Bid();

		bid.setBidId(bidDTO.getBidId());


		bid.setBidAmount(bidDTO.getBidAmount());


		bid.setBidDate(bidDTO.getBidDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		bid = bidDao.save(bid);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Bid> getAllBids(Pageable pageable) {
		return bidDao.findAll(pageable);
	}

	public Page<Bid> getAllBids(Specification<Bid> spec, Pageable pageable) {
		return bidDao.findAll(spec, pageable);
	}

	public ResponseEntity<BidPageDTO> getBids(BidSearchDTO bidSearchDTO) {
	
			Integer bidId = bidSearchDTO.getBidId(); 
    			String sortBy = bidSearchDTO.getSortBy();
			String sortOrder = bidSearchDTO.getSortOrder();
			String searchQuery = bidSearchDTO.getSearchQuery();
			Integer page = bidSearchDTO.getPage();
			Integer size = bidSearchDTO.getSize();

	        Specification<Bid> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, bidId, "bidId"); 
			
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Bid> bids = this.getAllBids(spec, pageable);
		
		//System.out.println(String.valueOf(bids.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(bids.getTotalPages()));
		
		List<Bid> bidsList = bids.getContent();
		
		BidConvertCriteriaDTO convertCriteria = new BidConvertCriteriaDTO();
		List<BidDTO> bidDTOs = this.convertBidsToBidDTOs(bidsList,convertCriteria);
		
		BidPageDTO bidPageDTO = new BidPageDTO();
		bidPageDTO.setBids(bidDTOs);
		bidPageDTO.setTotalElements(bids.getTotalElements());
		return ResponseEntity.ok(bidPageDTO);
	}

	public List<BidDTO> convertBidsToBidDTOs(List<Bid> bids, BidConvertCriteriaDTO convertCriteria) {
		
		List<BidDTO> bidDTOs = new ArrayList<BidDTO>();
		
		for (Bid bid : bids) {
			bidDTOs.add(convertBidToBidDTO(bid,convertCriteria));
		}
		
		return bidDTOs;

	}
	
	public BidDTO convertBidToBidDTO(Bid bid, BidConvertCriteriaDTO convertCriteria) {
		
		BidDTO bidDTO = new BidDTO();
		
		bidDTO.setBidId(bid.getBidId());

	
		bidDTO.setBidAmount(bid.getBidAmount());

	
		bidDTO.setBidDate(bid.getBidDate());

	

		
		return bidDTO;
	}

	public ResultDTO updateBid(BidDTO bidDTO, RequestDTO requestDTO) {
		
		Bid bid = bidDao.getById(bidDTO.getBidId());

		bid.setBidId(ControllerUtils.setValue(bid.getBidId(), bidDTO.getBidId()));

		bid.setBidAmount(ControllerUtils.setValue(bid.getBidAmount(), bidDTO.getBidAmount()));

		bid.setBidDate(ControllerUtils.setValue(bid.getBidDate(), bidDTO.getBidDate()));



        bid = bidDao.save(bid);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BidDTO getBidDTOById(Integer bidId) {
	
		Bid bid = bidDao.getById(bidId);
			
		
		BidConvertCriteriaDTO convertCriteria = new BidConvertCriteriaDTO();
		return(this.convertBidToBidDTO(bid,convertCriteria));
	}







}
