package com.company.controller;

import com.company.dto.ProductDTO;
import com.company.model.Category;
import com.company.model.Product;
import com.company.service.CategoryService;
import com.company.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AdminController {

    private static String uploadDIR = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String adminController(){
        return "adminHome";
    }

    //Category Section

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.findAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute() Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String getCatDel(@PathVariable int id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String getCatUpdate(@PathVariable int id, Model model){
        Category category = categoryService.findCategoryById(id);
        model.addAttribute("category", category);

        return "categoriesAdd";
    }

    //Product Session

    @GetMapping("/admin/products")
    public String getProduct(Model model){
        model.addAttribute("products", productService.findAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.findAllCategory());

        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute() ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCategory(categoryService.findCategoryById(productDTO.getCategoryId()));
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDIR, imageUUID);
            Files.write(fileNameAndPath, file.getBytes()); //File-i yazdirir ve qovlug ve adini qeyd edir
        }else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String getProductDel(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/{id}")
    public String getProductUpdate(@PathVariable Long id, Model model){
        Product product = productService.findProductById(id);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setImageName(product.getImageName());
        productDTO.setCategoryId(product.getCategory().getId());

        model.addAttribute("categories", categoryService.findAllCategory());
        model.addAttribute("productDTO", productDTO);

        return "productsAdd";
    }

}
