package com.haruhanip.domains.mail.repository;

import java.time.Duration;

public interface ValidationMailRepository {
    void saveValidationMail(String toMail, String text, Duration duration);

    String getValidationMail(String toMail);
}
