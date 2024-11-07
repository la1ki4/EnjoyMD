package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Set<Category> findAllById(Integer categoryId);
}
