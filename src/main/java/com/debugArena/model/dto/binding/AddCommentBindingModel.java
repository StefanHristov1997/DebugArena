package com.debugArena.model.dto.binding;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;




@Getter
@Setter
public class AddCommentBindingModel {

    @NotBlank(message = "{blank_field_message}")
    private String textContent;

    private LocalDate createdOn;

    public AddCommentBindingModel() {
        this.createdOn = LocalDate.now();
    }
}
