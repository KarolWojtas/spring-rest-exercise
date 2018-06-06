package guru.springframework.bootstrap;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
@Component
public class CustomerBootstrap implements CommandLineRunner{
	CustomerRepository customerRepository;
	VendorRepository vendorRepository;
	

	public CustomerBootstrap(CustomerRepository customerRepository, VendorRepository vendorRepository) {
		super();
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		Customer pys = new Customer();
		pys.setFirstName("Henryk");
		pys.setLastName("Pupi");
		
		Customer madzia = new Customer();
		madzia.setFirstName("Magdalena");
		madzia.setLastName("Dubska");
		
		customerRepository.save(madzia);
		customerRepository.save(pys);
		
		Vendor appleVendor = new Vendor();
		appleVendor.setName("Apple");
		vendorRepository.save(appleVendor);
	}

}
