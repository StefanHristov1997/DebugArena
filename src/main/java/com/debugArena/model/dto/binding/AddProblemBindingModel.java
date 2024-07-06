package com.debugArena.model.dto.binding;

import com.debugArena.model.enums.LanguageEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AddProblemBindingModel {

    @NotBlank
    @Size(min = 6)
    private String title;

    @NotBlank
    @Size(min = 20)
    private String description;

    @NotNull
    @PastOrPresent
    private LocalDate createdOn;

    @NotNull
    private LanguageEnum language;
}
