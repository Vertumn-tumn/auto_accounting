package org.accounting.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class YearlyReport {
    private Map<String, ArrayList<YearlyItem>> yearlyItemList = new HashMap<>();

    public void averageIncome() {

    }

    public void averageСonsumption() {

    }

    public void profitForEachMonth() {

    }

}
