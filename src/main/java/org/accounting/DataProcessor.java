package org.accounting;

import lombok.Data;
import org.accounting.model.MonthlyItem;
import org.accounting.model.MonthlyReport;
import org.accounting.model.YearlyItem;
import org.accounting.model.YearlyReport;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Data
public class DataProcessor {
    private String monthlyReportsPath = "C:/Users/admin/Documents/monthReport";
    private String yearlyReportsPath = "C:/Users/admin/Documents/yearReport";
    private DataReader dataReader = new DataReader();
    private Map<String, MonthlyReport> monthlyReports = new HashMap<>();
    private YearlyReport yearlyReport = new YearlyReport();

    public void сountAllMonthlyReports() {
        //передаем в метод объекта класса dataReader путь в директории с месячными отчётами, получить мапу String, String
        Map<String, String> reports = dataReader.readMonthlyReports(monthlyReportsPath);
        //Преобразовываем value мапы к объекту MonthlyReport
        if (!reports.isEmpty()) {
            monthlyReports = valueTypeConversionForMonthReports(reports);
        }
    }

    public void сountYearlyReport() {
        //передаем в метод объекта класса dataReader путь к директории с годовыми отчётами, получить объект класса YearlyReport
        Map<String, String> report = dataReader.readYearlyReports(yearlyReportsPath);
        if (!report.isEmpty()) {
            yearlyReport = valueTypeConversionForYearReport(report);
        }
    }

    public void monthlyReportsInfo() {
        if (!monthlyReports.isEmpty()) {
            System.out.println("Информация по месячным отчётам:");
            monthlyReports.entrySet().forEach((key -> {
                System.out.println(key.getKey());
                MonthlyReport monthlyReport = key.getValue();
                System.out.println("Информация о самых прибыльных товарах:");
                Map<String, Double> profit = monthlyReport.mostProfitable();
                if (profit.isEmpty()) System.out.println("В этом месяце нет доходов.");
                else {
                    Map.Entry<String, Double> entry = profit.entrySet().stream().findFirst().get();
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
                System.out.println("Информация о расходах:");
                Map<String, Double> expens = monthlyReport.mostExpensive();
                if (expens.isEmpty()) System.out.println("В этом месяце нет расходов.");
                else {
                    Map.Entry<String, Double> entry = expens.entrySet().stream().findFirst().get();
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }));
        } else {
            System.out.println("Сначала считайте месячные отчёты.");
        }
    }


    public void yearlyReportInfo() {
        if (!yearlyReport.getYearlyItemMap().isEmpty()) {
            System.out.println("Информация за " + yearlyReport.getYear());
            System.out.println("Прибыль за каждый месяц: ");
            Map<String, Double> eachMonth = yearlyReport.profitForEachMonth();
            eachMonth.forEach((key, value) -> System.out.println(key + " = " + value));
            yearlyReport.averageIncomeAndСonsumption();
            System.out.println("Средний расход: " + yearlyReport.getAverageConsumption());
            System.out.println("Средний доход: " + yearlyReport.getAverageIncome());
        } else {
            System.out.println("Сначала считайте годовой отчёт.");
        }
    }

    public void compareReports() {
        if (monthlyReports.isEmpty() || yearlyReport.getYearlyItemMap().isEmpty()) {
            System.out.println("Не прочтены отчёты. Прочтите годовой и месячные отчёты для дальнейшей сверки.");
        } else {
            monthlyIncomeAndExpensesReconciliation(monthlyReports, yearlyReport);
        }
    }

    private void monthlyIncomeAndExpensesReconciliation(Map<String, MonthlyReport> monthlyReports, YearlyReport yearlyReport) {
        Map<String, Double> totalMonthIncomeMap = new HashMap<>();
        Map<String, Double> totalMonthConsumptionMap = new HashMap<>();
        Map<String, Double> totalYearIncomeMap = new HashMap<>();
        Map<String, Double> totalYearConsumptionMap = new HashMap<>();

        //посчитаем доходы и расходы из месячных отчётов, наполним мапы
        for (Map.Entry<String, MonthlyReport> report : monthlyReports.entrySet()) {
            double income = 0;
            double consumption = 0;
            String month = report.getKey();
            for (MonthlyItem item : report.getValue().getMonthlyItemList()) {
                double totalSum = item.getQuantity() * item.getSumOfOne();
                if (item.isExpensive()) {
                    consumption += totalSum;
                } else {
                    income += totalSum;
                }
            }
            totalMonthIncomeMap.put(month, income);
            totalMonthConsumptionMap.put(month, consumption);
        }
        //посчитаем доходы и расходы из годового отчёта, наполним мапы
        for (Map.Entry<String, Set<YearlyItem>> item : yearlyReport.getYearlyItemMap().entrySet()) {
            double income = 0;
            double consumption = 0;
            String month = item.getKey();
            for (YearlyItem monthInfo : item.getValue()) {
                if (monthInfo.isExpensive()) consumption += monthInfo.getAmount();
                else income += monthInfo.getAmount();
            }
            totalYearIncomeMap.put(month, income);
            totalYearConsumptionMap.put(month, consumption);
        }

        //Сравним годовые мапы с месячными, а не наоборот, т.к.годовые отчёты составляются на основе месячных, хотя кто его знает?
        if (totalYearIncomeMap.size() != totalMonthIncomeMap.size() || totalYearConsumptionMap.size() != totalMonthConsumptionMap.size()) {
            System.out.println("Количество месяцев в годовом и месячных отчётах не совпадают. Пожалуйста перепроверьте отчёты.");
        } else {
            for (Map.Entry<String, Double> yearEntry : totalYearIncomeMap.entrySet()) {
                for (Map.Entry<String, Double> monthEntry : totalMonthIncomeMap.entrySet()) {
                    if (!Objects.equals(yearEntry.getValue(), monthEntry.getValue()) && Objects.equals(yearEntry.getKey(), monthEntry.getKey())) {
                        System.out.println("Доход годового отчёта за " + yearEntry.getKey() + " не сходится с месячным отчётом");
                    }
                }
            }
        }

        //то же самое, но уж для расходов
        for (Map.Entry<String, Double> yearEntry : totalYearConsumptionMap.entrySet()) {
            for (Map.Entry<String, Double> monthEntry : totalMonthConsumptionMap.entrySet()) {
                if (!Objects.equals(yearEntry.getValue(), monthEntry.getValue()) && Objects.equals(yearEntry.getKey(), monthEntry.getKey())) {
                    System.out.println("Расходы годового отчёта за " + yearEntry.getKey() + " не сходятся с месячным отчётом");
                }
            }
        }
    }

    private YearlyReport valueTypeConversionForYearReport(Map<String, String> report) {
        YearlyReport yearlyRep = new YearlyReport();
        yearlyRep.setYear(report.entrySet().stream().findFirst().get().getKey());


        for (Map.Entry<String, String> entry : report.entrySet()) {
            String reportData = entry.getValue();

            // Разделяем строку отчета на отдельные элементы
            String[] items = reportData.split("\r\n");

            // Обрабатываем каждую строку в отчете
            for (int i = 1; i < items.length; i++) {
                String[] itemDetails = items[i].split(",");
                String month = DataUtil.months.get(itemDetails[0]);
                int amount = Integer.parseInt(itemDetails[1]);
                boolean isExpensive = Boolean.parseBoolean(itemDetails[2]);

                // достаём из объекта  yearlyRep мапу, наполняем её полученными значениями
                yearlyRep.addYearlyItem(month, new YearlyItem(amount, isExpensive));
            }
        }
        return yearlyRep;
    }

    private Map<String, MonthlyReport> valueTypeConversionForMonthReports(Map<String, String> reports) {
        Map<String, MonthlyReport> monthlyReportMap = new HashMap<>();

        // Проходим по каждому отчету в мапе
        for (Map.Entry<String, String> entry : reports.entrySet()) {
            String key = entry.getKey();
            String reportData = entry.getValue();

            // Разделяем строку отчета на отдельные элементы
            String[] items = reportData.split("\r\n");
            MonthlyReport monthlyReport = new MonthlyReport();

            // Обрабатываем каждый товар в отчете
            for (int i = 1; i < items.length; i++) {
                String[] itemDetails = items[i].split(",");
                String itemName = itemDetails[0];
                boolean isExpensive = Boolean.parseBoolean(itemDetails[1]);
                double quantity = Double.parseDouble(itemDetails[2]);
                int sumOfOne = Integer.parseInt(itemDetails[3]);

                // Создаем объект MonthlyItem и добавляем его в отчет
                MonthlyItem monthlyItem = new MonthlyItem(itemName, isExpensive, quantity, sumOfOne);
                monthlyReport.addMonthlyItem(monthlyItem);
            }
            monthlyReportMap.put(key, monthlyReport);
        }
        return monthlyReportMap;
    }
}

