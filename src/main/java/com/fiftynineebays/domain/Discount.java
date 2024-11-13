package com.fiftynineebays.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="discounts")
@Getter @Setter @NoArgsConstructor
public class Discount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="discount_id")
	private Integer discountId;
    
  	@Column(name="discount_amount")
	private double discountAmount;
    
  	@Column(name="discount_code")
	private String discountCode;
    
  	@Column(name="expiration_date")
	private Date expirationDate;
    
	




}
