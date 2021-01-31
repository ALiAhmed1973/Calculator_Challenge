package com.projects.thirdwayvcalculatorchallenge;


import androidx.annotation.VisibleForTesting;

import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class UndoRedo {

    private Deque<List<OperatorNumber>> undoListsOperations = new ArrayDeque<>();
    public Deque<List<OperatorNumber>> getUndoListsOperations() {
        return undoListsOperations;
    }


    /**
     * ArrayDeque of list of OperatorNumber to store what removed from undoOp but this can changed to remove and add to it
     * to perform redo method
     */
    private Deque<List<OperatorNumber>> redoListsOperations = new ArrayDeque<>();


    public void UpdateRedoList(Deque<List<OperatorNumber>> redoListsOperations) {
        this.redoListsOperations = new ArrayDeque<>(redoListsOperations);
    }


    public Deque<List<OperatorNumber>> getRedoListsOperations() {
        return redoListsOperations;
    }

    private Boolean isUndoButtonActive ;

    public Boolean getIsUndoButtonActive() {
        return isUndoButtonActive;
    }

    private Boolean isRedoButtonActive ;

    public Boolean getIsRedoButtonActive() {
        return isRedoButtonActive;
    }

    public void updateUndoList(Deque<List<OperatorNumber>> allHistoryOperation)
    {
        undoListsOperations =new ArrayDeque<>(allHistoryOperation);
        redoListsOperations.clear();
    }

    public List<OperatorNumber> getLastListUndo()
    {
        List<OperatorNumber> operatorNumbers = undoListsOperations.pollLast();
        redoListsOperations.add(operatorNumbers);
        if(!undoListsOperations.isEmpty())
        {
          return undoListsOperations.getLast();
        }else
        {
            List<OperatorNumber> newOp = new ArrayList<>(0);
          return  newOp;
        }
    }
    public List<OperatorNumber> getLastListRedo()
    {
        if (!redoListsOperations.isEmpty()) {
            List<OperatorNumber> operatorNumbers = redoListsOperations.pollLast();
             undoListsOperations.add(operatorNumbers);
             return operatorNumbers;
        }else
        {
            return new ArrayList<>();
        }
    }
    /**
     *To Check undo redo buttons active state
     */
    public void undoRedoButtonsChecks()
    {
        if(undoListsOperations.isEmpty())
        {
            isUndoButtonActive=false;
        }else
        {
            isUndoButtonActive=true;
        }

        if(redoListsOperations.isEmpty())
        {
            isRedoButtonActive=false;
        }else
        {
            isRedoButtonActive=true;
        }
    }
}
