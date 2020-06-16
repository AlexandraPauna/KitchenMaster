package com.example.demo.configuration;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DbInit {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMIN");

        Role roleUser = new Role();
        roleUser.setRole("USER");

        Role roleUserExists = roleService.findRoleByUserName(roleUser.getRole());
        if(roleUserExists == null){
            roleRepository.save(roleUser);
        }

        Role roleAdminExists = roleService.findRoleByUserName(roleAdmin.getRole());
        if(roleAdminExists == null){
            roleRepository.save(roleAdmin);
        }

        User admin = new User();
        admin.setPassword("1234%Asd");
        admin.setFirstName("AdminF"); admin.setLastName("AdminL"); admin.setEmail("admin@admin.com"); admin.setActive(true);
        admin.setUserName("Admin");
        Role adminRole = roleRepository.findByRole("ADMIN");
        admin.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
        User userExists = userService.findUserByUserName(admin.getUserName());
        if(userExists == null){
            userService.saveFirstUsers(admin);
        }
    }
}
