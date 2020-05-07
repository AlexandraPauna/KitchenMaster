package com.example.demo.model;

import com.example.demo.constraints.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    @Length(min = 3, message = "*Your user name must have at least 3 characters!")
    @NotEmpty(message = "*Please provide a user name!")
    private String userName;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Emai!")
    @NotEmpty(message = "*Please provide an email!")
    private String email;

    @Column(name = "password")
    //@Length(min = 5, message = "*Va rugam introduceti o parola de cel putin 5 caractere!")
    @NotEmpty(message = "*Please provide your passwor!")
    @ValidPassword
    private String password;

    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Recipe> recipes = new HashSet<Recipe>();

}
