package guru.springframework.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
@Service
public class CustomerServiceImpl implements CustomerService{
	CustomerRepository customerRepository;
	CustomerMapper customerMapper;
	private static final String apiRoot ="http://localhost:8080/api/v1/customers/";
	

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		if(id!=null) {
			Optional<Customer> customerOptional = customerRepository.findById(id);
			if (customerOptional.isPresent()) {
				return customerMapper.customerToCustomerDTO(customerOptional.get());
			} else {
				throw new ResourceNotFoundException();
			}
		}
		return new CustomerDTO();
		
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		return customerRepository.findAll().stream()
				.map(customerMapper::customerToCustomerDTO)
				.map(customer -> {
					//customer.setLink(apiRoot+customer.getId());
					customer.setLink(MvcUriComponentsBuilder.fromMethodName(CustomerController.class, "getCustomerById", customer.getId().toString()).build().toString());
					return customer;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customer) {
		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customer));
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customer) {
		Customer customerToSave = customerMapper.customerDtoToCustomer(customer);
		customerToSave.setId(id);
		
		return saveAndReturnDTO(customerToSave);
	}
	  private CustomerDTO saveAndReturnDTO(Customer customer) {
	        Customer savedCustomer = customerRepository.save(customer);
	        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
	        returnDto.setLink(MvcUriComponentsBuilder.fromMethodName(CustomerController.class, "getCustomerById", customer.getId().toString()).build().toString());
	        //returnDto.setLink("chuj");
	        
	        return returnDto;
	    }

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customer) {
		return customerRepository.findById(id).map(customerToUpdate -> {
			if(customer.getFirstName()!=null) {
				customerToUpdate.setFirstName(customer.getFirstName());
			}
			if(customer.getLastName()!=null) {
				customerToUpdate.setLastName(customer.getLastName());
			}
			CustomerDTO returnedCustomer = customerMapper.customerToCustomerDTO(customerRepository.save(customerToUpdate));
			returnedCustomer.setLink(MvcUriComponentsBuilder.fromMethodName(CustomerController.class, "getCustomerById", id.toString()).build().toString());
			return returnedCustomer;
		}).orElseThrow(()->new ResourceNotFoundException());
		
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
		
	}

	

}
