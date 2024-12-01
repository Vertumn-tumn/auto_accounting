package org.accounting.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MonthlyReport {
    private List<MonthlyItem> monthlyItemList = new ArrayList<>();

    public void mostProfitable() {

    }

    public void mostExpensive() {

    }

    public void addMonthlyItem(MonthlyItem monthlyItem) {
        monthlyItemList.add(monthlyItem);
    }
}
