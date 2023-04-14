package mich.addressbook.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import mich.addressbook.model.User;
import static mich.addressbook.repository.DBQueries.*;

import java.util.ArrayList;
import java.util.List;;

@Repository
public class AddressBookRepository {

    @Autowired
    RedisTemplate<String, String> redis;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveUser(User user){
        redis.opsForValue().set(user.getUserId().toString(), user.toJSON().toString());
    }

    public User loadUser(String userId){

        return User.create(redis.opsForValue().get(userId));
    }

    public User createUser(User user) {
        
            jdbcTemplate.update(INSERT_NEW_USER, user.getUserId(), user.getName(), user.getEmail(), user.getPhone(), user.getDob().toString());

            return user;
        }

    public List<User> getUserByUserId(String userId) {
            List<User> users = new ArrayList<User>();
            SqlRowSet rs = null;
    
            rs = jdbcTemplate.queryForRowSet(SELECT_USER_BY_ID, userId);
    
            while (rs.next())
                users.add(User.create(rs));
            return users;
        }
    
}
