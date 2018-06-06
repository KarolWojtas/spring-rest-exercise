package guru.springframework.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.repositories.VendorRepository;
@DataJpaTest
@RunWith(SpringRunner.class)
public class VendorServiceImplIT {
	@Autowired
	private VendorRepository vendorRepository;
	private VendorMapper mapper = VendorMapper.INSTANCE;
	private VendorService vendorService;
	@Before
	public void setUp() throws Exception {
		vendorService = new VendorServiceImpl(vendorRepository, mapper);
		VendorDTO vendor = new VendorDTO();
		vendor.setName("Apple");
		
		vendorService.saveNewVendor(vendor);
	}

	@Test
	public void testDeleteVendorById() {
		
		Long id = vendorService.getAllVendors().iterator().next().getId();
		vendorService.deleteVendorById(id);
		
		assertEquals(vendorRepository.count(), 0);
	}

	@Test
	public void testPatchVendorbyId() {
		VendorDTO vendor2 = new VendorDTO();
		vendor2.setName("Apple Inc");
		Long id = vendorService.getAllVendors().iterator().next().getId();
		VendorDTO vendorSaved = vendorService.patchVendorbyId(id, vendor2);
		
		assertEquals("Apple Inc", vendorSaved.getName());
		assertEquals(1, vendorRepository.count());
		
	}

}
