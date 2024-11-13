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
@Table(name="transactions")
@Getter @Setter @NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="transaction_id")
	private Integer transactionId;
    
  	@Column(name="transaction_date")
	private Date transactionDate;
    
  	@Column(name="transaction_status")
	private String transactionStatus;
    
	




}
