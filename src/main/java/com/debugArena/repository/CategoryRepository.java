package com.debugArena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.debugArena.model.entity.LanguageEntity;

@Repository
public interface CategoryRepository extends JpaRepository<LanguageEntity, Long> {
}
