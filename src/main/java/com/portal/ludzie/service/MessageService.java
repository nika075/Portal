package com.portal.ludzie.service;

public interface MessageService {
    void sendEmail(String senderLogin, String receiverLogin, String title, String message);
}
