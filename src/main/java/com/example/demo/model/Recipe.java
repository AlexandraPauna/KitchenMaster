package com.example.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date date;

    @Column(name = "name")
    @Length(min = 3, message = "*Recipe name must have at least 3 characters!")
    @NotEmpty(message = "*Please provide a recipe name!")
    private String name;

    @Column(name = "description")
    @Length(min = 5, message = "*Recipe description must have at least 3 characters!")
    @NotEmpty(message = "*Please provide a recipe description!")
    private String description;

    @Column(name = "calories")
    @Min(value = 1, message = "Must be greater than zero")
    @NotNull(message = "*Please provide calories for recipe!")
    private Integer calories;

    @Column(name = "serving")
    @Min(value = 1, message = "Must be greater than zero")
    @NotNull(message = "*Please provide number of servings!")
    private Integer serving;

    @Column(name = "preparation_time")
    @Min(value = 1, message = "Must be greater than zero")
    @NotNull(message = "*Please provide the time needed for preparation!")
    private Integer preparation_time;

    @OneToOne(cascade = CascadeType.ALL)
    private Info info;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<Category>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score")
    @Value("${score:0}")
    private Integer score;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Rating> ratings = new HashSet<Rating>();
}
