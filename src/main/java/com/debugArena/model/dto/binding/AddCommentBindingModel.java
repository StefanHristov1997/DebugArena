package com.debugArena.model.dto.binding;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddCommentBindingModel {

    private String textContent;

    private LocalDate createdOn;

    public AddCommentBindingModel() {
        this.createdOn = LocalDate.now();
    }
}
