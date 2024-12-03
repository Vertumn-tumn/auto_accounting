package org.accounting.model;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class YearlyReport {
    private String year;
    double averageIncome;
    double averageConsumption;

    private Map<String, Set<YearlyItem>> yearlyItemMap = new HashMap<>();

    public void averageIncomeAndСonsumption() {

        double income = 0;
        double consumption = 0;
        for (Map.Entry<String, Set<YearlyItem>> item : yearlyItemMap.entrySet()) {
            for (YearlyItem monthInfo : item.getValue()) {
                if (monthInfo.isExpensive()) consumption += monthInfo.getAmount();
                else income += monthInfo.getAmount();
            }
        }
        averageIncome = income;
        averageConsumption = consumption;
    }

    public Map<String, Double> profitForEachMonth() {
        Map<String, Double> profitPerMonth = new HashMap<>();
        for (Map.Entry<String, Set<YearlyItem>> item : yearlyItemMap.entrySet()) {
            double profit = 0;
            for (YearlyItem monthInfo : item.getValue()) {
                if (monthInfo.isExpensive()) profit -= monthInfo.getAmount();
                else profit += monthInfo.getAmount();
            }
            profitPerMonth.put(item.getKey(), profit);
        }
        return profitPerMonth;
    }

    public void addYearlyItem(String month, YearlyItem yearlyItem) {
        yearlyItemMap.computeIfAbsent(month, key -> new HashSet<>()).add(yearlyItem);
        /*if (yearlyItemMap.containsKey(month)) {
            Set<YearlyItem> yearlyItems = yearlyItemMap.get(month);
            yearlyItems.add(yearlyItem);
            yearlyItemMap.put(month, yearlyItems);
        } else {
            Set<YearlyItem> set = new HashSet<>();
            set.add(yearlyItem);
            yearlyItemMap.put(month, set);
        }*/
    }

}
