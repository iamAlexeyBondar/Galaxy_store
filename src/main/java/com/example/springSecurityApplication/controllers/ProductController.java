package com.example.springSecurityApplication.controllers;

import com.example.springSecurityApplication.repositories.ProductRepository;
import com.example.springSecurityApplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController
    {
        private final ProductRepository productRepository;
        private final ProductService productService;
        @Autowired
        public ProductController(ProductRepository productRepository, ProductService productService)
            {
                this.productRepository = productRepository;
                this.productService = productService;
            }
        @GetMapping("")
        public String getAllProduct(Model model)
            {
                model.addAttribute("products", productService.getAllProduct());
                return "/product/product";
            }
        @GetMapping("/info/{id}")
        public String infoUser(@PathVariable("id") int id, Model model)
            {
                model.addAttribute("product", productService.getProductId(id));
                return "product/infoProduct";
            }
        @PostMapping("/search")
        public String productSearch
                (
                        // 1. if (!contact.isEmpty())
                        @RequestParam(value = "contact", required = false, defaultValue = "") String contact, Model model,

                        // 2. else (price.equals("sorted_by_descending_price"))
                        // 3. else (price.equals("sorted_by_ascending_price"))
                        @RequestParam(value = "price", required = false, defaultValue = "") String price,

                        // 4. else (!ot.isEmpty())
                        @RequestParam("ot") String ot,

                        // 5. else (!Do.isEmpty())
                        @RequestParam("do") String Do,

                        // 6. else
                        @RequestParam("search") String search
                )
            {
                // 1. Сортировка по категории мебели (кресло, кровать, диван). Данные из таблицы Product колонки Category_id. На сайте в виде гиперссылки.
                if (!contact.isEmpty())
                    {
                        if (contact.equals("kreslo"))
                            {
                                model.addAttribute("search_product", productRepository.findByCategory(1));
                            }
                        else if (contact.equals("krovat"))
                            {
                                model.addAttribute("search_product", productRepository.findByCategory(2));
                            }
                        else if (contact.equals("divan"))
                            {
                                model.addAttribute("search_product", productRepository.findByCategory(3));
                            }
                    }
                // 2. НЕ РАБОТАЕТ. Сортировка цены от большего к меньшему. Данные из таблицы Product колонки Price. На сайте в виде input.
                else if (price.equals("ubivanie"))
                    {
                        model.addAttribute("ubivanie", productRepository.findByFeaturedOrderByCreatedDesc(Boolean.valueOf(String.valueOf(1))));
                    }
                // 3. НЕ РАБОТАЕТ. Сортировка цены от меньшего к большему. Данные из таблицы Product колонки Price. На сайте в виде input.
                else if (price.equals("vozrastanie"))
                    {
                        model.addAttribute("vozrastanie", productRepository.findByTitleOrderByPriorityAsc(String.valueOf(1)));
                    }
                // 4. НЕ РАБОТАЕТ. Сортировка цены больше чем. Данные из таблицы Product колонки Price. На сайте в виде гиперссылки.
                else if (!ot.isEmpty())
                    {
                        model.addAttribute("ot", productRepository.findByPriorityGreaterThan(3));
                    }
                // 5. НЕ РАБОТАЕТ. Сортировка цены маньше чем. Данные из таблицы Product колонки Price. На сайте в виде гиперссылки.
                else if (!Do.isEmpty())
                    {
                        model.addAttribute("do", productRepository.findByPriorityLessThan(3));
                    }
                // 6. Поиск по наименованию товара. Данные из таблицы Product колонки Title. На сайте в виде input.
                else
                    {
                        model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
                    }

                model.addAttribute("value_search", search);
                model.addAttribute("value_price_ot", ot);
                model.addAttribute("value_price_do", Do);
                model.addAttribute("products", productService.getAllProduct());
                return "/product/search";
            }
    }

