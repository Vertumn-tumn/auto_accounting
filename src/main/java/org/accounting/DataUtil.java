package org.accounting;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
    static Map<String, String> months = new HashMap<>();
    static {
        months.put("01", "Январь");
        months.put("02", "Февраль");
        months.put("03", "Март");
        months.put("04", "Апрель");
        months.put("05", "Май");
        months.put("06", "Июнь");
        months.put("07", "Июль");
        months.put("08", "Август");
        months.put("09", "Сентябрь");
        months.put("10", "Октябрь");
        months.put("11", "Ноябрь");
        months.put("12", "Декабрь");
    }
}
