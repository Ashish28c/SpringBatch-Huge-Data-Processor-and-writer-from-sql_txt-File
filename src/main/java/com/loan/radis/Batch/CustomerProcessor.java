package com.loan.radis.Batch;

import org.springframework.batch.item.ItemProcessor;

import com.loan.radis.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer>{

	@Override
	public Customer process(Customer Customer) throws Exception {
		
		return Customer;	
	}
	
}
