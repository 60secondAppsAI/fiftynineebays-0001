package com.fiftynineebays.dao;

import java.util.List;

import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.domain.Promotion;





public interface PromotionDAO extends GenericDAO<Promotion, Integer> {
  
	List<Promotion> findAll();
	






}


