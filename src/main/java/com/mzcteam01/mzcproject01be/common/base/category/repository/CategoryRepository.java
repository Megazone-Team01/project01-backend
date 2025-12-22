package com.mzcteam01.mzcproject01be.common.base.category.repository;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCode(String code);
    List<Category> findAllByParentIdAndDeletedAtIsNull( Integer parentId );
    List<Category> findAllByParentIsNullAndDeletedAtIsNull();
}
