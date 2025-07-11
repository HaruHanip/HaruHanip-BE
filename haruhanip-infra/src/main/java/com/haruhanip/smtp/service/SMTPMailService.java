package com.haruhanip.smtp.service;

import com.haruhanip.domains.mail.repository.ValidationMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMTPMailService implements GeneralMailService {

    private final JavaMailSender javaMailSender;
    private final ValidationMailRepository validationMailRepository;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpireTime;

    public void sendMail(String toMail) {
        log.info("Send mail to {}", toMail);

        String title = "[하루한입] 인증 코드 발송";
        SimpleMailMessage message = createEmailForm(toMail, title, createRandomKey());
        try {
            javaMailSender.send(message);
            validationMailRepository.saveValidationMail(toMail, message.getText(),
                    Duration.ofMillis(authCodeExpireTime));
        } catch (Exception e) {
            log.error("Failed to send mail", e);
            throw new RuntimeException("Failed to send mail");
        }
    }

    public void verifyMail(String toMail, String authCode) {
        log.info("Verify mail to {}", toMail);

        String redisAuthCode = validationMailRepository.getValidationMail(toMail);
        if (redisAuthCode == null) {
            log.error("Auth code is expired");
            throw new RuntimeException("Auth code is expired");
        }
        if (!redisAuthCode.equals(authCode)) {
            log.error("Auth code is not matched");
            throw new RuntimeException("Auth code is not matched");
        }
    }

    private SimpleMailMessage createEmailForm(String toMail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    private String createRandomKey() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < length; i++) {
                key.append(random.nextInt(10));
            }
            return key.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to create random key", e);
            throw new RuntimeException("Failed to create random key");
        }
    }


}