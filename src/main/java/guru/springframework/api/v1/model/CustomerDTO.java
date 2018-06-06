package guru.springframework.api.v1.model;



import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerDTO {
	@JsonIgnore
	private Long id;
	@ApiModelProperty("This is first name")
	private String firstName;
	private String lastName;
	@JsonProperty("customer_url")
	private String link;
}
