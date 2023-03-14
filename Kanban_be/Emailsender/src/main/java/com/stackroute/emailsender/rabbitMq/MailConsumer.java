package com.stackroute.emailsender.rabbitMq;

import com.stackroute.emailsender.model.EmailDetails;
import com.stackroute.emailsender.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "task_queue1")
    public void getmailFromToDto(EmailDto emailDto){
        EmailDetails emailDetails=new EmailDetails(emailDto.getEmailId(), emailDto.getMessage(),null ,emailDto.getSubject() );
        System.out.println("hi bro "+emailDto.getEmailId());
        emailService.sendTextEmail(emailDetails);
    }

    @RabbitListener(queues = "member_queue")
    public void getmailMemberToDto(EmailDto emailDto)
    {
        EmailDetails emailDetails=new EmailDetails(emailDto.getEmailId(), emailDto.getMessage(),null ,emailDto.getSubject() );
        System.out.println("Member mail Check"+emailDto.getEmailId());
        emailService.sendEmailMemberAdded(emailDetails);
    }

    @RabbitListener(queues = "task_queue")
    public void getmailTaskMembers(EmailDto emailDto)
    {
        EmailDetails emailDetails=new EmailDetails(emailDto.getEmailId(), emailDto.getMessage(),null ,emailDto.getSubject() );
        System.out.println("Task check"+emailDto.getEmailId());
        emailService.assignTask(emailDetails);
    }

    @RabbitListener(queues = "otp_queue")
    public void getmailOTP(OTP otp)
    {

       System.out.println("OTP check "+otp.getEmailId()+" "+otp.getOtp());
        emailService.sendOTP(otp);
    }

}
