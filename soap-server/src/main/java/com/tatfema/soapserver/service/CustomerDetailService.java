package com.tatfema.soapserver.service;

import com.tatfema.soapserver.bean.Customer;
import com.tatfema.soapserver.helper.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDetailService {

    private static List<Customer> customers = new ArrayList<>();

    static {
        Customer customer1 = new Customer(1, "Bob", "999999", "bob@gmail.com");
        customers.add(customer1);
        Customer customer2 = new Customer(2, "Jose", "88888", "jose@gmail.com");
        customers.add(customer2);
        Customer customer3 = new Customer(3, "Maria", "7777777", "maria@gmail.com");
        customers.add(customer3);
        Customer customer4 = new Customer(4, "Joao", "6666666", "joao@gmail.com");
        customers.add(customer4);
    }

    public Customer findById(int id) {
        Optional<Customer> customerOptional = customers.stream()
                .filter(customer -> customer.getId() == id).findAny();
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }
        return null;
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Status deleteById(int id) {
        Optional<Customer> customerOptional = customers.stream().filter(customer -> customer.getId() ==id).findAny();
        if(customerOptional.isPresent()){
            customers.remove(customerOptional.get());
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }
}
