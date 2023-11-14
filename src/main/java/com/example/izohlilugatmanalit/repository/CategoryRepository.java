package com.example.izohlilugatmanalit.repository;

import com.example.izohlilugatmanalit.entity.Category;
import com.example.izohlilugatmanalit.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndDeletedAtIsNull(Integer id);


    @Query(
            nativeQuery = true,
            value = "select * from category"
    )
    List<Category> getAllByQuery();

    Page<Category> findAllByDeletedAtIsNull(Pageable pageable);
}
