package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

@Mapper(componentModel="spring")
public interface CategoryMapper{
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	CategoryDTO categoryToCategoryDTO(Category category);
}
