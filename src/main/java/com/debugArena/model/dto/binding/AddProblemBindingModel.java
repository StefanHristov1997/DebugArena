package com.debugArena.model.dto.binding;

import com.debugArena.model.enums.LanguageEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static messages.ValidationErrorMessages.*;

@Getter
@Setter
public class AddProblemBindingModel {

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(message = TITLE_LENGTH_MESSAGE)
    private String title;

    @NotBlank(message = BLANK_FIELD_MESSAGE)
    @Size(message = DESCRIPTION_LENGTH_MESSAGE)
    private String description;

    @PastOrPresent(message = VALID_DATE_MESSAGE)
    private LocalDate createdOn;

    @NotNull(message = LANGUAGE__NOT_NULL_MESSAGE)
    private LanguageEnum language;
}
