package com.example.springSecurityApplication.repositories;


import com.example.springSecurityApplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>
    {
        // Находим продукты по части наименования без учета регистра
        List<Product> findByTitleContainingIgnoreCase(String name);

        // Мой метод вызова из таблицы Product category_id товаров, в контроллере указываем конкретный id, который нужно вывести
        @Query(value = "select * from product where category_id=?1", nativeQuery = true)
        List<Product> findByCategory(int category);

        // Мой метод сортировки цены "меньше чем"
        @Query(value = "select * from product where price<?1", nativeQuery = true)
        List<Product> findByPriorityLessThan(int priority);

        // Мой метод сортировки цены "больше чем"
        @Query(value = "select * from product where price>?1", nativeQuery = true)
        List<Product> findByPriorityGreaterThan(int priority);

        // Мой метод сортировка цены по возрастанию
        @Query(value = "select * from product where price=?1 order by n.priority ASC", nativeQuery = true)
        List<Product> findByTitleOrderByPriorityAsc(String price);

        // Мой метод сортировка цены по убыванию
        @Query(value = "select * from product where price=?1 order by n.created DESC", nativeQuery = true)
        List<Product> findByFeaturedOrderByCreatedDesc(boolean price);
    }
