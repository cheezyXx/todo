package com.example.todolist.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class RegisterEmailService {

    private final EmailSenderService emailSenderService;

    @Autowired
    public RegisterEmailService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public void sendRegisterEmail(String toEmail, String token) throws MessagingException {
        String body = String.format("""
                        <html>
                            <head>
                                <style>
                                    a {
                                        text-decoration: none;
                                         padding: 1rem 2em;
                                         background-color: #4666ff;
                                         color: #fff !important;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                       <h1>Welcome to Epic todo list<h1>
                            
                                       <a href="https://epic-todo-list.com/password/%s">Set a password</a>
                                </div>
                            </body>
                        </html>
                """, token);

        emailSenderService.send(toEmail, body, "Welcome to our page!!!");
    }
}
