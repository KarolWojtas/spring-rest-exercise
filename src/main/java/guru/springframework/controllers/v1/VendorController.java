package guru.springframework.controllers.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/vendors/")
@Api(tags= {"vendors"})
public class VendorController {
	private VendorService vendorService;
	public VendorController() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}
	@GetMapping
	@ApiOperation(value="List all vendors")
	public ResponseEntity<VendorListDTO> getAllVendors(){
		return new ResponseEntity<VendorListDTO>(new VendorListDTO(vendorService.getAllVendors()), HttpStatus.OK);
	}
	@GetMapping("{id}")
	@ApiOperation(value="Get a vendor by id")
	public ResponseEntity<VendorDTO> getVendorById(@PathVariable String id){
		return new ResponseEntity<VendorDTO>(vendorService.getVendorById(Long.valueOf(id)),HttpStatus.OK);
	}
	@PostMapping
	@ApiOperation(value="Create a vendor")
	public ResponseEntity<VendorDTO> createNewVendor(@RequestBody  VendorDTO vendorDTO){
		return new ResponseEntity<VendorDTO>(vendorService.saveNewVendor(vendorDTO),HttpStatus.CREATED);
		
	}
	@DeleteMapping("{id}")
	@ApiOperation("Delete a vendor by id")
	public ResponseEntity<String> deleteVendorbyId(@PathVariable String id) {
			vendorService.deleteVendorById(Long.valueOf(id));
		return new ResponseEntity<String>("Successfullydeleted vendor: "+id, HttpStatus.ACCEPTED);
	}
	@PutMapping("{id}")
	@ApiOperation("Replace a vendor by new data")
	public ResponseEntity<VendorDTO> updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDto){
		return new ResponseEntity<VendorDTO>(vendorService.overwriteVendorById(Long.valueOf(id), vendorDto), HttpStatus.OK);
	}
	@PatchMapping("{id}")
	@ApiOperation("Update a vendor")
	public ResponseEntity<VendorDTO> patchVendor(@PathVariable String id, @RequestBody VendorDTO vendorDto){
		
		return new ResponseEntity<VendorDTO>(vendorService.patchVendorbyId(Long.valueOf(id), vendorDto), HttpStatus.OK);
	}
}
