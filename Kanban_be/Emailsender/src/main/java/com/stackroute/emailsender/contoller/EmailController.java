package com.stackroute.emailsender.contoller;

import com.stackroute.emailsender.model.EmailDetails;
import com.stackroute.emailsender.rabbitMq.OTP;
import com.stackroute.emailsender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EmailController {
    @Autowired
    private EmailService emailService;



    //    http://localhost:7575/sendmail
    @PostMapping("/sendmail")
    public ResponseEntity<?> sendMail( @RequestBody EmailDetails emailDetails)
    {

        emailService.sendTextEmail(emailDetails);
        System.out.println(emailDetails.getEmailId());
        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }

    @PostMapping("/sendmail/mem")
    public ResponseEntity<?> sendMailMem( @RequestBody EmailDetails emailDetails)
    {

        emailService.sendEmailMemberAdded(emailDetails);
        System.out.println(emailDetails.getEmailId());
        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }

    @PostMapping("/sendmail/task")
    public ResponseEntity<?> sendMailTask( @RequestBody EmailDetails emailDetails)
    {

        emailService.assignTask(emailDetails);
        System.out.println(emailDetails.getEmailId());
        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }

    @PostMapping("/sendmail/otp")
    public ResponseEntity<?> sendOTP( @RequestBody OTP otp)
    {
        emailService.sendOTP(otp);

        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }



}
