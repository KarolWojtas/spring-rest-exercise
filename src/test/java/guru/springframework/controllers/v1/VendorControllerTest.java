package guru.springframework.controllers.v1;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.services.AbstractControllerTest;
import guru.springframework.services.VendorService;

public class VendorControllerTest {
	@Mock
	private VendorService vendorService;
	private static final String API_ROOT ="/api/v1/vendors/";
	VendorDTO vendor;
	@InjectMocks
	private VendorController controller;
	private MockMvc mockMvc;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		vendor = new VendorDTO();
		vendor.setId(1l);
		vendor.setName("Apple");
		
	}

	@Test
	public void testGetAllVendors() throws Exception {
		List<VendorDTO> vendors = Arrays.asList(new VendorDTO(), new VendorDTO());
		
		when(vendorService.getAllVendors()).thenReturn(vendors);
		
		mockMvc.perform(get(API_ROOT))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", Matchers.hasSize(2)))
			.andExpect(jsonPath("$.vendors[0].vendor_url", Matchers.notNullValue()));
	}

	@Test
	public void testGetvendorById() throws Exception {
		when(vendorService.getVendorById(anyLong())).thenReturn(vendor);
		
		mockMvc.perform(get(API_ROOT+1).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", Matchers.equalTo("Apple")));
		
	}

	@Test
	public void testCreateNewVendor() throws Exception {
		when(vendorService.saveNewVendor(any(VendorDTO.class))).thenReturn(vendor);
		
		mockMvc.perform(post(API_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(vendor)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", Matchers.equalTo("Apple")));
	}

	@Test
	public void testDeleteVendorbyId() throws Exception {
		mockMvc.perform(delete(API_ROOT+1))
			.andExpect(status().isAccepted());
	}

	@Test
	public void testUpdateVendor() throws JsonProcessingException, Exception {
		when(vendorService.overwriteVendorById(anyLong(), any(VendorDTO.class))).thenReturn(vendor);
		
		mockMvc.perform(put(API_ROOT+1).content(new ObjectMapper().writeValueAsString(vendor)).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", Matchers.equalTo("Apple")));
	}

	@Test
	public void testPatchVendor() throws JsonProcessingException, Exception {
	when(vendorService.patchVendorbyId(anyLong(), any(VendorDTO.class))).thenReturn(vendor);
		
		mockMvc.perform(patch(API_ROOT+1).content(new ObjectMapper().writeValueAsString(vendor)).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", Matchers.equalTo("Apple")));
	}

}
