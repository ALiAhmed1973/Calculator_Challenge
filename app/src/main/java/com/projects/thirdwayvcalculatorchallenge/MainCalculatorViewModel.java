package com.projects.thirdwayvcalculatorchallenge;

import android.widget.Switch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainCalculatorViewModel extends ViewModel {

    public MutableLiveData<Float> resultMutableLiveData = new MutableLiveData<>();
    public float inputNum;
    private CalculatorOperation currentOperation;

    public MainCalculatorViewModel() {
        resultMutableLiveData.setValue(0f);
    }


    public void equal() {
        switch (currentOperation) {
            case Add:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() + inputNum);
                break;
            case Minus:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() - inputNum);
                break;
            case Multiply:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() * inputNum);
                break;
            case Division:
                resultMutableLiveData.setValue(resultMutableLiveData.getValue() / inputNum);
                break;
        }
    }

    public void setCurrentOperation(CalculatorOperation currentOperation) {
        this.currentOperation = currentOperation;
    }
}
