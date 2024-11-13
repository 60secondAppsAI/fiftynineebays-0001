package com.fiftynineebays.dao.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.fiftynineebays.dao.GenericDAO;

import jakarta.persistence.EntityManager;

public class GenericDAOImpl<T, ID> extends SimpleJpaRepository<T, ID> implements GenericDAO<T, ID> {

    
	    private EntityManager em; // protected for package and subclasses

    private Class<T> type;

    public GenericDAOImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {

    	super(entityInformation, entityManager);
    	this.em = entityManager;
    	this.type = entityInformation.getJavaType();
   }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return this.findAll(pageable);
    }
    
    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return this.findAll(spec, pageable);
    }

}