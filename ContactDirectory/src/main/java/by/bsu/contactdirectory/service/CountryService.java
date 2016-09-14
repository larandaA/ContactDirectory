package by.bsu.contactdirectory.service;

import java.util.LinkedList;
import java.util.List;

import by.bsu.contactdirectory.dao.CountryDao;
import by.bsu.contactdirectory.dao.DaoException;

public class CountryService {
	
	public CountryService() {}
	
	public List<String> getCountryNames() {
		List<String> countries = null;
		try {
			countries = CountryDao.getInstance().getNames();
		} catch (DaoException ex) {
			countries = new LinkedList<>();
		}
		return countries;
	}

}
