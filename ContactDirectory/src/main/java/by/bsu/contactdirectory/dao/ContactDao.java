package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 04.09.2016.
 */
public class ContactDao extends AbstractDao {

    private static ContactDao instance = new ContactDao();

    private static final String CREATE = "INSERT INTO `contact`(`first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, " +
            "`country_id`, `marital_status`, `web_site`, `email`, `place_of_work`) VALUES (?, ?, ?, ?, ?, " +
            "(SELECT `id` FROM `country` WHERE `name` = ? LIMIT 1), ?, ?, ?, ?);";
	    private static final String SELECT = "SELECT `contact`.`id`, `first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, " +
	            "`name`, `marital_status`, `web_site`, `email`, `place_of_work` FROM `contact` LEFT JOIN `country` ON `contact`.`country_id` = `country`.`id`";
    private static final String SELECT_LIST = SELECT + " LIMIT ?, ?;";
    private static final String SELECT_BY_BIRTHDAY = SELECT + " WHERE EXTRACT(DAY FROM `birthdate`) = EXTRACT(DAY FROM curdate()) " +
    		"AND EXTRACT(MONTH FROM `birthdate`) = EXTRACT(MONTH FROM curdate());";
    private static final String SELECT_SINGLE = SELECT + " WHERE `contact`.`id` = ? LIMIT 1;";
    private static final String UPDATE = "UPDATE `contact` SET `first_name` = ?, `last_name` = ?, `patronymic` = ?, `birth_date` = ?, " +
            "`gender` = ?, `country_id` = (SELECT `name` FROM `country` WHERE `id` = ?), `marital_status` = ?, " +
            "`web_site` = ?, `email` = ?, `place_of_work` = ? WHERE `id` = ?;";
    private static final String DELETE = "DELETE FROM `contact` WHERE `id` = ?;";
    private static final String SELECT_AMOUNT = "SELECT COUNT(`id`) FROM `contact`;";
    private static final String LAST_ID = "SELECT LAST_INSERT_ID();";

    private ContactDao() {}

    public static ContactDao getInstance() { return instance; }

    private Contact parse(ResultSet rs) throws SQLException{
        Contact contact = new Contact();

        contact.setId(rs.getInt(1));
        contact.setFirstName(rs.getString(2));
        contact.setLastName(rs.getString(3));
        contact.setPatronymic(rs.getString(4));

        Date date = rs.getDate(5);
        if (date == null) {
        	contact.setBirthDate(null);
        } else {
        	Calendar birthDate = Calendar.getInstance();
	        birthDate.setTimeInMillis(rs.getDate(5).getTime());
	        contact.setBirthDate(birthDate);
        }
        
        String buf = rs.getString(6);
        if (buf == null) {
        	contact.setGender(null);
        } else {
        	contact.setGender(Gender.valueOf(buf));
        }        
        contact.setCitizenship(rs.getString(7));
        
        buf = rs.getString(8);
        if (buf == null) {
        	contact.setMaritalStatus(null);
        } else {
        	contact.setMaritalStatus(MaritalStatus.valueOf(buf));
        }        
        contact.setWebSite(rs.getString(9));
        contact.setEmail(rs.getString(10));
        contact.setPlaceOfWork(rs.getString(11));

        return contact;
    }

    public int create(Contact contact) throws DaoException {
        int id = 0;
        try (Connection cn = getConnection(); 
        		PreparedStatement st = cn.prepareStatement(CREATE); 
        		PreparedStatement st2 = cn.prepareStatement(LAST_ID)) {
            st.setString(1, contact.getFirstName());
            st.setString(2, contact.getLastName());
            System.out.println(contact.getPatronymic());
            if (contact.getPatronymic() == null) {
            	st.setNull(3, Types.VARCHAR);
            } else {
            	 st.setString(3, contact.getPatronymic());
            }
            if (contact.getBirthDate() == null) {
            	st.setNull(4, Types.DATE);
            } else {
            	st.setDate(4, new java.sql.Date(contact.getBirthDate().getTimeInMillis()));
            }
            if (contact.getGender() == null) {
            	st.setNull(5, Types.OTHER);
            } else {
            	st.setString(5, contact.getGender().toString());
            }
            if (contact.getCitizenship() == null) {
            	st.setNull(6, Types.VARCHAR);
            } else {
            	 st.setString(6, contact.getCitizenship());
            }
            if (contact.getMaritalStatus() == null) {
            	st.setNull(7, Types.OTHER);
            } else {
            	st.setString(7, contact.getMaritalStatus().toString());
            }            
            if (contact.getWebSite() == null) {
            	st.setNull(8, Types.VARCHAR);
            } else {
            	 st.setString(8, contact.getWebSite());
            }
            if (contact.getEmail() == null) {
            	st.setNull(9, Types.VARCHAR);
            } else {
            	 st.setString(9, contact.getEmail());
            }
            if (contact.getPlaceOfWork() == null) {
            	st.setNull(10, Types.VARCHAR);
            } else {
            	 st.setString(10, contact.getPlaceOfWork());
            }
            st.executeUpdate();
            ResultSet rs = st2.executeQuery();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            throw new DaoException(ex);
        }
        System.out.println("Success add");
        return id;
    }

    public Contact findContactById(int id) throws DaoException {
        Contact contact = null;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_SINGLE)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                contact = parse(rs);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return contact;
    }

    public List<Contact> findContactList(int offset, int amount) throws DaoException {
        LinkedList<Contact> contactList = new LinkedList<Contact>();
        try (Connection cn = getConnection();PreparedStatement st = cn.prepareStatement(SELECT_LIST)) {
            st.setInt(1, offset);
            st.setLong(2, amount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contactList.add(parse(rs));
            }
        } catch (SQLException ex) {
        	ex.printStackTrace();
            throw new DaoException(ex);
        }
        return contactList;
    }
    
    public List<Contact> findContactListByBirthday() throws DaoException {
        LinkedList<Contact> contactList = new LinkedList<Contact>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_BY_BIRTHDAY)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contactList.add(parse(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return contactList;
    }

    public void update(Contact contact) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(UPDATE)) {
            st.setString(1, contact.getFirstName());
            st.setString(2, contact.getLastName());
            if (contact.getPatronymic() == null) {
            	st.setNull(3, Types.VARCHAR);
            } else {
            	 st.setString(3, contact.getPatronymic());
            }
            if (contact.getBirthDate() == null) {
            	st.setNull(4, Types.DATE);
            } else {
            	st.setDate(4, new java.sql.Date(contact.getBirthDate().getTimeInMillis()));
            }
            if (contact.getGender() == null) {
            	st.setNull(5, Types.OTHER);
            } else {
            	st.setString(5, contact.getGender().toString());
            }
            if (contact.getCitizenship() == null) {
            	st.setNull(6, Types.VARCHAR);
            } else {
            	 st.setString(6, contact.getCitizenship());
            }
            if (contact.getMaritalStatus() == null) {
            	st.setNull(7, Types.OTHER);
            } else {
            	st.setString(7, contact.getMaritalStatus().toString());
            }            
            if (contact.getWebSite() == null) {
            	st.setNull(8, Types.VARCHAR);
            } else {
            	 st.setString(8, contact.getWebSite());
            }
            if (contact.getEmail() == null) {
            	st.setNull(9, Types.VARCHAR);
            } else {
            	 st.setString(9, contact.getEmail());
            }
            if (contact.getPlaceOfWork() == null) {
            	st.setNull(10, Types.VARCHAR);
            } else {
            	 st.setString(10, contact.getPlaceOfWork());
            }
            st.setInt(11, contact.getId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public void delete(int id) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(DELETE)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }
    
    public int count() throws DaoException {
    	int amount = 0;
    	try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_AMOUNT)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    	return amount;
    }
}
