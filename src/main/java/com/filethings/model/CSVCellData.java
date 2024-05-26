package com.filethings.model;

import com.filethings.model.CSVCellType;

public class CSVCellData implements Comparable<CSVCellData> {

    private final String value;
    private final CSVCellType type;

    public CSVCellData(String value) {
        this.value = value.trim();

        CSVCellType type;
        if (value.isEmpty()) {
            type = CSVCellType.EMPTY;
        } else {
            try {
                Double.parseDouble(value);
                type = CSVCellType.NUMBER;
            } catch (NumberFormatException e) {
                type = CSVCellType.STRING;
            }
        }
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public CSVCellType getType() {
        return type;
    }

    @Override
    public int compareTo(CSVCellData other) {
        if (this.type == CSVCellType.NUMBER && other.type == CSVCellType.NUMBER) {
            try {
                double thisValue = Double.parseDouble(this.value);
                double otherValue = Double.parseDouble(other.value);
                return Double.compare(thisValue, otherValue);
            } catch (NumberFormatException e) {
                return this.value.compareTo(other.value);
            }
        }
        return this.value.compareTo(other.value);
    }

}
