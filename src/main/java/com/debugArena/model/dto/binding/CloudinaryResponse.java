package com.debugArena.model.dto.binding;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudinaryResponse {

    private String publicId;

    private String url;
}
