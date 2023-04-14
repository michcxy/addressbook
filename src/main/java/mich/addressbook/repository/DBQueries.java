package mich.addressbook.repository;

public class DBQueries {
    public static final String INSERT_NEW_USER ="INSERT INTO user (id, name, email, phone, dob) VALUES (?, ?, ?, ?, ?)";
    public static final String  SELECT_USER_BY_ID = "select id, name, email, phone, dob from user where id = ?";
}
