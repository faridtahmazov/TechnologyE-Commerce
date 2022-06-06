package com.company.controller;

import com.company.global.GlobalData;
import com.company.service.CategoryService;
import com.company.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "index";
    }

    @GetMapping("/shop")
    public String getShop(Model model){
        model.addAttribute("categories", categoryService.findAllCategory());
        model.addAttribute("products", productService.findAllProduct());
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String getShopByCategory(Model model, @PathVariable int id){
        model.addAttribute("categories", categoryService.findAllCategory());
        model.addAttribute("products", productService.findAllProductsByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String getViewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.findProductById(id));
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "viewProduct";
    }
}
