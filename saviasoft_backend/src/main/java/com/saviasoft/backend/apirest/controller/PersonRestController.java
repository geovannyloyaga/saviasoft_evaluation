package com.saviasoft.backend.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saviasoft.backend.apirest.dto.ResponseDto;
import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Person;
import com.saviasoft.backend.apirest.interfaces.IPersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonRestController {
	
	@Autowired
	private IPersonService personService;
	
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/getPersonList")
	public ResponseListDto<Person> getPersonList(){
		try {
			ResponseListDto<Person> personsFound = this.personService.getPersonList();
			return personsFound;
		} catch (Exception e) {
			return new ResponseListDto<Person>(409, "Error para obtener lista de personas", null, 0);
		}
	}

	@PostMapping("/save")
	public ResponseDto<Person> save(@RequestBody Person requestPeron){
		try {
			ResponseDto<Person> personCreated = this.personService.save(requestPeron);
			return personCreated;
		} catch (Exception e) {
			return new ResponseDto<Person>(409, "Error al crear la persona", null);
		}
	}
	
	@PutMapping("/update/{personId}")
	public ResponseDto<Person> update(@PathVariable Long personId, @RequestBody Person requestPerson){
		try {
			ResponseDto<Person> personCreated = this.personService.update(personId, requestPerson);
			return personCreated;
		} catch (Exception e) {
			return new ResponseDto<Person>(409, "Error al actualizar la persona", null);
		}
	}
	
	@DeleteMapping("/delete/{personId}")
	public ResponseDto<Person> delete(@PathVariable Long personId){
		try {
			ResponseDto<Person> personDeleted = this.personService.delete(personId);
			return personDeleted;
		} catch (Exception e) {
			return new ResponseDto<Person>(409, "Error al eliminar la persona", null);
		}
	}
}
