package com.keisarmind.dao;

import com.keisarmind.config.DatabaseConfig;
import com.keisarmind.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContatoDAO {

    private final Connection connection;

    public ContactDAOImpl() {
        connection = DatabaseConfig.getConnection();
        try {
            connection.setAutoCommit(false); // Ensure auto-commit is disabled
        } catch (SQLException e) {
            throw new RuntimeException("Error configuring database connection", e);
        }
    }

    @Override
    public List<Contact> getAllcontacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contatos";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setAge(rs.getInt("age"));
                contacts.add(contact);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching contacts", ex);
        }

        return contacts;
    }

    @Override
    public Contact findContactById(int id) {
        Contact contact = null;
        String sql = "SELECT * FROM contatos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setAge(rs.getInt("age"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error finding contact by ID", ex);
        }

        return contact;
    }

    @Override
    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contatos (name, email, age) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setInt(3, contact.getAge());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            throw new RuntimeException("Error saving contact", ex);
        }
    }

    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contatos SET name = ?, email = ?, age = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setInt(3, contact.getAge());
            stmt.setInt(4, contact.getId());

            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            throw new RuntimeException("Error updating contact", ex);
        }
    }

    @Override
    public void deleteContact(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            throw new RuntimeException("Error deleting contact", ex);
        }
    }
}
