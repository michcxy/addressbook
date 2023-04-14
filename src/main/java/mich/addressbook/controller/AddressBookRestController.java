package mich.addressbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import mich.addressbook.model.User;
import mich.addressbook.repository.AddressBookRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressBookRestController {
    
    @Autowired
    AddressBookRepository repo;

    @GetMapping("/user")
    public ResponseEntity<String> getUserById(@RequestParam String userId){
        List<User> user = repo.getUserByUserId(userId);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (User u : user) {
            arrayBuilder.add(u.toJson());
            }
        JsonArray result = arrayBuilder.build();

        if(user.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error_code' : " + HttpStatus.NOT_FOUND + "'}");
    

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());

    }

}
