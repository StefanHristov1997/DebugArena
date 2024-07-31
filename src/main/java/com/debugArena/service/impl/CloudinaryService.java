package com.debugArena.service.impl;

import com.cloudinary.Cloudinary;
import com.debugArena.exeption.FuncErrorException;
import com.debugArena.model.dto.binding.CloudinaryResponse;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary, LoggedUserHelper loggedUserHelper) {
        this.cloudinary = cloudinary;
        this.loggedUserHelper = loggedUserHelper;
    }

    @Transactional
    public CloudinaryResponse uploadFile(final MultipartFile file, final String fileName) {
        UserEntity currentUser = loggedUserHelper.get();

        try {
            final Map result = this.cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id",
                                    "DebugArena/userID/" + currentUser.getId() + "/"
                                            + fileName));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url)
                    .build();

        } catch (final Exception e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }
}
