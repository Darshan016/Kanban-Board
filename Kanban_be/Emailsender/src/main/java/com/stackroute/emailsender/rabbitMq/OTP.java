package com.stackroute.emailsender.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OTP {
    private String emailId;
    private String otp;
}
