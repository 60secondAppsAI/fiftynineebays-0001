package com.fiftynineebays.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PromotionDTO {

	private Integer promotionId;

	private String promotionType;

	private Date promotionStartDate;

	private Date promotionEndDate;






}
