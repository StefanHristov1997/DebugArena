package com.debugArena.repository;

import com.debugArena.model.entity.LanguageEntity;
import com.debugArena.model.enums.LanguageEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    Optional<LanguageEntity> findByName(LanguageEnum name);
}
