package com.mzcteam01.mzcproject01be.common.base.category.repository;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
