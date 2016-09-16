package by.bsu.contactdirectory.service;

import by.bsu.contactdirectory.dao.AddressDao;
import by.bsu.contactdirectory.dao.DaoException;
import by.bsu.contactdirectory.dao.SearchDao;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.SearchObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class SearchService {

    private String lastCondition;

    public boolean isResultEmpty(SearchObject so) {
        prepareSearchObject(so);
        String condition = buildCondition(so);
        int resultAmount = 0;
        try {
            resultAmount = SearchDao.getInstance().count(so, condition);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return resultAmount == 0;
    }

    public int getPageAmount(SearchObject so) {
        int amount = 0;
        try {
            amount = SearchDao.getInstance().count(so, lastCondition);
        } catch (DaoException ex) {

        }
        if (amount % ContactService.contactAmountPerPage == 0) {
            return amount / ContactService.contactAmountPerPage;
        }
        return amount / ContactService.contactAmountPerPage + 1;
    }

    public List<Contact> searchContacts(SearchObject so, int page) {
        String condition = buildCondition(so);
        lastCondition = condition;
        List<Contact> contacts;
        try {
            int offset = (page - 1) * ContactService.contactAmountPerPage;
            contacts = SearchDao.getInstance().search(so, condition, offset, ContactService.contactAmountPerPage);
        } catch (DaoException ex) {
            ex.printStackTrace();
            contacts = new LinkedList<>();
        }

        for(Contact contact : contacts) {
            try {
                contact.setAddress(AddressDao.getInstance().findById(contact.getId()));
            } catch (DaoException ex) {
                //add log
            }
        }

        return contacts;
    }

    private void prepareSearchObject(SearchObject so) {
        if (so == null) {
            return;
        }
        so.setFirstName(preparedStringField(so.getFirstName()));
        so.setLastName(preparedStringField(so.getLastName()));
        so.setPatronymic(preparedStringField(so.getPatronymic()));
        so.setCity(preparedStringField(so.getCity()));
        so.setLocalAddress(preparedStringField(so.getLocalAddress()));
        so.setIndex(preparedStringField(so.getIndex()));
        so.setCitizenship(preparedStringField(so.getCitizenship()));
        so.setCountry(preparedStringField(so.getCountry()));
    }

    private String preparedStringField(String field) {
        if (field == null) {
            return null;
        }
        if (field.isEmpty()) {
            return null;
        }
        return field
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
    }

    private String buildCondition(SearchObject so) {
        StringBuilder st = new StringBuilder();

        if(so.getFirstName() != null) {
            st.append("`first_name` LIKE ? ESCAPE '!'");
        }

        if(so.getLastName() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`last_name` LIKE ? ESCAPE '!'");
        }

        if(so.getPatronymic() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`patronymic` LIKE ? ESCAPE '!'");
        }

        if(so.getBirthDateBigger() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`birth_date` > ?");
        }

        if(so.getBirthDateLess() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`birth_date` < ?");
        }

        if(so.getGender() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`gender` = ?");
        }

        if(so.getCitizenship() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`citizenship`.`name` = ?");
        }

        if(so.getMaritalStatus() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`marital_status` = ?");
        }
        if(so.getCountry() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`addr_country`.`name` = ?");
        }

        if(so.getCity() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`city` LIKE ? ESCAPE '!'");
        }

        if(so.getLocalAddress() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`local_address` LIKE ? ESCAPE '!'");
        }

        if(so.getIndex() != null) {
            if (st.length() != 0) {
                st.append(" AND ");
            }
            st.append("`index` LIKE ? ESCAPE '!'");
        }

        System.out.println(st.toString());

        if (st.length() == 0) {
            return "";
        } else {
            return " WHERE " + st.toString();
        }
    }
}
