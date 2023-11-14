package com.example.izohlilugatmanalit.repository;

import com.example.izohlilugatmanalit.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    Optional<Word> findByIdAndDeletedAtIsNull(Integer id);


    @Query(
            nativeQuery = true,
            value = "select * from word"
    )
    List<Word> getAllByQuery();

    Page<Word> findAllByDeletedAtIsNull(Pageable pageable);
}
