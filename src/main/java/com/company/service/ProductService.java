package com.company.service;

import com.company.model.Product;
import com.company.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public Product findProductById(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            return productOptional.get();
        }else{
            throw new NullPointerException("Product not found in this id = " + id + "!");
        }
    }
}
