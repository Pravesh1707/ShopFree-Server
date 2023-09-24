package com.example.Models;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PaymentInformation {

	@Column(name = "cardholder_name")
	private String cardholderName;
	
	@Column(name = "card_Number")
	private String cardNumber;
	
	@Column(name = "expiration_Date;")
	private LocalDate expirationDate;
	
	@Column(name = "cvv")
	private String cv;
}
