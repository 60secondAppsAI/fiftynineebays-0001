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
import com.fiftynineebays.dao.OrderDAO;
import com.fiftynineebays.domain.Order;
import com.fiftynineebays.dto.OrderDTO;
import com.fiftynineebays.dto.OrderSearchDTO;
import com.fiftynineebays.dto.OrderPageDTO;
import com.fiftynineebays.dto.OrderConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.OrderService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDAO orderDao;

	


	@Override
	public GenericDAO<Order, Integer> getDAO() {
		return (GenericDAO<Order, Integer>) orderDao;
	}
	
	public List<Order> findAll () {
		List<Order> orders = orderDao.findAll();
		
		return orders;	
		
	}

	public ResultDTO addOrder(OrderDTO orderDTO, RequestDTO requestDTO) {

		Order order = new Order();

		order.setOrderId(orderDTO.getOrderId());


		order.setOrderDate(orderDTO.getOrderDate());


		order.setStatus(orderDTO.getStatus());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Order> getAllOrders(Pageable pageable) {
		return orderDao.findAll(pageable);
	}

	public Page<Order> getAllOrders(Specification<Order> spec, Pageable pageable) {
		return orderDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderPageDTO> getOrders(OrderSearchDTO orderSearchDTO) {
	
			Integer orderId = orderSearchDTO.getOrderId(); 
   			String status = orderSearchDTO.getStatus(); 
 			String sortBy = orderSearchDTO.getSortBy();
			String sortOrder = orderSearchDTO.getSortOrder();
			String searchQuery = orderSearchDTO.getSearchQuery();
			Integer page = orderSearchDTO.getPage();
			Integer size = orderSearchDTO.getSize();

	        Specification<Order> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderId, "orderId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Order> orders = this.getAllOrders(spec, pageable);
		
		//System.out.println(String.valueOf(orders.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orders.getTotalPages()));
		
		List<Order> ordersList = orders.getContent();
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		List<OrderDTO> orderDTOs = this.convertOrdersToOrderDTOs(ordersList,convertCriteria);
		
		OrderPageDTO orderPageDTO = new OrderPageDTO();
		orderPageDTO.setOrders(orderDTOs);
		orderPageDTO.setTotalElements(orders.getTotalElements());
		return ResponseEntity.ok(orderPageDTO);
	}

	public List<OrderDTO> convertOrdersToOrderDTOs(List<Order> orders, OrderConvertCriteriaDTO convertCriteria) {
		
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		
		for (Order order : orders) {
			orderDTOs.add(convertOrderToOrderDTO(order,convertCriteria));
		}
		
		return orderDTOs;

	}
	
	public OrderDTO convertOrderToOrderDTO(Order order, OrderConvertCriteriaDTO convertCriteria) {
		
		OrderDTO orderDTO = new OrderDTO();
		
		orderDTO.setOrderId(order.getOrderId());

	
		orderDTO.setOrderDate(order.getOrderDate());

	
		orderDTO.setStatus(order.getStatus());

	

		
		return orderDTO;
	}

	public ResultDTO updateOrder(OrderDTO orderDTO, RequestDTO requestDTO) {
		
		Order order = orderDao.getById(orderDTO.getOrderId());

		order.setOrderId(ControllerUtils.setValue(order.getOrderId(), orderDTO.getOrderId()));

		order.setOrderDate(ControllerUtils.setValue(order.getOrderDate(), orderDTO.getOrderDate()));

		order.setStatus(ControllerUtils.setValue(order.getStatus(), orderDTO.getStatus()));



        order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderDTO getOrderDTOById(Integer orderId) {
	
		Order order = orderDao.getById(orderId);
			
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		return(this.convertOrderToOrderDTO(order,convertCriteria));
	}







}
