package com.debugArena.config;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.entity.dto.binding.UserRegisterBindingModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        Provider<UserEntity> newUserProvider = req -> new UserEntity();

        Converter<String, String> passwordConverter
                = ctx -> (ctx.getSource() == null)
                ? null
                : passwordEncoder().encode(ctx.getSource());

        modelMapper
                .createTypeMap(UserRegisterBindingModel.class, UserEntity.class)
                .setProvider(newUserProvider)
                .addMappings(mapper -> mapper
                        .using(passwordConverter)
                        .map(UserRegisterBindingModel::getPassword, UserEntity::setPassword));


        return modelMapper;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
