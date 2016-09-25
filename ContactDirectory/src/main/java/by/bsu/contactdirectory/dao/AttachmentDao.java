package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 05.09.2016.
 */
public class AttachmentDao extends AbstractDao {

    private static AttachmentDao instance = new AttachmentDao();

    private static final String CREATE = "INSERT INTO `attachment`(`path`, `download_date`, `comment`, `contact_id`, `name`) " +
            "VALUES (?, curdate(), ?, ?, ?);";
    private static final String UPDATE = "UPDATE `attachment` SET `name` = ?, `comment` = ? WHERE `id` = ?;";
    private static final String DELETE = "DELETE FROM `attachment` WHERE `id` = ?;";
    private static final String SELECT = "SELECT `id`, `path`, `download_date`, `comment`, `contact_id`, `name` FROM `attachment`";
    private static final String SELECT_LIST = SELECT + " WHERE `contact_id` = ?;";
    private static final String SELECT_SINGLE = SELECT + " WHERE `id` = ? LIMIT 1;";

    private AttachmentDao() {}

    public static AttachmentDao getInstance() { return instance; }

    private Attachment parse(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();

        attachment.setId(rs.getInt(1));
        attachment.setPath(rs.getString(2));

        Calendar downloadDate = Calendar.getInstance();
        downloadDate.setTimeInMillis(rs.getDate(3).getTime());
        attachment.setDownloadDate(downloadDate);

        attachment.setComment(rs.getString(4));
        attachment.setContactId(rs.getInt(5));
        attachment.setName(rs.getString(6));

        return attachment;
    }

    void create(Attachment attachment, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(CREATE)) {
            st.setString(1, attachment.getPath());
            if (attachment.getComment() == null) {
            	st.setNull(2, Types.VARCHAR);
            } else {
            	st.setString(2, attachment.getComment());
            }
            st.setInt(3, attachment.getContactId());
            st.setString(4, attachment.getName());

            st.executeUpdate();
        }
    }

    void update(Attachment attachment, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(UPDATE)) {
            st.setString(1, attachment.getName());
            if (attachment.getComment() == null) {
            	st.setNull(2, Types.VARCHAR);
            } else {
            	st.setString(2, attachment.getComment());
            }

            st.executeUpdate();
        }
    }

    public void delete(int id, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(DELETE)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    public Attachment findById(int id) throws DaoException {
        Attachment attachment = null;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_SINGLE)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                attachment = parse(rs);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return attachment;
    }

    public List<Attachment> findByContact(int contactId) throws DaoException {
        LinkedList<Attachment> phones = new LinkedList<Attachment>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_LIST)) {
            st.setInt(1, contactId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                phones.add(parse(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return phones;
    }

}
