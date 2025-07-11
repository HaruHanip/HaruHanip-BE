package com.haruhanip.api.user.service;

import com.haruhanip.smtp.service.GeneralMailService;
import com.haruhanip.smtp.service.SMTPMailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final GeneralMailService mailService;

    public void sendMail(@Valid @NotNull String email) {
        mailService.sendMail(email);
    }

    public void verifyMail(String email, String s) {
        mailService.verifyMail(email, s);
    }
}
