package guru.springframework.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.bootstrap.CustomerBootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceITTest {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	VendorRepository vendorRepository;
	CustomerService customerService;
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
		customerService = new CustomerServiceImpl(customerRepository, customerMapper);
		CustomerBootstrap boot = new CustomerBootstrap(customerRepository,vendorRepository);
		
		boot.run();
	}
	@Test
	public void testPatchCustomerFirstName() {
		String updatedName= "Updated name";
		Long id = getCustomerIdValue();
		Customer originalCustomer = customerRepository.findById(id).get();
		assertNotNull(originalCustomer.getId());
		
		 String originalFirstName = originalCustomer.getFirstName();
	     String originalLastName = originalCustomer.getLastName();
	     
	     CustomerDTO customerDTO = new CustomerDTO();
	     customerDTO.setFirstName(updatedName);
	     
	     customerService.patchCustomer(id, customerDTO);

	        Customer updatedCustomer = customerRepository.findById(id).get();

	        assertNotNull(updatedCustomer);
	        assertEquals(updatedName, updatedCustomer.getFirstName());
	        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
	        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
	}
	private Long getCustomerIdValue() {
		List<Customer> customers = customerRepository.findAll();
		return customers.get(0).getId();
	}
	
}
