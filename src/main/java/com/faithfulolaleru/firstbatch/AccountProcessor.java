package com.faithfulolaleru.firstbatch;

import org.springframework.batch.item.ItemProcessor;

public class AccountProcessor implements ItemProcessor<Account, Account> {  // account goes in, account comes out
    @Override
    public Account process(Account account) throws Exception {

        System.out.println("Inserting account --> " + account);

        return account;
    }
}
