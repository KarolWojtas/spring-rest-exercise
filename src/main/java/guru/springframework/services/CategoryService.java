package guru.springframework.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

public interface CategoryService{
	CategoryDTO getCategoryByName(String name);
	List<CategoryDTO> getAllCategories(); 
}
