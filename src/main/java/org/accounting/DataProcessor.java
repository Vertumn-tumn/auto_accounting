package org.accounting;

import lombok.Data;
import org.accounting.model.MonthlyItem;
import org.accounting.model.MonthlyReport;
import org.accounting.model.YearlyReport;

import java.util.HashMap;
import java.util.Map;

@Data
public class DataProcessor {
    private String monthlyReportsPath = "C:/Users/admin/Documents/monthReport";
    private String yearlyReportsPath = "C:/Users/admin/Documents/yearReport";
    private DataReader dataReader = new DataReader();
    private Map<String, MonthlyReport> monthlyReports;
    private YearlyReport yearlyReport;

    public void сountAllMonthlyReports() {
        //передаем в метод объекта класса dataReader путь в директории с месячными отчётами, получить мапу String, String
        Map<String, String> reports = dataReader.readMonthlyReports(monthlyReportsPath);
        //Преобразовываем value мапы к объекту MonthlyReport
        if (!reports.isEmpty()) {
            monthlyReports = valueTypeConversion(reports);
        }
    }

    public void сountYearlyReport() {
        //передаем в метод объекта класса dataReader путь к директории с годовыми отчётами, получить объект класса YearlyReport
        Map<String, String> report = dataReader.readYearlyReports(yearlyReportsPath);
        if (!report.isEmpty()) {

        }
    }

    private Map<String, MonthlyReport> valueTypeConversion(Map<String, String> reports) {
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
    public void compareReports() {
    }
}

