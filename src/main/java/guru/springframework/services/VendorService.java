package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.VendorDTO;

public interface VendorService {
	VendorDTO getVendorById(Long id);
	List<VendorDTO> getAllVendors();
	VendorDTO saveNewVendor(VendorDTO vendorDto);
	VendorDTO overwriteVendorById(Long id, VendorDTO vendorDto);
	void deleteVendorById(Long id);
	VendorDTO patchVendorbyId(Long id, VendorDTO vendorDto);
}
