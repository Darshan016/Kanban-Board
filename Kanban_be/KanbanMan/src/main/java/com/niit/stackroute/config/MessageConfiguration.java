package com.niit.stackroute.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MessageConfiguration
{
    private String exchangeName = "task_exchange";
    private String registerQueue1 = "task_queue1";

    private String registerQueueMember= "member_queue";

    private String queueTask= "task_queue";

    private String queueOtp= "otp_queue";



    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(exchangeName);
    }

    //for task service
    @Bean
    @Primary
    public Queue registerQueue1()
    {
        return new Queue(registerQueue1,false);
    }


    @Bean

    public Queue registerQueueMember()
    {
        return new Queue(registerQueueMember,false);
    }


    @Bean

    public Queue queueTask()
    {
        return new Queue(queueTask,false);
    }

    @Bean

    public Queue queueOtp()
    {
        return new Queue(queueOtp,false);
    }


    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter()
    {
        return new  Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemp=new RabbitTemplate(connectionFactory);
        rabbitTemp.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemp;
    }

    @Bean
    Binding bindingUser(Queue registerQueue, DirectExchange exchange)
    {
        return BindingBuilder.bind(registerQueue1()).to(exchange).with("task_queue1");
    }

    @Bean
    Binding bindingUserMember(Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(registerQueueMember()).to(exchange).with("member_queue");
    }

    @Bean
    Binding bindingTask(Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(queueTask()).to(exchange).with("task_queue");
    }

    @Bean
    Binding bindingOtp(Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(queueOtp()).to(exchange).with("otp_queue");
    }




}
