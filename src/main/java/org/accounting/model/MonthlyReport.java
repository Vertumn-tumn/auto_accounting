package org.accounting.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MonthlyReport {
    private List<MonthlyItem> monthlyItemList = new ArrayList<>();

    public Map<String, Double> mostProfitable() {
        double maxProfit = 0;
        Map<String, Double> nameMap = new HashMap<>();

        for (MonthlyItem item : monthlyItemList) {
            if (!item.isExpensive() && (item.getQuantity() * item.getSumOfOne() > maxProfit)) {
                maxProfit = item.getQuantity() * item.getSumOfOne();
                if (nameMap.isEmpty()) {
                    nameMap.put(item.getItemName(), maxProfit);
                } else {
                    nameMap.clear();
                    nameMap.put(item.getItemName(), maxProfit);
                }
            }
        }
        return nameMap;
    }

    public Map<String, Double> mostExpensive() {
        double maxExpense = 0;
        Map<String, Double> nameMap = new HashMap<>();

        for (MonthlyItem item : monthlyItemList) {
            if (item.isExpensive() && (item.getQuantity() * item.getSumOfOne() > maxExpense)) {
                maxExpense = item.getQuantity() * item.getSumOfOne();
                if (nameMap.isEmpty()) {
                    nameMap.put(item.getItemName(), maxExpense);
                } else {
                    nameMap.clear();
                    nameMap.put(item.getItemName(), maxExpense);
                }
            }
        }
        return nameMap;
    }

    public void addMonthlyItem(MonthlyItem monthlyItem) {
        monthlyItemList.add(monthlyItem);
    }
}
