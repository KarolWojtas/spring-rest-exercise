package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;

public class CategoryServiceTest {
	public static final String NAME = "Jimmy";
	public static final Long ID = 2l; 
	CategoryService categoryService;
	@Mock
	CategoryRepository repository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		categoryService = new CategoryServiceImpl(repository, CategoryMapper.INSTANCE);
	}

	@Test
	public void testGetCategoryByName() {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);
		
		when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(category));
		
		CategoryDTO categoryFound = categoryService.getCategoryByName(NAME);
		
		assertEquals(NAME, categoryFound.getName());
		
	}

	@Test
	public void testGetAllCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		
		when(repository.findAll()).thenReturn(categories);
		
		List<CategoryDTO> categoriesDTO = categoryService.getAllCategories();
		
		assertEquals(3, categoriesDTO.size());
 		
	}

}
