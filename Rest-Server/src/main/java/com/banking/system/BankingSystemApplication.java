package com.banking.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class BankingSystemApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(BankingSystemApplication.class, args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
