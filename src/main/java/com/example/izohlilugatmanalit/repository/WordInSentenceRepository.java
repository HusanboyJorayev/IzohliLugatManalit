package com.example.izohlilugatmanalit.repository;


import com.example.izohlilugatmanalit.entity.WordInSentence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordInSentenceRepository extends JpaRepository<WordInSentence,Integer> {
    Optional<WordInSentence> findByIdAndDeletedAtIsNull(Integer id);


    @Query(
            nativeQuery = true,
            value = "select * from wordInSentence"
    )
    List<WordInSentence> getAllByQuery();

    Page<WordInSentence> findAllByDeletedAtIsNull(Pageable pageable);
}
