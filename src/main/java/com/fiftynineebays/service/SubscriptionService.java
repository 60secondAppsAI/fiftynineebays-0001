package com.fiftynineebays.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.fiftynineebays.domain.Subscription;
import com.fiftynineebays.dto.SubscriptionDTO;
import com.fiftynineebays.dto.SubscriptionSearchDTO;
import com.fiftynineebays.dto.SubscriptionPageDTO;
import com.fiftynineebays.dto.SubscriptionConvertCriteriaDTO;
import com.fiftynineebays.service.GenericService;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SubscriptionService extends GenericService<Subscription, Integer> {

	List<Subscription> findAll();

	ResultDTO addSubscription(SubscriptionDTO subscriptionDTO, RequestDTO requestDTO);

	ResultDTO updateSubscription(SubscriptionDTO subscriptionDTO, RequestDTO requestDTO);

    Page<Subscription> getAllSubscriptions(Pageable pageable);

    Page<Subscription> getAllSubscriptions(Specification<Subscription> spec, Pageable pageable);

	ResponseEntity<SubscriptionPageDTO> getSubscriptions(SubscriptionSearchDTO subscriptionSearchDTO);
	
	List<SubscriptionDTO> convertSubscriptionsToSubscriptionDTOs(List<Subscription> subscriptions, SubscriptionConvertCriteriaDTO convertCriteria);

	SubscriptionDTO getSubscriptionDTOById(Integer subscriptionId);







}





