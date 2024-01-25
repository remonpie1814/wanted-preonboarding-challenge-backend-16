package com.wanted.preonboarding.ticket.domain.entity;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String name;
    private String phoneNumber;
    private int amount;

    public void pay(int money) {
        if (this.amount-money<0){
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.amount-=money;
    }
}
