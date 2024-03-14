package com.saviasoft.backend.apirest.business.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.saviasoft.backend.apirest.dao.PersonDao;
import com.saviasoft.backend.apirest.dto.PersonDto;
import com.saviasoft.backend.apirest.dto.ResponseDto;
import com.saviasoft.backend.apirest.dto.ResponseListDto;
import com.saviasoft.backend.apirest.entities.Country;
import com.saviasoft.backend.apirest.entities.Person;
import com.saviasoft.backend.apirest.interfaces.IPersonService;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private PersonDao personDao;

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public ResponseDto<Person> save(PersonDto person) {
		Person personCreated = null;
		ResponseDto<Person> responsePerson = new ResponseDto<Person>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			Country country = new Country();
			country.setId(person.getCountry_id());
			Person newPerson = new Person(null, person.getName(), person.getLastname(), person.getEmail(), person.getBirthday(), person.getSalary(), country);
			
			personCreated = this.personDao.save(newPerson);
			if (personCreated != null && personCreated.getId() != null) {
				responsePerson.setResponseObject(personCreated);
				this.transactionManager.commit(statusTransaction);
				responsePerson.setCode(200);
			} else {					
				this.transactionManager.rollback(statusTransaction);
				responsePerson.setCode(409);
				responsePerson.setError("Error al momento de crear la persona");
			}
			return responsePerson;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responsePerson.setCode(409);
			responsePerson.setError("Error al momento de crear la persona");
			return responsePerson;
		} finally {
			personCreated = null;
			responsePerson = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}

	@Override
	public ResponseDto<Person> update(Long idPerson, Person person) {
		Person personUpdated = null;
		ResponseDto<Person> responsePerson = new ResponseDto<Person>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			if (this.personDao.findById(idPerson) != null) {
				personUpdated = this.personDao.update(person);
				if (personUpdated != null && personUpdated.getId() != null) {
					responsePerson.setResponseObject(personUpdated);
					this.transactionManager.commit(statusTransaction);
					responsePerson.setCode(200);	
				} else {
					this.transactionManager.rollback(statusTransaction);
					responsePerson.setCode(409);
					responsePerson.setError("Error al momento de crear la persona");
				}
			} else {
				responsePerson.setCode(409);
				responsePerson.setError("Error al momento de crear la persona");
			}
			return responsePerson;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responsePerson.setCode(409);
			responsePerson.setError("Error al momento de crear la persona");
			return responsePerson;
		} finally {
			personUpdated = null;
			responsePerson = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}

	@Override
	public ResponseDto<Person> delete(Long personId) {
		Person personDeleted = null;
		Boolean isDeleted = false;
		ResponseDto<Person> responsePerson = new ResponseDto<Person>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			personDeleted = this.personDao.findById(personId);
			isDeleted = this.personDao.delete(personDeleted);
			if (isDeleted) {
				this.transactionManager.commit(statusTransaction);
				responsePerson.setCode(200);	
			} else {
				this.transactionManager.rollback(statusTransaction);
				responsePerson.setCode(409);
				responsePerson.setError("Error al momento de eliminar la persona");
			}
			return responsePerson;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responsePerson.setCode(409);
			responsePerson.setError("Error al momento de eliminar la persona");
			return responsePerson;
		} finally {
			personDeleted = null;
			isDeleted = null;
			responsePerson = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseListDto<Person> getPersonList() {
		ResponseListDto<Person> responsePersonList = new ResponseListDto<Person>(200, null, new ArrayList<>(), 0);
		List<Person> foundPersonList = null;
		try {
			foundPersonList = this.personDao.getPersonList();
			responsePersonList.setResponseList(foundPersonList);
			responsePersonList.setCode(200);
			return responsePersonList;
		} catch (Exception e) {
			responsePersonList.setCode(409);
			responsePersonList.setError("Error al momento de obtener la lista de personas");
			return responsePersonList;
		} finally {
			responsePersonList = null;
			foundPersonList = null;
		}
	}

}
