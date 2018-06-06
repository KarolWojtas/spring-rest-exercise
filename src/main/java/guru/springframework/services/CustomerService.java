package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.CustomerDTO;

public interface CustomerService {
	CustomerDTO getCustomerById(Long id);
	List<CustomerDTO> getAllCustomers();
	CustomerDTO createNewCustomer(CustomerDTO customer);
	CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customer);
	void deleteCustomerById(Long id);
	CustomerDTO patchCustomer(Long id, CustomerDTO customer);
}
