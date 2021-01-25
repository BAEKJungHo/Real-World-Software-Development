package com.weave.rwsd.chapter2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 모든 거래의 내역 합산하기
 *
 * CSV 파일 형식
 * 30-01-2017, -10, Deliveroo
 * 30-01-2017, 6000, Salary
 * ...
 *
 * CSV 는 종종 콤마로 구분된 파일을 의미하는데, 어떤 사람들은 구분자로 분리된 파일을 말하기도한다.
 *
 * KISS 원칙으로 시작
 * - KISS(keep it short and simple), 응용프로그램 코드를 한 개의 클래스로 구현
 */
public class BankTransactionalAnalyzerSimple {

    private static final String RESOURCES = "src/main/resources/";

    public static void main(final String... args) throws IOException {
        // 1. 은행 입출금 내역의 총 수입과 총 지출은 얼마인가
        final Path path = Paths.get(RESOURCES + args[0]); // get csv file
        final List<String> lines = Files.readAllLines(path); // read all rows
        double total = 0d;
        for(final String line : lines) {
            final String[] columns = line.split(",");  // split comma
            final double amount = Double.parseDouble(columns[1]); // get amount
            total += amount; // accumulate amount
        }
        System.out.println("The total for all transactions is " + total);

        // 2. 특정 달엔 몇 건의 입출금 내역이 발생했는가?
        final Path path2 = Paths.get(RESOURCES + args[0]); // get csv file
        final List<String> lines2 = Files.readAllLines(path); // read all rows
        double total2 = 0d;
        final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(final String line : lines) {
            final String[] columns = line.split(",");  // split comma
            final LocalDate date = LocalDate.parse(columns[0], DATE_PATTERN);
            if(date.getMonth() == Month.JANUARY) {
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }
        }
        System.out.println("The total for all transactions is " + total);
    }

}
