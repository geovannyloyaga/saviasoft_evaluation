package com.saviasoft.backend.apirest.business.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saviasoft.backend.apirest.dao.CountryDao;
import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Country;
import com.saviasoft.backend.apirest.interfaces.ICountryService;

@Service
public class CountryService implements ICountryService {


	@Autowired
	private CountryDao countryDao;

	public void setPersonDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseListDto<Country> getCountryList() {
		ResponseListDto<Country> responseCountryList = new ResponseListDto<Country>(200, null, new ArrayList<>(), 0);
		List<Country> foundPersonList = null;
		try {
			foundPersonList = this.countryDao.getCountryList();
			responseCountryList.setResponseList(foundPersonList);
			responseCountryList.setCode(200);
			return responseCountryList;
		} catch (Exception e) {
			responseCountryList.setCode(409);
			responseCountryList.setError("Error al momento de obtener la lista de países");
			return responseCountryList;
		} finally {
			responseCountryList = null;
			foundPersonList = null;
		}
	}
}