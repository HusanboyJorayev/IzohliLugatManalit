package com.example.izohlilugatmanalit.repository;

import com.example.izohlilugatmanalit.entity.DayWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayWordRepository extends JpaRepository<DayWord, Integer> {
    Optional<DayWord> findByIdAndDeletedAtIsNull(Integer id);


    @Query(
            nativeQuery = true,
            value = "select * from dayWord"
    )
    List<DayWord> getAllByQuery();

    Page<DayWord> findAllByDeletedAtIsNull(Pageable pageable);
}
