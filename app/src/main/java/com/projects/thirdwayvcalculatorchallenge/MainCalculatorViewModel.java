package com.projects.thirdwayvcalculatorchallenge;

import android.widget.Switch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainCalculatorViewModel extends ViewModel {

    public MutableLiveData<Float> resultMutableLiveData = new MutableLiveData<>();
    public float inputNum;
    private CalculatorOperation currentOperation;
    public MutableLiveData<List<String>> historyListMutableLiveData = new MutableLiveData<>();
    private List<String> historyList = new ArrayList<>();
    private String currentCalculation;
    public MainCalculatorViewModel() {
        resultMutableLiveData.setValue(0f);
    }


    public void equal() {
        currentCalculation= "";
        switch (currentOperation) {
            case Add:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() + inputNum);
                currentCalculation="+".concat(String.valueOf(inputNum));
                addingHistoryList(currentCalculation);
                break;
            case Minus:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() - inputNum);
                currentCalculation="-".concat(String.valueOf(inputNum));
                addingHistoryList(currentCalculation);
                break;
            case Multiply:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() * inputNum);
                currentCalculation="*".concat(String.valueOf(inputNum));
                addingHistoryList(currentCalculation);
                break;
            case Division:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() / inputNum);
                currentCalculation="/".concat(String.valueOf(inputNum));
                addingHistoryList(currentCalculation);
                break;
        }
    }

    private void addingHistoryList(String currentCalculation) {
        historyList.add(currentCalculation);
        historyListMutableLiveData.setValue(historyList);
    }

    public void setCurrentOperation(CalculatorOperation currentOperation) {
        this.currentOperation = currentOperation;
    }
}
