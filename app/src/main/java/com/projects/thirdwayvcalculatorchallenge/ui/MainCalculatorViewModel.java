package com.projects.thirdwayvcalculatorchallenge.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projects.thirdwayvcalculatorchallenge.data.CalculatorOperation;
import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * viewModel for MainActivity
 */
public class MainCalculatorViewModel extends ViewModel {
    private static final String TAG = "MainCalculatorViewModel";
    /**
     * MutableLivedata for observing result change and hold result to show at calculator screen
     */
    private MutableLiveData<Float> resultMutableLiveData = new MutableLiveData<>();
    /**
     * For encapsulate resultMutableLiveData
     * @return resultMutableLiveData
     */
    public MutableLiveData<Float> getResultMutableLiveData() {
        return resultMutableLiveData;
    }
    /**
     *  operatorNumber object to hold current operation
     */
    private OperatorNumber operatorNumber;
    /**
     * MutableLivedata of operations list hold every operation performed and observe any change
     * to create recyclerview list
     */
    private MutableLiveData<List<OperatorNumber>> historyListMutableLiveData = new MutableLiveData<>();
    /**
     * For encapsulate historyListMutableLiveData
     * @return historyListMutableLiveData
     */
    public MutableLiveData<List<OperatorNumber>> getHistoryListMutableLiveData() {
        return historyListMutableLiveData;
    }

    /**
     * list of operation for tracking operations and to added to historyListMutableLiveData
     */
    private List<OperatorNumber> historyList = new ArrayList<>();
    /**
     * For checking EqualButton active state and observe any change to update equal button
     */
    private MutableLiveData<Boolean> isEqualButtonActive = new MutableLiveData<>(false);
    /**
     * For encapsulate isEqualButtonActive
     * @return isEqualButtonActive
     */
    public MutableLiveData<Boolean> getIsEqualButtonActive() {
        return isEqualButtonActive;
    }
    /**
     * For encapsulate isEqualButtonActive
     * @param active so set isEqualButtonActive
     */
    public void setIsEqualButtonActive(Boolean active) {
        this.isEqualButtonActive.setValue(active);
    }
    /**
     * For checking UndoButton active state and observe any change to update undo button
     */
    private MutableLiveData<Boolean> isUndoButtonActive = new MutableLiveData<>();
    /**
     * For encapsulate isUndoButtonActive
     * @return isUndoButtonActive
     */
    public MutableLiveData<Boolean> getIsUndoButtonActive() {
        return isUndoButtonActive;
    }
    /**
     * For checking RedoButton active state and observe any change to update redo button
     */
    private MutableLiveData<Boolean> isRedoButtonActive = new MutableLiveData<>();
    /**
     * For encapsulate isRedoButtonActive
     * @return isRedoButtonActive
     */
    public MutableLiveData<Boolean> getIsRedoButtonActive() {
        return isRedoButtonActive;
    }
    /**
     * For checking AddButton active state and observe any change to update Add button
     */
    private MutableLiveData<Boolean> isAddButtonActive = new MutableLiveData<>(true);
    /**
     * For encapsulate isAddButtonActive
     * @return isAddButtonActive
     */
    public MutableLiveData<Boolean> getIsAddButtonActive() {
        return isAddButtonActive;
    }
    /**
     * For checking MinusButton active state and observe any change to update minus button
     */
    private MutableLiveData<Boolean> isMinusButtonActive = new MutableLiveData<>(true);
    /**
     * For encapsulate isMinusButtonActive
     * @return isMinusButtonActive
     */
    public MutableLiveData<Boolean> getIsMinusButtonActive() {
        return isMinusButtonActive;
    }
    /**
     * For checking MultiplyButton active state and observe any change to update multiply button
     */
    private MutableLiveData<Boolean> isMultiplyButtonActive = new MutableLiveData<>(true);
    /**
     * For encapsulate isMultiplyButtonActive
     * @return isMultiplyButtonActive
     */
    public MutableLiveData<Boolean> getIsMultiplyButtonActive() {
        return isMultiplyButtonActive;
    }
    /**
     * For checking DivisionButton active state and observe any change to update division button
     */
    private MutableLiveData<Boolean> isDivisionButtonActive = new MutableLiveData<>(true);
    /**
     * For encapsulate isDivisionButtonActive
     * @return isDivisionButtonActive
     */
    public MutableLiveData<Boolean> getIsDivisionButtonActive() {
        return isDivisionButtonActive;
    }

    /**
     * list of MutableLiveData to store operation Buttons active state to perform for loop method to all of them
     */
    private List<MutableLiveData<Boolean>> operationButtonsState = new ArrayList<>();
    /**
     * ArrayDeque of list of OperatorNumber to store everything that happen from start to end even undo and redo result
     * every list of turn stored
     */
    private Deque<List<OperatorNumber>> allHistoryOperation= new ArrayDeque<>();
    /**
     * ArrayDeque of list of OperatorNumber to store like allHistoryOperation but this can changed to remove and add to it
     * to perform undo method
     */
    private Deque<List<OperatorNumber>> undoListsOperations = new ArrayDeque<>();
    /**
     * ArrayDeque of list of OperatorNumber to store what removed from undoOp but this can changed to remove and add to it
     * to perform redo method
     */
    private Deque<List<OperatorNumber>> redoListsOperations = new ArrayDeque<>();
    /**
     * viewModel Constructor to instantiate variables for once
     */
    public MainCalculatorViewModel() {
        resultMutableLiveData.setValue(0f);
        operatorNumber = new OperatorNumber();
        operationButtonsState.add(isAddButtonActive);
        operationButtonsState.add(isMinusButtonActive);
        operationButtonsState.add(isMultiplyButtonActive);
        operationButtonsState.add(isDivisionButtonActive);
    }

    /**
     * Calculation method for perform calculate operation when equal button clicked
     * adding one operation for history list that track current list of operations showed in cells to
     * added to historyListMutableLiveData to update recycler view adapter
     * equal for calculate every list in history list after operation
     * updating addingToDeque to add every list happened to allHistoryOperation and undoListsOperations too
     * and check buttons state to active all operations button
     */
    public void Calculation() {
        historyList.add(operatorNumber);
        historyListMutableLiveData.setValue(historyList);
        equal();
        addingToDeque();
        calculatorOperationChecks();
    }

    /**
     * Removing on operation from recyclerview adapter when user choice item to delete
     * and update recyclerview adapter by historyListMutableLiveData and equal the result list again
     * updating addingToDeque to add every list happened to allHistoryOperation and undoListsOperations too
     * @param deletedOperatorNumber item that clicked
     */
    public void removeHistoryList(OperatorNumber deletedOperatorNumber) {
        historyList.remove(deletedOperatorNumber);
        historyListMutableLiveData.setValue(historyList);
        equal();
        addingToDeque();
    }

    /**
     * To track all operations from start to end and checks undo redo active state
     */
    private void addingToDeque() {
        List<OperatorNumber> operatorNumbers = new ArrayList<>(historyList);
        allHistoryOperation.add(operatorNumbers);
        undoListsOperations =new ArrayDeque<>(allHistoryOperation);
        redoListsOperations.clear();
        UndoRedoButtonsChecks();
    }

    /**
     * To set current CurrentOperation when user clicked sign button
     * and check operation button active state to active one and disabled others
     * @param currentOperation current CurrentOperation to store it in operatorNumber
     */
    public void setCurrentOperation(CalculatorOperation currentOperation) {
        operatorNumber = new OperatorNumber();
        operatorNumber.setNumOperator(currentOperation);
        if(isEqualButtonActive.getValue()==false) {
            calculatorOperationChecks();
            isEqualButtonActive.setValue(true);
        }
    }

    /**
     * to set current value of input number performed by user
     * @param currentInputNum current operation value to store it in operatorNumber
     */
    public void setCurrentInputNum(Float currentInputNum) {
        operatorNumber.setNumValue(currentInputNum);
    }

    /**
     * undo method to back on step and to perform that it delete last list of undo list and added
     * to redo list after removing get last list from undo list and update allHistoryOperation that track every operation,
     * historyListMutableLiveData to update recycler view adapter,
     * and equal last list that passed to adapter.
     * check undo button active state to active when it has operation to perform and disable when empty
     */
    public void undo()
    {
        List<OperatorNumber> operatorNumbers = undoListsOperations.pollLast();
        redoListsOperations.add(operatorNumbers);
        if(!undoListsOperations.isEmpty())
        {
            allHistoryOperation.add(undoListsOperations.getLast());
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
    /**
     * redo method to move forward on step after undo to perform that it delete last list of redo list and added
     * to undo list and update allHistoryOperation that track every operation,
     * historyListMutableLiveData to update recycler view adapter,
     * and equal last list that passed to adapter.
     * check redo button active state to active when it has operation to perform and disable when empty
     */
    public void redo() {

        if (!redoListsOperations.isEmpty()) {
            List<OperatorNumber> operatorNumbers = redoListsOperations.pollLast();
            undoListsOperations.add(operatorNumbers);
            allHistoryOperation.add(operatorNumbers);
            List<OperatorNumber> lastListOperatorNumbers = allHistoryOperation.getLast();
            historyList = new ArrayList<>(lastListOperatorNumbers);
            historyListMutableLiveData.setValue(historyList);
            equal();
            isUndoButtonActive.setValue(true);
        }
        UndoRedoButtonsChecks();
    }

    /**
     * equal every operation in historyList and update final result
     */
    private void equal() {
        float result=0;
        for (OperatorNumber operatorNumber1: Objects.requireNonNull(historyListMutableLiveData.getValue())) {
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

    /**
     *To Check undo redo buttons active state
     */
    private void UndoRedoButtonsChecks()
    {
        if(undoListsOperations.isEmpty())
        {
            isUndoButtonActive.setValue(false);
        }else
        {
            isUndoButtonActive.setValue(true);
        }

        if(redoListsOperations.isEmpty())
        {
            isRedoButtonActive.setValue(false);
        }else
        {
            isRedoButtonActive.setValue(true);
        }
    }
    /**
     *To Check operations buttons active state
     */
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

    /**
     *To active all or disable all operations buttons
     */
    private void setAllCalculatorOperationListFalse()
    {
        for (MutableLiveData<Boolean> item: operationButtonsState)
        {

            item.setValue(!item.getValue());
        }
    }
}
