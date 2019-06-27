package com.example.demo.asm.third.service;

import com.example.demo.asm.third.SecurityChecker;

public class AccountWithSecurityCheck implements Account {
    private  Account account;
    public AccountWithSecurityCheck (Account account) {
        this.account = account;
    }
    @Override
    public void operation() {
        SecurityChecker.checkSecurity();
        account.operation();
        SecurityChecker.aftercheckSecurity();
    }
}
