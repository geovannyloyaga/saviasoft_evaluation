package com.saviasoft.backend.apirest.interfaces;

import com.saviasoft.backend.apirest.dto.PersonDto;
import com.saviasoft.backend.apirest.dto.ResponseDto;
import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Person;

public interface IPersonService {

	public ResponseDto<Person> save(PersonDto person);
	
	public ResponseDto<Person> update(Long personId, Person person);
	
	public ResponseDto<Person> delete(Long personId);
	
	public ResponseListDto<Person> getPersonList();
}
