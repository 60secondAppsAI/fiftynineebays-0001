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
import com.fiftynineebays.dao.CategoryDAO;
import com.fiftynineebays.domain.Category;
import com.fiftynineebays.dto.CategoryDTO;
import com.fiftynineebays.dto.CategorySearchDTO;
import com.fiftynineebays.dto.CategoryPageDTO;
import com.fiftynineebays.dto.CategoryConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.CategoryService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, Integer> implements CategoryService {

    private final static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDAO categoryDao;

	


	@Override
	public GenericDAO<Category, Integer> getDAO() {
		return (GenericDAO<Category, Integer>) categoryDao;
	}
	
	public List<Category> findAll () {
		List<Category> categorys = categoryDao.findAll();
		
		return categorys;	
		
	}

	public ResultDTO addCategory(CategoryDTO categoryDTO, RequestDTO requestDTO) {

		Category category = new Category();

		category.setCategoryId(categoryDTO.getCategoryId());


		category.setName(categoryDTO.getName());


		category.setDescription(categoryDTO.getDescription());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		category = categoryDao.save(category);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Category> getAllCategorys(Pageable pageable) {
		return categoryDao.findAll(pageable);
	}

	public Page<Category> getAllCategorys(Specification<Category> spec, Pageable pageable) {
		return categoryDao.findAll(spec, pageable);
	}

	public ResponseEntity<CategoryPageDTO> getCategorys(CategorySearchDTO categorySearchDTO) {
	
			Integer categoryId = categorySearchDTO.getCategoryId(); 
 			String name = categorySearchDTO.getName(); 
 			String description = categorySearchDTO.getDescription(); 
 			String sortBy = categorySearchDTO.getSortBy();
			String sortOrder = categorySearchDTO.getSortOrder();
			String searchQuery = categorySearchDTO.getSearchQuery();
			Integer page = categorySearchDTO.getPage();
			Integer size = categorySearchDTO.getSize();

	        Specification<Category> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, categoryId, "categoryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Category> categorys = this.getAllCategorys(spec, pageable);
		
		//System.out.println(String.valueOf(categorys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(categorys.getTotalPages()));
		
		List<Category> categorysList = categorys.getContent();
		
		CategoryConvertCriteriaDTO convertCriteria = new CategoryConvertCriteriaDTO();
		List<CategoryDTO> categoryDTOs = this.convertCategorysToCategoryDTOs(categorysList,convertCriteria);
		
		CategoryPageDTO categoryPageDTO = new CategoryPageDTO();
		categoryPageDTO.setCategorys(categoryDTOs);
		categoryPageDTO.setTotalElements(categorys.getTotalElements());
		return ResponseEntity.ok(categoryPageDTO);
	}

	public List<CategoryDTO> convertCategorysToCategoryDTOs(List<Category> categorys, CategoryConvertCriteriaDTO convertCriteria) {
		
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		
		for (Category category : categorys) {
			categoryDTOs.add(convertCategoryToCategoryDTO(category,convertCriteria));
		}
		
		return categoryDTOs;

	}
	
	public CategoryDTO convertCategoryToCategoryDTO(Category category, CategoryConvertCriteriaDTO convertCriteria) {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		categoryDTO.setCategoryId(category.getCategoryId());

	
		categoryDTO.setName(category.getName());

	
		categoryDTO.setDescription(category.getDescription());

	

		
		return categoryDTO;
	}

	public ResultDTO updateCategory(CategoryDTO categoryDTO, RequestDTO requestDTO) {
		
		Category category = categoryDao.getById(categoryDTO.getCategoryId());

		category.setCategoryId(ControllerUtils.setValue(category.getCategoryId(), categoryDTO.getCategoryId()));

		category.setName(ControllerUtils.setValue(category.getName(), categoryDTO.getName()));

		category.setDescription(ControllerUtils.setValue(category.getDescription(), categoryDTO.getDescription()));



        category = categoryDao.save(category);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CategoryDTO getCategoryDTOById(Integer categoryId) {
	
		Category category = categoryDao.getById(categoryId);
			
		
		CategoryConvertCriteriaDTO convertCriteria = new CategoryConvertCriteriaDTO();
		return(this.convertCategoryToCategoryDTO(category,convertCriteria));
	}







}
