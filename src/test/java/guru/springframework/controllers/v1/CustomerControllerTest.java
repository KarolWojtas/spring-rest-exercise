package guru.springframework.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.CustomerDTO;

import guru.springframework.services.AbstractControllerTest;
import guru.springframework.services.CustomerService;
import guru.springframework.services.ResourceNotFoundException;

public class CustomerControllerTest {
	@Mock
	CustomerService service;
	MockMvc mockMvc;
	CustomerController controller;
	private static final String FIRST_NAME = "Henryk";
	private static final Long ID = 3l;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new CustomerController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();

	}

	@Test
	public void testGetCustomerById() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);

		when(service.getCustomerById(anyLong())).thenReturn(customer);

		mockMvc.perform(get("/api/v1/customers/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", Matchers.equalTo(FIRST_NAME)));
	}

    @Test
    public void testNotFoundException() throws Exception {

        when(service.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/api/v1/customers/" + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

	@Test
	public void testGetAllCustomers() throws Exception {
		List<CustomerDTO> customers = Lists.newArrayList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());

		when(service.getAllCustomers()).thenReturn(customers);

		mockMvc.perform(get("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", Matchers.hasSize(3)));

	}

	@Test
	public void testCreateNewCustomer() throws Exception {
		CustomerDTO customerDto = new CustomerDTO();
		customerDto.setId(ID);
		customerDto.setFirstName(FIRST_NAME);
		customerDto.setLink("http://localhost:8080/api/v1/customers/1");

		when(service.createNewCustomer(any(CustomerDTO.class))).thenReturn(customerDto);

		mockMvc.perform(post("/api/v1/customers/").content(AbstractControllerTest.asJsonString(customerDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customer_url", equalTo("http://localhost:8080/api/v1/customers/1")));
	}

	@Test
	public void updateCustomerById() throws Exception {
		CustomerDTO customerDto = new CustomerDTO();
		customerDto.setId(ID);
		customerDto.setFirstName(FIRST_NAME);
		customerDto.setLink("http://localhost:8080/api/v1/customers/1");

		when(service.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(customerDto);

		mockMvc.perform(put("/api/v1/customers/1").content(AbstractControllerTest.asJsonString(customerDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customer_url", equalTo("http://localhost:8080/api/v1/customers/1")));
	}

	@Test
	public void testPatchCustomer() throws Exception {

		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName("Flintstone");
		returnDTO.setLink("/api/v1/customers/1");

		when(service.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON)
				.content(AbstractControllerTest.asJsonString(customer))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo("Fred")))
				.andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}

	@Test
	public void testDeleteById() throws Exception {

		mockMvc.perform(delete("/api/v1/customers/1")).andExpect(status().isOk());
		verify(service, times(1)).deleteCustomerById(anyLong());

	}
}
