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
import com.fiftynineebays.dao.CartDAO;
import com.fiftynineebays.domain.Cart;
import com.fiftynineebays.dto.CartDTO;
import com.fiftynineebays.dto.CartSearchDTO;
import com.fiftynineebays.dto.CartPageDTO;
import com.fiftynineebays.dto.CartConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.CartService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class CartServiceImpl extends GenericServiceImpl<Cart, Integer> implements CartService {

    private final static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	CartDAO cartDao;

	


	@Override
	public GenericDAO<Cart, Integer> getDAO() {
		return (GenericDAO<Cart, Integer>) cartDao;
	}
	
	public List<Cart> findAll () {
		List<Cart> carts = cartDao.findAll();
		
		return carts;	
		
	}

	public ResultDTO addCart(CartDTO cartDTO, RequestDTO requestDTO) {

		Cart cart = new Cart();

		cart.setCartId(cartDTO.getCartId());


		cart.setQuantity(cartDTO.getQuantity());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		cart = cartDao.save(cart);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Cart> getAllCarts(Pageable pageable) {
		return cartDao.findAll(pageable);
	}

	public Page<Cart> getAllCarts(Specification<Cart> spec, Pageable pageable) {
		return cartDao.findAll(spec, pageable);
	}

	public ResponseEntity<CartPageDTO> getCarts(CartSearchDTO cartSearchDTO) {
	
			Integer cartId = cartSearchDTO.getCartId(); 
  			String sortBy = cartSearchDTO.getSortBy();
			String sortOrder = cartSearchDTO.getSortOrder();
			String searchQuery = cartSearchDTO.getSearchQuery();
			Integer page = cartSearchDTO.getPage();
			Integer size = cartSearchDTO.getSize();

	        Specification<Cart> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, cartId, "cartId"); 
			
			

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

		Page<Cart> carts = this.getAllCarts(spec, pageable);
		
		//System.out.println(String.valueOf(carts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(carts.getTotalPages()));
		
		List<Cart> cartsList = carts.getContent();
		
		CartConvertCriteriaDTO convertCriteria = new CartConvertCriteriaDTO();
		List<CartDTO> cartDTOs = this.convertCartsToCartDTOs(cartsList,convertCriteria);
		
		CartPageDTO cartPageDTO = new CartPageDTO();
		cartPageDTO.setCarts(cartDTOs);
		cartPageDTO.setTotalElements(carts.getTotalElements());
		return ResponseEntity.ok(cartPageDTO);
	}

	public List<CartDTO> convertCartsToCartDTOs(List<Cart> carts, CartConvertCriteriaDTO convertCriteria) {
		
		List<CartDTO> cartDTOs = new ArrayList<CartDTO>();
		
		for (Cart cart : carts) {
			cartDTOs.add(convertCartToCartDTO(cart,convertCriteria));
		}
		
		return cartDTOs;

	}
	
	public CartDTO convertCartToCartDTO(Cart cart, CartConvertCriteriaDTO convertCriteria) {
		
		CartDTO cartDTO = new CartDTO();
		
		cartDTO.setCartId(cart.getCartId());

	
		cartDTO.setQuantity(cart.getQuantity());

	

		
		return cartDTO;
	}

	public ResultDTO updateCart(CartDTO cartDTO, RequestDTO requestDTO) {
		
		Cart cart = cartDao.getById(cartDTO.getCartId());

		cart.setCartId(ControllerUtils.setValue(cart.getCartId(), cartDTO.getCartId()));

		cart.setQuantity(ControllerUtils.setValue(cart.getQuantity(), cartDTO.getQuantity()));



        cart = cartDao.save(cart);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CartDTO getCartDTOById(Integer cartId) {
	
		Cart cart = cartDao.getById(cartId);
			
		
		CartConvertCriteriaDTO convertCriteria = new CartConvertCriteriaDTO();
		return(this.convertCartToCartDTO(cart,convertCriteria));
	}







}
