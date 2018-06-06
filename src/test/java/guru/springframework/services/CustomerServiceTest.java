package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	@Mock
	CustomerRepository repository;
	CustomerMapper mapper = CustomerMapper.INSTANCE;
	CustomerService service;
	Customer customer1;
	private static final String FIRST_NAME = "Dupek";
	private static final String LAST_NAME = "Dupski";
	private static final Long ID = 3l;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new CustomerServiceImpl(repository,mapper);
		customer1 = new Customer();
		customer1.setId(3l);
		customer1.setFirstName("Dupek");
		customer1.setLastName("Dupski");
	}

	@Test
	public void testGetCustomerById() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(customer1));
		
		CustomerDTO customerFound = service.getCustomerById(3l);
		
		assertEquals(Long.valueOf(3l), customerFound.getId());
		assertEquals("Dupek", customerFound.getFirstName());
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = Arrays.asList(new Customer(1l), new Customer(2l), new Customer(3l));
		
		when(repository.findAll()).thenReturn(customers);
		
		List<CustomerDTO> customersFound = service.getAllCustomers();
		
		assertEquals(3, customersFound.size());
	}
	@Test
	public void testCreateNewCustomer() {
		CustomerDTO customerDto = new CustomerDTO();
		customerDto.setId(ID);
		customerDto.setFirstName(FIRST_NAME);
		
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		
		when(repository.save(any(Customer.class))).thenReturn(customer);
		
		CustomerDTO savedDto = service.createNewCustomer(customerDto);
		savedDto.setLink("lol");
		
		assertEquals(customerDto.getFirstName(), savedDto.getFirstName());
	}
	@Test
	public void testSaveCustomerByDTO() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1l);

        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = service.saveCustomerByDTO(1L, customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
       
	}
	@Test
	public void testDeleteByID() {
		repository.deleteById(ID);
		verify(repository , times(1)).deleteById(anyLong());
	}

}
