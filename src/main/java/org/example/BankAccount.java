package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final int accountNumber;
    private double balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        try {
            lock.lock();
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void deposit(double amount) {
        try {
            lock.lock();
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        try {
            lock.lock();
            if (balance >= amount) {
                balance -= amount;
            } else {
                throw new IllegalArgumentException("Insufficient funds");
            }
        } finally {
            lock.unlock();
        }
    }
}
