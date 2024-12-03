package org.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyItem {
    private int amount;
    private boolean isExpensive;
}
