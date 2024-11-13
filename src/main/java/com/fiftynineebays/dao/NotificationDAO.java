package com.fiftynineebays.dao;

import java.util.List;

import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.domain.Notification;





public interface NotificationDAO extends GenericDAO<Notification, Integer> {
  
	List<Notification> findAll();
	






}


