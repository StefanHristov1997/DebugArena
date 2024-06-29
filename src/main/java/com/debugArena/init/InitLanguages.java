package com.debugArena.init;

import com.debugArena.model.entity.LanguageEntity;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitLanguages implements CommandLineRunner {

    private final Map<Long, LanguageEnum> languages = Map.of(
            1L, LanguageEnum.JAVA,
            2L, LanguageEnum.CSHARP,
            3L, LanguageEnum.JAVASCRIPT,
            4L,  LanguageEnum.PYTHON
    );

    private final LanguageRepository languageRepository;

    @Autowired
    public InitLanguages(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = languageRepository.count();

        if (count > 0) {
            return;
        }

        for (Long id : languages.keySet()) {
            LanguageEntity role = new LanguageEntity(id, languages.get(id));

            languageRepository.save(role);
        }
    }
}
