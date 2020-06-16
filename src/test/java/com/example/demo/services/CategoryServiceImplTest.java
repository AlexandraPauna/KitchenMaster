package com.example.demo.services;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.repository.CategoryRepository;
import  com.example.demo.service.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void findCategories() {
        List<Category> categoriesRet = new ArrayList<Category>();
        Category category = new Category();
        category.setCategory_id(100);
        category.setName("Testing");
        categoriesRet.add(category);

        when(categoryRepository.findAll()).thenReturn(categoriesRet);
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(categories.size(), 1);
        verify(categoryRepository, times(1)).findAll();
    }
}
