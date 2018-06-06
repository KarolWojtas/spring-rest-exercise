package guru.springframework.controllers.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("/api/v1/customers/")
@Api(description="This is my customer controller")
public class CustomerController {
	CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	@GetMapping("{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id){
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(Long.valueOf(id)), HttpStatus.OK);
	}
	@GetMapping
	@ApiOperation(value="This will get a list of all vendors", notes="Notes")
	public ResponseEntity<CustomerListDTO> getAllCustomers(){
		return ResponseEntity.ok(new CustomerListDTO(customerService.getAllCustomers()));
	}
	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO),HttpStatus.CREATED);
	}
	@PutMapping("{id}")
	public ResponseEntity<CustomerDTO> replaceCustomerById(@PathVariable String id, @RequestBody CustomerDTO customer){
		return new ResponseEntity<CustomerDTO>(customerService.saveCustomerByDTO(Long.valueOf(id), customer),HttpStatus.CREATED);
	}
	@PatchMapping("{id}")
	public ResponseEntity<CustomerDTO> patchCustomerById(@PathVariable String id, @RequestBody CustomerDTO customer){
		return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(Long.valueOf(id), customer), HttpStatus.CREATED);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<CustomerDTO> deleteCustomerById(@PathVariable String id){
		customerService.deleteCustomerById(Long.valueOf(id));
		return ResponseEntity.ok().build();
	}
}