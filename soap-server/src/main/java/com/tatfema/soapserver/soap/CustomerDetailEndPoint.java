package com.tatfema.soapserver.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tatfema.soapserver.bean.Customer;
import com.tatfema.soapserver.service.CustomerDetailService;
import com.tatfema.soapserver.soap.exception.CustomerNotFoundException;

import br.com.tatfema.CustomerDetail;
import br.com.tatfema.DeleteCustomerRequest;
import br.com.tatfema.DeleteCustomerResponse;
import br.com.tatfema.GetAllCustomerDetailRequest;
import br.com.tatfema.GetAllCustomerDetailResponse;
import br.com.tatfema.GetCustomerDetailRequest;
import br.com.tatfema.GetCustomerDetailResponse;
import br.com.tatfema.InsertCustomerResponse;
import br.com.tatfema.InsertCustomerlRequest;
import br.com.tatfema.Status;

@Endpoint
public class CustomerDetailEndPoint {

	@Autowired
	CustomerDetailService service;

	@PayloadRoot(namespace = "http://tatfema.com.br", localPart = "GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest request)
			throws CustomerNotFoundException {
		Customer customer = service.findById(request.getId());
		if (customer == null) {
			throw new CustomerNotFoundException("Invalid Customer id:" + request.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}

	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
	}

	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}

	@PayloadRoot(namespace = "http://tatfema.com.br", localPart = "GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse proccessGetAllCustomerDetailRequest(
			@RequestPayload GetAllCustomerDetailRequest request) {
		List<Customer> customers = service.findAll();
		return convertToGetAllDetailResponse(customers);
	}

	private GetAllCustomerDetailResponse convertToGetAllDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse response = new GetAllCustomerDetailResponse();
		customers.stream().forEach(customer -> response.getCustomerDetail().add(convertToCustomerDetail(customer)));
		return response;
	}

	@PayloadRoot(namespace = "http://tatfema.com.br", localPart = "DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest request) {
		DeleteCustomerResponse response = new DeleteCustomerResponse();
		response.setStatus(convertStatusSoap(service.deleteById(request.getId())));
		return response;
	}

	// br.com.tatfema.Status
	private br.com.tatfema.Status convertStatusSoap(com.tatfema.soapserver.helper.Status statusService) {

		if (statusService == com.tatfema.soapserver.helper.Status.FAILURE) {
			return Status.FAILURE;
		}
		return Status.SUCESS;
	}

	@PayloadRoot(namespace = "http://tatfema.com.br", localPart = "InsertCustomerlRequest")
	@ResponsePayload
	public InsertCustomerResponse insertCustomerRequest(@RequestPayload InsertCustomerlRequest request) {
		InsertCustomerResponse response = new InsertCustomerResponse();
		response.setStatus(convertStatusSoap(service.isert(convertCustomer(request.getCustomerDetail()))));
		return response;
	}

	private Customer convertCustomer(CustomerDetail customerDetail) {
		return new Customer(customerDetail.getId(), customerDetail.getName(), customerDetail.getPhone(),
				customerDetail.getEmail());
	}

}
