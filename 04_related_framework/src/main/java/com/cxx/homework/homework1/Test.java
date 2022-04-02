package com.cxx.homework.homework1;

import com.cxx.homework.homework1.annotion.Log;
import com.cxx.homework.homework1.annotion.Transaction;

public interface Test {

    @Transaction
    void methodTransaction();

    @Log
    void methodLog();

    @Transaction
    @Log
    void methodTransactionWithLog();

    void methodNothing();
}
