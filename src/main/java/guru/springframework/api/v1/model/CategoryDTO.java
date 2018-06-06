package guru.springframework.api.v1.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CategoryDTO{
	@JsonIgnore
	private Long id;
    private String name;
    
    
}
