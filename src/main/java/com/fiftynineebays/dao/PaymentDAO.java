package com.fiftynineebays.dao;

import java.util.List;

import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


