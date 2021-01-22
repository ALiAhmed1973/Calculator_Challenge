package com.projects.thirdwayvcalculatorchallenge;

import android.util.Log;
import android.widget.Switch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MainCalculatorViewModel extends ViewModel {
    private static final String TAG = "MainCalculatorViewModel";
    public MutableLiveData<Float> resultMutableLiveData = new MutableLiveData<>();
    private OperatorNumber operatorNumber;
    public MutableLiveData<List<OperatorNumber>> historyListMutableLiveData = new MutableLiveData<>();
    private List<OperatorNumber> historyList = new ArrayList<>();
    public MutableLiveData<Boolean> isEqualButtonActive = new MutableLiveData<>();
    private Deque<List<OperatorNumber>> allHistoryOperation= new ArrayDeque<>();
    private Deque<List<OperatorNumber>> UndoOp= new ArrayDeque<>();
    public MainCalculatorViewModel() {
        resultMutableLiveData.setValue(0f);
        operatorNumber = new OperatorNumber();
    }

    public void setIsEqualButtonActive(Boolean active) {
        this.isEqualButtonActive.setValue(active);
    }




    private void addingToDeque() {
        List<OperatorNumber> operatorNumbers = new ArrayList<>(historyList);
        allHistoryOperation.add(operatorNumbers);
       // UndoOp.add(operatorNumbers);
    }
    public void Calculation() {
        historyList.add(operatorNumber);
        historyListMutableLiveData.setValue(historyList);
        equal();
        addingToDeque();
    }
    public void removeHistoryList(OperatorNumber deletedOperatorNumber) {
        historyList.remove(operatorNumber);
        historyListMutableLiveData.setValue(historyList);
        equal();
        addingToDeque();
    }

    public void setCurrentOperation(CalculatorOperation currentOperation) {
        operatorNumber = new OperatorNumber();
        operatorNumber.setNumOperator(currentOperation);
        isEqualButtonActive.setValue(true);
    }
    public void setCurrentInputNum(Float currentInputNum) {
        operatorNumber.setNumValue(currentInputNum);
    }

    public void redo()
    {
        List<OperatorNumber> operatorNumbers = allHistoryOperation.getFirst();
        allHistoryOperation.addLast(operatorNumbers);
        historyList=new ArrayList<>(operatorNumbers);
        historyListMutableLiveData.setValue(historyList);
        equal();

    }
    public void undo()
    {
        List<OperatorNumber> operatorNumbers = allHistoryOperation.pollLast();
        allHistoryOperation.addFirst(operatorNumbers);
        List<OperatorNumber> lastListOperatorNumbers = allHistoryOperation.getLast();
        historyList=new ArrayList<>(lastListOperatorNumbers);
        historyListMutableLiveData.setValue(historyList);
        equal();


    }

    private void equal() {

        float result=0;
        for (OperatorNumber operatorNumber1:historyList) {
            switch (operatorNumber1.getNumOperator()) {
                case Add:
                    result= result+operatorNumber1.getNumValue();
                    break;
                case Minus:
                    result= result-operatorNumber1.getNumValue();
                    break;
                case Multiply:
                    result= result*operatorNumber1.getNumValue();
                    break;
                case Division:
                    result= result/operatorNumber1.getNumValue();
                    break;
            }
        }
        resultMutableLiveData.setValue(result);
    }

//    public void addingHistory()
//    {
//        addingHistoryList(operatorNumber);
//    }
}
