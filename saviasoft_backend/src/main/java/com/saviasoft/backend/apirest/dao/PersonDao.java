package com.saviasoft.backend.apirest.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saviasoft.backend.apirest.entities.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PersonDao {

	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Person> getPersonList() {
        List<Person> articles = null;
		try {
	        articles = entityManager.createQuery("FROM Person", Person.class).getResultList();
	        return articles;
		} catch (Exception e) {
			return articles;
		}  finally {
			articles = null;
		}
	}
	
	public Person findById(Long id) {
		Person personFound = null;
        try {
			personFound = entityManager.find(Person.class, id);
			return personFound;
        } catch (Exception e) {
			return personFound;
		} finally {
			personFound = null;
		}
    }
	
	public Person save(Person person) {
		Person personCreated = null;
        try {
        	entityManager.persist(person);
        	entityManager.flush();
			personCreated = person;
			return personCreated;
        } catch (Exception e) {
			return personCreated;
		} finally {
			personCreated = null;
		}
    }

    public Person update(Person person) {
		Person personUpdated = null;
        try {
        	entityManager.merge(person);
        	entityManager.flush();
			personUpdated = person;
			return personUpdated;
        } catch (Exception e) {
			return personUpdated;
		} finally {
			personUpdated = null;
		}
    }

    public boolean delete(Person person) {
    	boolean personDeleted = false;
        try {
        	entityManager.remove(person);
        	entityManager.flush();
        	personDeleted = true;
			return personDeleted;
        } catch (Exception e) {
			return personDeleted;
		}
    }
}
