package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 100000)
    @NotEmpty(message = "*Please provide a description!")
    private String info;


    @OneToOne
    private Recipe recipe;
}
