package com.stackroute.emailsender.service;

import com.stackroute.emailsender.model.EmailDetails;
import com.stackroute.emailsender.rabbitMq.OTP;
import org.springframework.stereotype.Service;

@Service
public interface EmailService
{
    void sendTextEmail(EmailDetails emailDetails);
    void sendEmailMemberAdded(EmailDetails emailDetails);

    void assignTask(EmailDetails emailDetails);

    void sendOTP(OTP otp);
}
