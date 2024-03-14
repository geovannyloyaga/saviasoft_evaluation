package com.saviasoft.backend.apirest.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saviasoft.backend.apirest.entities.Country;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CountryDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Country> getCountryList() {
        List<Country> countries = null;
		try {
	        countries = entityManager.createQuery("FROM Country", Country.class).getResultList();
	        return countries;
		} catch (Exception e) {
			return countries;
		}  finally {
			countries = null;
		}
	}
}
