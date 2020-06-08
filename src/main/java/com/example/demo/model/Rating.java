package com.example.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private User user;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @Column(name = "serving")
    @Min(value = 1, message = "Must be greater than zero")
    @Max(value = 5, message = "Must be less than six")
    @NotNull(message = "*Please provide score!")
    private Integer score;

    @Column(name = "details")
    @Length(min = 5, message = "*Rating description must have at least 5 characters!")
    @NotNull(message = "*Please provide details!")
    private String details;
}
