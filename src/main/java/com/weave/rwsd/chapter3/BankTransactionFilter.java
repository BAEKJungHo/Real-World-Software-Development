package com.weave.rwsd.chapter3;

import com.weave.rwsd.chapter2.BankTransaction;

@FunctionalInterface
public interface BankTransactionFilter {

    boolean test(BankTransaction bankTransaction);

}
