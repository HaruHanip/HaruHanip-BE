package com.haruhanip.smtp.service;

public interface GeneralMailService {
    public void sendMail(String toMail);
    public void verifyMail(String toMail, String authCode);
}
