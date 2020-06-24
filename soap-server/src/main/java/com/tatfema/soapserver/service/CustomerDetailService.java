package com.tatfema.soapserver.service;

import com.tatfema.soapserver.bean.Customer;
import com.tatfema.soapserver.bean.CustomerRepository;
import com.tatfema.soapserver.helper.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDetailService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }
        return null;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Status deleteById(Integer id) {
        try {
            customerRepository.deleteById(id);
            return Status.SUCCESS;
        } catch (Exception e) {
            return Status.FAILURE;
        }
    }

    public Status isert(Customer customer) {
        try {
            customerRepository.save(customer);
            return Status.SUCCESS;
        } catch (Exception e) {
            return Status.FAILURE;

        }
    }
}
