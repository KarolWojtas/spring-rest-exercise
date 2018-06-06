package guru.springframework.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.Then;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

public class VendorServiceImplTest {
	@Mock
	private VendorRepository vendorRepository;
	private VendorService service;
	private static final Long ID =3L;
	private static final String NAME = "Apple";
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
	}

	@Test
	public void testGetVendorById() {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Apple");
		
		when(vendorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(vendor1));
		
		VendorDTO vendorDTO = service.getVendorById(1l);
		
		assertEquals(vendorDTO.getName(), vendor1.getName());
		assertThat(vendorDTO.getName(), equalTo(vendor1.getName()));
	}

	@Test
	public void testGetAllVendors() {
		List<Vendor> vendors = Arrays.asList(new Vendor(),new Vendor());
		
		when(vendorRepository.findAll()).thenReturn(vendors);
		
		List<VendorDTO> vendorDTOs = service.getAllVendors();
		
		assertNotNull(vendorDTOs);
		assertEquals(vendorDTOs.size(), vendors.size());
	}

	@Test
	public void testSaveNewVendor() {
		Vendor vendor = new Vendor();
		vendor.setName("Apple");
		vendor.setId(1l);
		VendorDTO vendorDTO = VendorMapper.INSTANCE.vendorToVendorDto(vendor);
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
		
		VendorDTO vendorSaved = service.saveNewVendor(vendorDTO);
		
		assertNotNull(vendorSaved);
		assertEquals(vendor.getId(), vendorSaved.getId());
		assertEquals(vendor.getName(), vendorSaved.getName());
	}

	@Test
	public void testOverwriteVendorById() {
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		VendorDTO vendorDto = new VendorDTO();
		vendorDto.setId(ID);
		vendorDto.setName(NAME);
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
		
		VendorDTO vendorSaved = service.overwriteVendorById(ID, vendorDto);
		
		assertNotNull(vendorSaved);
		assertEquals("Apple", vendorSaved.getName());
	}


	

}
