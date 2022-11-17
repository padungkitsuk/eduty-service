package com.streamit.application.dto;

import lombok.Data;

@Data
public class Payer {
	private SpecifiedTaxRegistration specifiedTaxRegistration;
	
	
	public Payer() {
		super();
	}


	public Payer(SpecifiedTaxRegistration specifiedTaxRegistration) {
		super();
		this.specifiedTaxRegistration = specifiedTaxRegistration;
	}


	
	

}
