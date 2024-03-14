package com.saviasoft.backend.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Country;
import com.saviasoft.backend.apirest.interfaces.ICountryService;

@RestController
@RequestMapping("api/countries")
public class CountryRestController {
	
	@Autowired
	private ICountryService countryService;
	
	public void setCountryService(ICountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("/getCountryList")
	public ResponseListDto<Country> getPersonList(){
		try {
			ResponseListDto<Country> personsFound = this.countryService.getCountryList();
			return personsFound;
		} catch (Exception e) {
			return new ResponseListDto<Country>(409, "Error para obtener lista de paises", null, 0);
		}
	}
}
