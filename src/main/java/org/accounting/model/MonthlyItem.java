package org.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyItem {
    private String itemName;
    private boolean isExpensive;
    private double quantity;
    private int sumOfOne;
}
