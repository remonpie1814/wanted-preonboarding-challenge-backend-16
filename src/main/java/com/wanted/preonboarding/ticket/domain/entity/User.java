package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.core.exception.NotEnoughAmountException;
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

    public void pay(int money) throws NotEnoughAmountException {
        if (this.amount - money < 0) {
            throw new NotEnoughAmountException();
        }
        this.amount -= money;
    }
}
