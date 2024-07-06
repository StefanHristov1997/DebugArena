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

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 6, message = "{title.length.message}")
    private String title;

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 20, message = "{description.length.message}")
    private String description;

    @NotNull(message = "{date.not.null.message}")
    @PastOrPresent(message = "{valid.date.message}")
    private LocalDate createdOn;

    @NotNull(message = "{language.not.null.message}")
    private LanguageEnum language;
}
