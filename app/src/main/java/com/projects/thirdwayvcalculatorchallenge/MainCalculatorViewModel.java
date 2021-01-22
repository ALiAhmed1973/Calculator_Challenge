package com.projects.thirdwayvcalculatorchallenge;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MainCalculatorViewModel extends ViewModel {
    private static final String TAG = "MainCalculatorViewModel";
    private MutableLiveData<Float> resultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Float> getResultMutableLiveData() {
        return resultMutableLiveData;
    }
    private OperatorNumber operatorNumber;
    private MutableLiveData<List<OperatorNumber>> historyListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<OperatorNumber>> getHistoryListMutableLiveData() {
        return historyListMutableLiveData;
    }
    private List<OperatorNumber> historyList = new ArrayList<>();
    private MutableLiveData<Boolean> isEqualButtonActive = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> getIsEqualButtonActive() {
        return isEqualButtonActive;
    }
    private MutableLiveData<Boolean> isUndoButtonActive = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsUndoButtonActive() {
        return isUndoButtonActive;
    }
    private MutableLiveData<Boolean> isRedoButtonActive = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsRedoButtonActive() {
        return isRedoButtonActive;
    }
    private MutableLiveData<Boolean> isAddButtonActive = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> getIsAddButtonActive() {
        return isAddButtonActive;
    }
    private MutableLiveData<Boolean> isMinusButtonActive = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> getIsMinusButtonActive() {
        return isMinusButtonActive;
    }
    private MutableLiveData<Boolean> isMultiplyButtonActive = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> getIsMultiplyButtonActive() {
        return isMultiplyButtonActive;
    }
    private MutableLiveData<Boolean> isDivisionButtonActive = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> getIsDivisionButtonActive() {
        return isDivisionButtonActive;
    }
    private List<MutableLiveData<Boolean>> allCalculatorOperation= new ArrayList<>();
    private Deque<List<OperatorNumber>> allHistoryOperation= new ArrayDeque<>();
    private Deque<List<OperatorNumber>> undoOp = new ArrayDeque<>();
    private Deque<List<OperatorNumber>> redoOp= new ArrayDeque<>();
    public MainCalculatorViewModel() {
        resultMutableLiveData.setValue(0f);
        operatorNumber = new OperatorNumber();
        allCalculatorOperation.add(isAddButtonActive);
        allCalculatorOperation.add(isMinusButtonActive);
        allCalculatorOperation.add(isMultiplyButtonActive);
        allCalculatorOperation.add(isDivisionButtonActive);
    }

    public void setIsEqualButtonActive(Boolean active) {
        this.isEqualButtonActive.setValue(active);
    }




    private void addingToDeque() {
        List<OperatorNumber> operatorNumbers = new ArrayList<>(historyList);
        allHistoryOperation.add(operatorNumbers);
        undoOp=new ArrayDeque<>(allHistoryOperation);
        UndoRedoButtonsChecks();
    }
    public void Calculation() {
        historyList.add(operatorNumber);
        historyListMutableLiveData.setValue(historyList);
        equal();
        addingToDeque();
        calculatorOperationChecks();
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
        if(isEqualButtonActive.getValue()==false) {
            calculatorOperationChecks();
            isEqualButtonActive.setValue(true);
        }
    }
    public void setCurrentInputNum(Float currentInputNum) {
        operatorNumber.setNumValue(currentInputNum);
    }

    public void redo() {

        if (!redoOp.isEmpty()) {
            List<OperatorNumber> operatorNumbers = redoOp.pollLast();
            undoOp.add(operatorNumbers);
            allHistoryOperation.add(operatorNumbers);
            List<OperatorNumber> lastListOperatorNumbers = allHistoryOperation.getLast();
            historyList = new ArrayList<>(lastListOperatorNumbers);
            historyListMutableLiveData.setValue(historyList);
            equal();
            isUndoButtonActive.setValue(true);
        }
        UndoRedoButtonsChecks();
    }
    public void undo()
    {


            List<OperatorNumber> operatorNumbers = undoOp.pollLast();
            redoOp.add(operatorNumbers);
            if(!undoOp.isEmpty())
            {
                allHistoryOperation.add(undoOp.getLast());
                List<OperatorNumber> lastListOperatorNumbers = allHistoryOperation.getLast();
                historyList = new ArrayList<>(lastListOperatorNumbers);
                historyListMutableLiveData.setValue(historyList);
                equal();
                isRedoButtonActive.setValue(true);
            }else
            {
                List<OperatorNumber> newOp = new ArrayList<>(0);
                allHistoryOperation.add(newOp);
                List<OperatorNumber> lastListOperatorNumbers = allHistoryOperation.getLast();
                historyList = new ArrayList<>(lastListOperatorNumbers);
                historyListMutableLiveData.setValue(historyList);
                equal();
                isRedoButtonActive.setValue(true);
            }


        UndoRedoButtonsChecks();
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

    private void UndoRedoButtonsChecks()
    {
        if(undoOp.isEmpty())
        {
            isUndoButtonActive.setValue(false);
        }else
        {
            isUndoButtonActive.setValue(true);
        }

        if(redoOp.isEmpty())
        {
            isRedoButtonActive.setValue(false);
        }else
        {
            isRedoButtonActive.setValue(true);
        }
    }

    private void calculatorOperationChecks()
    {
        setAllCalculatorOperationListFalse();
        switch (operatorNumber.getNumOperator()) {
            case Add:
                isAddButtonActive.setValue(true);
                break;
            case Minus:
                isMinusButtonActive.setValue(true);
                break;
            case Multiply:
                isMultiplyButtonActive.setValue(true);
                break;
            case Division:
                isDivisionButtonActive.setValue(true);
                break;
        }
    }

    private void setAllCalculatorOperationListFalse()
    {
        for (MutableLiveData<Boolean> item:allCalculatorOperation)
        {

            item.setValue(!item.getValue());
        }
    }
}
