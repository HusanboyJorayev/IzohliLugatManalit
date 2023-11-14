package com.example.izohlilugatmanalit.repository;

import com.example.izohlilugatmanalit.entity.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudioRepository extends JpaRepository<Audio,Integer> {

    Optional<Audio> findByIdAndDeletedAtIsNull(Integer id);
}
