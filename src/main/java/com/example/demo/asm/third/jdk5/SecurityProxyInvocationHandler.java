package com.example.demo.asm.third.jdk5;


import com.example.demo.asm.third.AccountImpl;
import com.example.demo.asm.third.SecurityChecker;
import com.example.demo.asm.third.service.Account;
import com.example.demo.asm.third.service.AccountWithSecurityCheck;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class SecurityProxyInvocationHandler implements InvocationHandler {
    private Object proxyedObject;

    public SecurityProxyInvocationHandler(Object o) {
        proxyedObject = o;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] arguments)
            throws Throwable {
        if (object instanceof Account && method.getName().equals("operation")) {
            SecurityChecker.checkSecurity();
        }
        return method.invoke(proxyedObject, arguments);
    }

    public static void main(String[] args) {

        Account account = (Account) Proxy.newProxyInstance(
                Account.class.getClassLoader(),
                new Class[] { Account.class },
                new SecurityProxyInvocationHandler(new AccountImpl())
        );
        account.operation();


//        AccountWithSecurityCheck account = new AccountWithSecurityCheck();
//        SecurityProxyInvocationHandler handler = new SecurityProxyInvocationHandler(account);
//        Account proxyAccount = (Account) Proxy.newProxyInstance(AccountImpl.class.getClassLoader(), AccountImpl.class.getInterfaces(), handler);
//        proxyAccount.operation();

//        Account account = (Account) Proxy.newProxyInstance(
//                Account.class.getClassLoader(),
//                new Class[]{Account.class},
//                new SecurityProxyInvocationHandler(new AccountImpl())
//        );
//        account.operation();
    }
}
