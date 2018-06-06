package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;

@Mapper(componentModel="spring")
public interface VendorMapper {
	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);
	
	public Vendor vendorDtoToVendor(VendorDTO vendorDTO);
	public VendorDTO vendorToVendorDto(Vendor vendor);
}
