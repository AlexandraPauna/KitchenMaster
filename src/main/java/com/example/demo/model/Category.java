package com.example.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name")
    @Length(min = 3, message = "*Category name must have at least 3 characters!")
    @NotEmpty(message = "*Please provide a category name!")
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<Recipe>();

    /*public String getIdAsString() {
        return new Integer(category_id).toString();
    }*/
}
