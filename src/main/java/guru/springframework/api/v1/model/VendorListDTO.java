package guru.springframework.api.v1.model;

import java.util.List;

import guru.springframework.domain.Vendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class VendorListDTO {
	@ApiModelProperty("List of vendors")
	private List<VendorDTO> vendors;
	public VendorListDTO() {
		// TODO Auto-generated constructor stub
	}
	public VendorListDTO(List<VendorDTO> vendors) {
		super();
		this.vendors = vendors;
	}

}
