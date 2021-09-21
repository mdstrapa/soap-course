package com.marcosoft.soap.webservices.customersadministration.soap;

import com.marcosoft.*;
import com.marcosoft.soap.webservices.customersadministration.bean.Customer;
import com.marcosoft.soap.webservices.customersadministration.service.CustomerDetailService;
import com.marcosoft.soap.webservices.customersadministration.soap.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;
import java.util.List;


@Endpoint
public class CustomerDetailEndPoint {

    @Autowired
    CustomerDetailService service;

    @PayloadRoot(namespace = "http://marcosoft.com", localPart = "GetCustomerDetailRequest")
    @ResponsePayload
    public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) throws Exception{
        Customer customer = service.findById(req.getId().intValue());
        if(customer == null){
            throw new CustomerNotFoundException("Invalid Customer Id " + req.getId().toString());
        }
        return convertToGetCustomerDetailResponse(customer);

    }

    private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer){
        GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
        resp.setCustomerDetail(convertToCustomerDetail(customer));
        return resp;
    }

    private CustomerDetail convertToCustomerDetail(Customer customer){
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setId(BigInteger.valueOf(customer.getId()));
        customerDetail.setName(customer.getName());
        customerDetail.setPhone(customer.getPhone());
        customerDetail.setEmail(customer.getEmail());
        return customerDetail;
    }

    @PayloadRoot(namespace = "http://marcosoft.com", localPart = "GetAllCustomerDetailRequest")
    @ResponsePayload
    public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req){
        List<Customer> customers = service.findAll();
        return convertToGetAllCustomerDetailResponse(customers);
    }

    private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers){
        GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
        customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
        return resp;
    }

    @PayloadRoot(namespace = "http://marcosoft.com", localPart = "DeleteCustomerRequest")
    @ResponsePayload
    public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req){
        DeleteCustomerResponse resp = new DeleteCustomerResponse();
        resp.setStatus(convertStatusSoap(service.deleteById(req.getId().intValue())));
        return resp;
    }

    private com.marcosoft.Status convertStatusSoap(com.marcosoft.soap.webservices.customersadministration.helper.Status statusService){
        if (statusService == com.marcosoft.soap.webservices.customersadministration.helper.Status.FAILURE){
            return Status.FAILURE;
        }
        return Status.SUCCESS;
    }

}
