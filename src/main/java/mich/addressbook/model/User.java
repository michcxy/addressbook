package mich.addressbook.model;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {

    private String userId;

    @NotNull(message="Please enter your name")
    @Size(min=3, message="Name must be at least 3 characters")
    @Size(max=64, message="Name must be less than 64 characters")
    private String name;

    @NotNull(message="Please enter your address")
    @NotEmpty(message="Please enter your address")
    private String email;
    
    @NotNull(message="Please enter your phone number")
    @Pattern(regexp="^[0-9]{8,}$", message="Must be a valid phone number")
    private String phone;

    @Past(message="Date of birth must not be future")
    @NotNull(message="Please input your date of birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    
    public User() {
        this.userId = generateId();
    }

    public User(String name, String email, String phone, LocalDate dob) {
        this.userId = generateId();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public User(String userId) {
    }
    
    public String generateId(){
        return UUID.randomUUID().toString().substring(0,8);
    }

    public int getAge() {
        return Period.between(getDob(), LocalDate.now()).getYears();
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
            .add("userId", this.getUserId())
            .add("name", this.getName())
            .add("email", this.getEmail())
            .add("phone", this.getPhone())
            .add("dob", this.getDob().toString())
            .build();
    }

    public static User create(JsonObject o){
        User u = new User();
        u.setUserId(o.getString("userId"));
        u.setName(o.getString("name"));
        u.setEmail(o.getString("email"));
        u.setPhone(o.getString("phone"));
        u.setDob(LocalDate.parse(o.getString("dob")));
        return u;
    }
    
    public static User create(String json){
        return create(Json.createReader(new StringReader(json)).readObject());
    }

    public static User create(SqlRowSet rs) {
        User user = new User();

        user.setUserId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone")); 
        user.setDob(LocalDate.parse(rs.getString("dob")));
        return user;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("id", getUserId())
        .add("name", getName())
        .add("email", getEmail())
        .add("phone", getPhone())
        .add("dob", getDob().toString())
        .build();
    }
    
}
