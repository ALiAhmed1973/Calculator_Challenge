package com.projects.thirdwayvcalculatorchallenge;

import com.projects.thirdwayvcalculatorchallenge.data.CalculatorOperation;
import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class UndoRedoTest {
    UndoRedo undoRedo;
    Deque<List<OperatorNumber>> allHistoryOperation;
    List<OperatorNumber> operatorNumberList1;
    List<OperatorNumber> operatorNumberList2;
    @Before
    public void setUp()
    {
        undoRedo= new UndoRedo();
        allHistoryOperation= new ArrayDeque<>();

        operatorNumberList1= Arrays.asList(new OperatorNumber(CalculatorOperation.Add,5f),
                new OperatorNumber(CalculatorOperation.Multiply,6f));

        operatorNumberList2= Arrays.asList(new OperatorNumber(CalculatorOperation.Add,7f),
                new OperatorNumber(CalculatorOperation.Division,8f));
    }

    @Test
    public void updateUndoList()
    {
        allHistoryOperation.add(operatorNumberList1);
        undoRedo.updateUndoList(allHistoryOperation);
        assertThat(undoRedo.getUndoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getUndoListsOperations().getFirst(), Matchers.equalTo(allHistoryOperation.getFirst()));
    }

    @Test
    public void undoOperationAddToRedoListEmptyUndoList()
    {
        allHistoryOperation.add(operatorNumberList1);
        undoRedo.updateUndoList(allHistoryOperation);
        undoRedo.getLastListUndo();
        assertThat(undoRedo.getUndoListsOperations().size(), Matchers.equalTo(0));
        assertThat(undoRedo.getRedoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getRedoListsOperations().getFirst(), Matchers.equalTo(allHistoryOperation.getFirst()));
    }

    @Test
    public void undoOperationAddToRedoListNotEmptyUndoList()
    {
        allHistoryOperation.add(operatorNumberList1);
        allHistoryOperation.add(operatorNumberList2);
        undoRedo.updateUndoList(allHistoryOperation);
        undoRedo.getLastListUndo();
        undoRedo.undoRedoButtonsChecks();

        assertThat(undoRedo.getUndoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getRedoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getIsUndoButtonActive(), Matchers.equalTo(true));
        assertThat(undoRedo.getIsRedoButtonActive(), Matchers.equalTo(true));
        assertThat(undoRedo.getRedoListsOperations().getLast(), Matchers.equalTo(allHistoryOperation.getLast()));
        assertThat(undoRedo.getUndoListsOperations().getLast(), Matchers.equalTo(allHistoryOperation.getFirst()));
    }

    @Test
    public void redoOperationAddToUndoListEmptyRedoList()
    {
        allHistoryOperation.add(operatorNumberList1);
        undoRedo.UpdateRedoList(allHistoryOperation);
        undoRedo.getLastListRedo();
        assertThat(undoRedo.getRedoListsOperations().size(), Matchers.equalTo(0));
        assertThat(undoRedo.getUndoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getUndoListsOperations().getLast(), Matchers.equalTo(allHistoryOperation.getLast()));
    }
    @Test
    public void redoOperationAddToUndoListNotEmptyRedoList()
    {
        allHistoryOperation.add(operatorNumberList1);
        allHistoryOperation.add(operatorNumberList2);
        undoRedo.UpdateRedoList(allHistoryOperation);
        undoRedo.getLastListRedo();
        assertThat(undoRedo.getRedoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getUndoListsOperations().size(), Matchers.equalTo(1));
        assertThat(undoRedo.getRedoListsOperations().getLast(), Matchers.equalTo(allHistoryOperation.getFirst()));
        assertThat(undoRedo.getRedoListsOperations().getLast(), Matchers.equalTo(operatorNumberList1));
        assertThat(undoRedo.getUndoListsOperations().getLast(), Matchers.equalTo(allHistoryOperation.getLast()));
        assertThat(undoRedo.getUndoListsOperations().getLast(), Matchers.equalTo(operatorNumberList2));
    }
    @Test
    public void checkUndoEmptyNotActive()
    {
        undoRedo.undoRedoButtonsChecks();
        assertThat(undoRedo.getIsUndoButtonActive(), Matchers.equalTo(false));
    }

    @Test
    public void checkRedoEmptyNotActive()
    {
        undoRedo.undoRedoButtonsChecks();
        assertThat(undoRedo.getIsRedoButtonActive(), Matchers.equalTo(false));
    }

    @Test
    public void checkUndoNotEmptyAndActive()
    {
        allHistoryOperation.add(operatorNumberList1);
        undoRedo.updateUndoList(allHistoryOperation);
        undoRedo.undoRedoButtonsChecks();
        assertThat(undoRedo.getIsUndoButtonActive(), Matchers.equalTo(true));
    }
    @Test
    public void checkRedoNotEmptyAndActive()
    {
        allHistoryOperation.add(operatorNumberList1);
        undoRedo.UpdateRedoList(allHistoryOperation);
        undoRedo.undoRedoButtonsChecks();
        assertThat(undoRedo.getIsRedoButtonActive(), Matchers.equalTo(true));
    }

}