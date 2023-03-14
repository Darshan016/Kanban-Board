package com.stackroute.emailsender.service;

import com.stackroute.emailsender.model.EmailDetails;
import com.stackroute.emailsender.rabbitMq.OTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService
{

    private static final Logger logger = LoggerFactory
            .getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendTextEmail(EmailDetails emailDetails)
    {
        logger.info("Start Sending email to "+emailDetails.getEmailId());

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailDetails.getEmailId());
        simpleMessage.setSubject("Your have been successfully registered");


        simpleMessage.setText("WELCOME ...");


        javaMailSender.send(simpleMessage);

        logger.info("Email sent to "+emailDetails.getEmailId());
    }


    @Override
    public void sendEmailMemberAdded(EmailDetails emailDetails)
    {
        logger.info("Start Sending email to "+emailDetails.getEmailId());

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailDetails.getEmailId());
        simpleMessage.setSubject("Added you as a member");


        simpleMessage.setText("Login to See your projects details ...");


        javaMailSender.send(simpleMessage);

        logger.info("Email sent to "+emailDetails.getEmailId());
    }

    @Override
    public void assignTask(EmailDetails emailDetails) {


        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailDetails.getEmailId());
        simpleMessage.setSubject("Assigned you in task");


        simpleMessage.setText("New Task Are assigned");


        javaMailSender.send(simpleMessage);

        logger.info("Email sent to "+emailDetails.getEmailId());
    }

    @Override
    public void sendOTP(OTP otp) {
        System.out.println("Inside service");
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(otp.getEmailId());
        simpleMessage.setSubject("Your One-Time-Password");
        simpleMessage.setText("Don't Share this to anyone"+otp.getOtp());
        javaMailSender.send(simpleMessage);
        logger.info("Email sent to "+otp.getEmailId());
    }
}
