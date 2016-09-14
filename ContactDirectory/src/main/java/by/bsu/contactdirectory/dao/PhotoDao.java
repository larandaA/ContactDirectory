package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alexandra on 05.09.2016.
 */
public class PhotoDao extends AbstractDao {

    private static PhotoDao instance = new PhotoDao();

    private static final String CREATE = "INSERT INTO `photo`(`path`, `contact_id`) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE `photo` SET `path` = ? WHERE `contact_id` = ?;";
    private static final String DELETE = "DELETE FROM `photo` WHERE `contact_id` = ?;";
    private static final String SELECT = "SELECT `path`, `contact_id` FROM `photo` WHERE `contact_id` = ? LIMIT 1;";

    private PhotoDao() {}

    public static PhotoDao getInstance() { return instance; }

    private Photo parse(ResultSet rs) throws SQLException {
        Photo photo = new Photo();

        photo.setPath(rs.getString(1));
        photo.setContactId(rs.getInt(2));

        return photo;
    }

    public void create(Photo photo) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(CREATE)) {
            st.setString(1, photo.getPath());
            st.setInt(2, photo.getContactId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public void update(Photo photo) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(UPDATE)) {
            st.setString(1, photo.getPath());
            st.setInt(2, photo.getContactId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public void delete(int contactId) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(DELETE)) {
            st.setInt(1, contactId);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public Photo findById(int contactId) throws DaoException {
        Photo photo = null;
        try (Connection cn = getConnection();PreparedStatement st = cn.prepareStatement(SELECT)) {
            st.setInt(1, contactId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                photo = parse(rs);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return photo;
    }
}
