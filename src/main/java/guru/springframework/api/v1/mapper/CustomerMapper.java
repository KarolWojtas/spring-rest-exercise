package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

@Mapper(componentModel="spring")
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	@Mappings({
		@Mapping(source="firstName", target="firstName"),
		@Mapping(source="lastName", target="lastName"),
		@Mapping(source="id", target="id")
	})
	public CustomerDTO customerToCustomerDTO(Customer customer);
	public Customer customerDtoToCustomer(CustomerDTO customer);
}
