package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.EmailRequestDTO;

public interface EmailService {
    void sendEmail(EmailRequestDTO emailRequest);
}
