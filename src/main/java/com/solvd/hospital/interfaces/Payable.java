package com.solvd.hospital.interfaces;

import com.solvd.hospital.exceptions.PaymentFailedException;

public interface Payable {
    double calculateTotal();
    void pay(double amount) throws PaymentFailedException;
}