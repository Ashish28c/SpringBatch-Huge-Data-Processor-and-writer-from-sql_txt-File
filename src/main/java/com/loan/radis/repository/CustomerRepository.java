package com.loan.radis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loan.radis.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
