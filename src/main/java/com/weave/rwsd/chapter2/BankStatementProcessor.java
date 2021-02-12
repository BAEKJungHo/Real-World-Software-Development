package com.weave.rwsd.chapter2;

import com.weave.rwsd.chapter3.BankTransactionFilter;
import com.weave.rwsd.chapter3.SummaryStatistics;

import java.time.Month;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class BankStatementProcessor {

    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public SummaryStatistics summarizeTransactions() {
        final DoubleSummaryStatistics doubleSummaryStatistics = bankTransactions.stream()
                .mapToDouble(BankTransaction::getAmount)
                .summaryStatistics();

        return new SummaryStatistics(doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getAverage());
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        double total = 0;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    /**
     * 특정 월이나 금액으로 입출금 내역 검색하기
     * 이 방식의 한계
     * - 거래 내역의 여러 속성을 조합할수록 코드가 점점 복잡해진다.
     * - 반복 로직과 비지니스 로직이 결합되어 분리하기가 어려워진다.
     * - 코드를 반복한다.
     * @param month
     * @param amount
     * @return
     */
    @Deprecated
    public List<BankTransaction> findTransactionsInMonthAndGreater(final Month month, final int amount) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction : bankTransactions) { // 반복 로직
            if(bankTransaction.getDate().getMonth() == month && bankTransaction.getAmount() >= amount) { // 비지니스 로직
                result.add(bankTransaction);
            }
        }
        return result;
    }

    /**
     * 위 메서드에서 반복 로직과 비지니스 로직을 함수형 인터페이스를 구현함으로써 분리
     * @param bankTransactionFilter
     * @return
     */
    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction : bankTransactions) {
            if(bankTransactionFilter.test(bankTransaction)) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

}
