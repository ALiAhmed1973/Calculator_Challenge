package com.projects.thirdwayvcalculatorchallenge;

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
