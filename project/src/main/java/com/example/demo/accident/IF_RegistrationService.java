package com.example.demo.accident;


import org.springframework.web.multipart.MultipartFile;

public interface IF_RegistrationService {

    void write(RegistrationEntity registrationentity, MultipartFile[] file);
}
