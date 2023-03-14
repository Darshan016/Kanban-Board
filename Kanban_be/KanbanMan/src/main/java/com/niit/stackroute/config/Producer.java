package com.niit.stackroute.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer
{
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange)
    {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendTaskToRabbitMq(EmailDetails emailDetails)
    {

        System.out.println(emailDetails);
        rabbitTemplate.convertSendAndReceive(exchange.getName(),"task_queue1",emailDetails);
    }

    public void sendMemberToRabbitMq(EmailDetails emailDetails)
    {
        System.out.println(emailDetails);
        rabbitTemplate.convertSendAndReceive(exchange.getName(),"member_queue",emailDetails);
    }

    public void sendTaskMail(EmailDetails emailDetails)
    {
        System.out.println(emailDetails);
        rabbitTemplate.convertSendAndReceive(exchange.getName(),"task_queue",emailDetails);
    }

    public void sendOTP(OTP otp)
    {
        System.out.println(otp);
        rabbitTemplate.convertSendAndReceive(exchange.getName(),"otp_queue",otp);
    }

}
