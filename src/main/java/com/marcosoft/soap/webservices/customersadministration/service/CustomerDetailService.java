package com.marcosoft.soap.webservices.customersadministration.service;

import com.marcosoft.soap.webservices.customersadministration.bean.Customer;
import com.marcosoft.soap.webservices.customersadministration.helper.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDetailService {

    private static List<Customer> customers = new ArrayList<>();

    static {
        Customer customer1 = new Customer(1,"Adriana","342342","adri@gmail.com");
        customers.add(customer1);

        Customer customer2 = new Customer(2,"Aline","342342","aline@gmail.com");
        customers.add(customer2);

        Customer customer3 = new Customer(3,"Caroline","342342","carolzinha@gmail.com");
        customers.add(customer3);

        Customer customer4 = new Customer(4,"Lisiane","342342","lisi@gmail.com");
        customers.add(customer4);

    }

    public Customer findById(int id){
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }
        return null;

    }

    public List<Customer> findAll(){
        return customers;
    }

    public Status deleteById(int id){
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
        if (customerOptional.isPresent()){
            customers.remove(customerOptional.get());
            return Status.SUCCESS;
        }
        return  Status.FAILURE;

    }

}
