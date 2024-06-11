package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank {
    private final Map<Integer, BankAccount> accounts;
    private final Lock lock = new ReentrantLock();

    public ConcurrentBank() {
        accounts = new HashMap<>();
    }

    public BankAccount createAccount(double initialBalance) {
        int accountNumber = accounts.size() + 1;
        BankAccount account = new BankAccount(accountNumber, initialBalance);
        try {
            lock.lock();
            accounts.put(accountNumber, account);
        } finally {
            lock.unlock();
        }
        return account;
    }

    public void transfer(BankAccount from, BankAccount to, double amount) {
        try {
            lock.lock();
            from.withdraw(amount);
            to.deposit(amount);
        } finally {
            lock.unlock();
        }
    }

    public double getTotalBalance() {
        try {
            lock.lock();
            double totalBalance = 0;
            for (BankAccount account : accounts.values()) {
                totalBalance += account.getBalance();
            }
            return totalBalance;
        } finally {
            lock.unlock();
        }
    }
}
