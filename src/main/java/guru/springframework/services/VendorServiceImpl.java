package guru.springframework.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
@Service
public class VendorServiceImpl implements VendorService{
	
	private VendorRepository vendorRepository;
	private VendorMapper mapper;
	public VendorServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper mapper) {
		super();
		this.vendorRepository = vendorRepository;
		this.mapper = mapper;
	}


	@Override
	public VendorDTO getVendorById(Long id) {
		/*Optional<Vendor> vendorOptional = vendorRepository.findById(id);
		if(vendorOptional.isPresent()) {
			return mapper.vendorToVendorDto(vendorOptional.get());
		} else {
			throw new ResourceNotFoundException();
		}*/
		return this.handleOptionalVendor(id, (vendorOptional) -> mapper.vendorToVendorDto(vendorOptional.get()));
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		// TODO Auto-generated method stub
		return vendorRepository.findAll().stream()
				.map(mapper::vendorToVendorDto)
				.map(dto->{
					dto.setLink("/api/v1/vendors/"+dto.getId());
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public VendorDTO saveNewVendor(VendorDTO vendorDto) {
		// TODO Auto-generated method stub
		
		return mapper.vendorToVendorDto(vendorRepository.save(mapper.vendorDtoToVendor(vendorDto)));
	}

	@Override
	public VendorDTO overwriteVendorById(Long id, VendorDTO vendorDto) {
		Vendor vendortoSave = !vendorDto.equals(null) ? mapper.vendorDtoToVendor(vendorDto) : new Vendor();
		vendortoSave.setId(id);		
		return saveAndReturnVendor(vendortoSave);
	}

	@Override
	public void deleteVendorById(Long id) {
		this.handleOptionalVendor(id, (vendorOptional) -> {
			vendorRepository.deleteById(id);
			return null;
		});
		
		
	}

	@Override
	public VendorDTO patchVendorbyId(Long id, VendorDTO vendorDto) {
			if(vendorDto.equals(new VendorDTO())) {
				throw new ResourceNotFoundException();
			}
			Vendor vendortoChange = this.handleOptionalVendor(id, (vendorOptional)->vendorOptional.get());
			if(!vendorDto.getName().equals(null)) {
				vendortoChange.setName(vendorDto.getName());
			}
			return saveAndReturnVendor(vendortoChange);
		
	}
	private VendorDTO saveAndReturnVendor(Vendor vendor) {
		VendorDTO vendorSaved = mapper.vendorToVendorDto(vendorRepository.save(vendor));
		return vendorSaved;
	}
	private <T>  T handleOptionalVendor(Long id, Function<Optional<Vendor>, T> function){
		Optional<Vendor> vendorOptional = vendorRepository.findById(id);
		if(vendorOptional.isPresent()) {
			return  function.apply(vendorOptional);
		} else {
			throw new ResourceNotFoundException();
		}
	}
}
