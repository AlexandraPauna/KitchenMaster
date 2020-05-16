package com.example.demo.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
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
}
