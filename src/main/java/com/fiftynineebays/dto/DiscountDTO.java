package com.fiftynineebays.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DiscountDTO {

	private Integer discountId;

	private double discountAmount;

	private String discountCode;

	private Date expirationDate;






}
