package mich.addressbook.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import mich.addressbook.model.User;
import mich.addressbook.repository.AddressBookRepository;


@Controller
public class AddressBookController {

    @Autowired
    AddressBookRepository repo;

    @GetMapping(path="/")
    public String getIndex(Model model, HttpSession session){

        model.addAttribute("user", new User());
        return "view0";
    }

    @PostMapping(path="/contact")
    public String showContact(Model model, HttpSession session, @Valid User user, BindingResult bindings){
        if(user.getAge() < 10){
            bindings.addError(new FieldError("user", "dob", "Have to be over 10 years old"));
        }
        else if(user.getAge() > 100){
            bindings.addError(new FieldError("user", "dob", "Have to be under 100 years old"));
        }
        if(bindings.hasErrors()){
            return "view0";
        }
        
        repo.saveUser(user);
        repo.createUser(user);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "view1";
        }

    @GetMapping(path="/contact/{userId}")
    public String loadContact(Model model, HttpSession session, @PathVariable String userId){
        model.addAttribute("user", repo.loadUser(userId));
        return "view1";
    }


}
