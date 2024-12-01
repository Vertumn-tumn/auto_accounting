package org.accounting;

import org.accounting.model.MonthlyReport;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();
        String userInput;
        String keyWord = "q";

        System.out.println("Привет, бухгалтер!Милый мой бухгалтер");
        System.out.println("Вот что я могу сделать для вас:");
        System.out.println("Считать все месячные отчёты - нажми 1");
        System.out.println("Считать годовой отчёт - нажми 2");
        System.out.println("Сверить отчёты - нажми 3");
        System.out.println("Вывести информацию о всех месячных отчётах - нажми 4");
        System.out.println("Вывести информацию о годовом отчёте - нажми 5");
        System.out.println("Для завершения работы с програмой нажми 'q'");
        System.out.println("Введите необходимое число или символ 'q' для выхода из программы:");
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();

        while (!userInput.equals(keyWord)) {
            switch (userInput) {
                case "1" -> {
                    //Считать все месячные отчёты
                    dataProcessor.сountAllMonthlyReports();
                    userInput = scanner.nextLine();
                }
                case "2" -> {
                    //Считать годовой отчёт
                    dataProcessor.сountYearlyReport();
                    userInput = scanner.nextLine();
                }
                case "3" -> {
                    //Сверить отчёты
                    dataProcessor.compareReports();
                    userInput = scanner.nextLine();
                }
                case "4" -> {
                    //Вывести информацию о всех месячных отчётах
                    Map<String, MonthlyReport> monthlyReports = dataProcessor.getMonthlyReports();
                    userInput = scanner.nextLine();
                }
                case "5" -> {
                    //Вывести информацию о годовом отчёте
                    dataProcessor.getYearlyReport();
                    System.out.println();
                    userInput = scanner.nextLine();
                }
                case "q" -> System.out.println("До свидания!!!");
                default -> {
                    System.out.println("Некорректный ввод! Пожалуйста введите корректное значение.");
                    System.out.println("Введите необходимое число:");
                    userInput = scanner.nextLine();
                }
            }
        }
    }
}