package org.accounting.model;

import lombok.Data;

@Data
public class YearlyItem {
    private int month;
    private int amount;
    private boolean isExpensive;
}
