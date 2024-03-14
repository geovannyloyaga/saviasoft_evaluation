package com.saviasoft.backend.apirest.interfaces;

import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Country;

public interface ICountryService {

	public ResponseListDto<Country> getCountryList();
}
