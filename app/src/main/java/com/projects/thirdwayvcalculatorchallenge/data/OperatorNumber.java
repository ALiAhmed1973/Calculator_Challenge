package com.projects.thirdwayvcalculatorchallenge.data;

import com.projects.thirdwayvcalculatorchallenge.data.CalculatorOperation;

/**
 * class for one operation has two fields
 * NumOperator for type of operation adding or minus...
 * NumValue for the actual value of number provided by user choice
 */
public class OperatorNumber {
    private CalculatorOperation NumOperator;
    private Float NumValue;

    public CalculatorOperation getNumOperator() {
        return NumOperator;
    }

    public void setNumOperator(CalculatorOperation numOperator) {
        NumOperator = numOperator;
    }

    public Float getNumValue() {
        return NumValue;
    }

    public void setNumValue(Float numValue) {
        NumValue = numValue;
    }
}
