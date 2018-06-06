package guru.springframework.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.services.CategoryService;

public class CategoryControllerTest {
	@Mock
	CategoryService categoryService;
	@InjectMocks
	CategoryController controller;
	public static final String NAME = "Jim";
	MockMvc mockMvc;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1l);
		category1.setName(NAME);
		
		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2l);
		category2.setName("Bob");
		List<CategoryDTO> categories = Lists.newArrayList(category1,category2);
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get("/api/v1/categories/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", Matchers.hasSize(2)));
	}

	@Test
	public void testGetCategoryByName() throws Exception {
		 CategoryDTO category1 = new CategoryDTO();
	        category1.setId(1l);
	        category1.setName(NAME);

	        when(categoryService.getCategoryByName(Mockito.anyString())).thenReturn(category1);

	        mockMvc.perform(get("/api/v1/categories/Jim")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.name", equalTo(NAME)));
	}

}
