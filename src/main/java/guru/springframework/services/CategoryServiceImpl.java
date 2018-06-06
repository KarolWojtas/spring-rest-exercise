package guru.springframework.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService{
	private CategoryRepository repository;
	private final CategoryMapper mapper;
	

	public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		Optional<Category> categoryOptional = repository.findByName(name);
		if (categoryOptional.isPresent()) {
			CategoryDTO dto = mapper.categoryToCategoryDTO(categoryOptional.get());
			return dto;
		}
		return new CategoryDTO();
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		
		return repository.findAll().stream().map(category -> mapper.categoryToCategoryDTO(category))
				.collect(Collectors.toList());
	}

}
