package com.example.demo.repositories;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"mysql"})
public class CategoryRepositoryTestIntegration {

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByName() {
        Optional<Category> optCategory = categoryRepository.findByName("Vegan");
        assertEquals(Optional.ofNullable(optCategory.get().getName()), "Vegan");
    }
}