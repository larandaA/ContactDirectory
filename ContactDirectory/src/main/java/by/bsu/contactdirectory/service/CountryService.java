package by.bsu.contactdirectory.service;

import java.util.LinkedList;
import java.util.List;

import by.bsu.contactdirectory.dao.CountryDao;
import by.bsu.contactdirectory.dao.DaoException;

public class CountryService {
	
	public CountryService() {}
	
	public List<String> getCountryNames() throws ServiceServerException {
		List<String> countries = null;
		try {
			countries = CountryDao.getInstance().getNames();
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		return countries;
	}

	public List<Integer> getCountryCodes() throws ServiceServerException {
		List<Integer> codes = null;
		try {
			codes = CountryDao.getInstance().getCodes();
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		return codes;
	}

}
