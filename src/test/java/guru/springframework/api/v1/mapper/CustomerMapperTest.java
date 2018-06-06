package guru.springframework.api.v1.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

public class CustomerMapperTest {
	CustomerMapper mapper = CustomerMapper.INSTANCE;
	private static final String FIRST_NAME = "Henryk";
	private static final String LAST_NAME = "Dupski";
	private static final Long ID =3l;
	@Test
	public void testMapping() {
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);
		
		CustomerDTO dto = mapper.customerToCustomerDTO(customer);
		
		assertEquals(Long.valueOf(3l), dto.getId());
		assertEquals(FIRST_NAME, dto.getFirstName());
		assertEquals(LAST_NAME, dto.getLastName());
		assertNull(dto.getLink());
	}
}
