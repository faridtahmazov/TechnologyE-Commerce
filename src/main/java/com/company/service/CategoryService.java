package com.company.service;

import com.company.model.Category;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }

    public Category findCategoryById(int id){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()){
            return categoryOptional.get();
        }else{
            throw new NullPointerException("Category not found in this id = " + id + "!");
        }
    }
}
