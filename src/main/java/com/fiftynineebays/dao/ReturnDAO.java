package com.fiftynineebays.dao;

import java.util.List;

import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.domain.Return;





public interface ReturnDAO extends GenericDAO<Return, Integer> {
  
	List<Return> findAll();
	






}


