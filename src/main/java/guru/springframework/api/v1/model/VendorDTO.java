package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class VendorDTO {
	
	@JsonIgnore
	private Long id;
	@ApiModelProperty("Name of a vendor")
	private String name;
	@JsonProperty("vendor_url")
	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty("vendor's url")
	private String link;
	public VendorDTO(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}
	
}
