package com.fiftynineebays.dao;

import java.util.List;

import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.domain.Order;





public interface OrderDAO extends GenericDAO<Order, Integer> {
  
	List<Order> findAll();
	






}


