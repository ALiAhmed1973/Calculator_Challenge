package com.projects.thirdwayvcalculatorchallenge.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.projects.thirdwayvcalculatorchallenge.data.CalculatorOperation;
import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class MainCalculatorViewModelTest {
    MainCalculatorViewModel viewModel;
    OperatorNumber operatorNumber ;
    OperatorNumber operatorNumber2 ;
    OperatorNumber operatorNumber3 ;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUpViewModel()
    {
        viewModel= new MainCalculatorViewModel();
        operatorNumber = new OperatorNumber(CalculatorOperation.Add,3f);
        operatorNumber2 = new OperatorNumber(CalculatorOperation.Add,2f);
        operatorNumber3 = new OperatorNumber(CalculatorOperation.Multiply,5f);
    }
    @Test
    public void setCurrentNumOperator()
    {
        viewModel.setCurrentOperation(CalculatorOperation.Add);
        assertThat(viewModel.getOperatorNumber().getNumOperator(), Matchers.equalTo(CalculatorOperation.Add));
    }
    @Test
    public void setCurrentNumValue()
    {
        viewModel.setCurrentInputNum(5f);
        assertThat(viewModel.getOperatorNumber().getNumValue(), Matchers.equalTo(5f));
    }
    @Test
    public void checkEqualButtonStateNotActive()
    {
        assertThat(viewModel.getIsEqualButtonActive().getValue(),Matchers.equalTo(false));
    }
    @Test
    public void checkEqualButtonStateActive()
    {
        viewModel.setCurrentOperation(CalculatorOperation.Add);
        assertThat(viewModel.getIsEqualButtonActive().getValue(),Matchers.equalTo(true));
    }
    @Test
    public void allCalculationOperatorsStateIsNOtActive()
    {
        viewModel.setAllCalculatorOperationListFalse();
        assertThat(viewModel.getIsAddButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsMinusButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsMultiplyButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsDivisionButtonActive().getValue(),Matchers.equalTo(false));
    }

    @Test
    public void checkCalculationOperatorsState()
    {
        viewModel.setCurrentOperation(CalculatorOperation.Add);
        viewModel.setCurrentInputNum(5f);
        assertThat(viewModel.getIsAddButtonActive().getValue(),Matchers.equalTo(true));
        assertThat(viewModel.getIsMinusButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsMultiplyButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsDivisionButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsEqualButtonActive().getValue(),Matchers.equalTo(true));
    }

    @Test
    public void equalAndTestResult()
    {
        List<OperatorNumber> operatorNumberList = Arrays.asList(operatorNumber,operatorNumber2,operatorNumber3);
       viewModel.equal(operatorNumberList);
       float result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(25f));
    }

    @Test
    public void removeFromRecyclerViewCellsAndTestResult()
    {
        List<OperatorNumber> operatorNumberList = new ArrayList<>();
        operatorNumberList.add(operatorNumber);
        operatorNumberList.add(operatorNumber2);
        operatorNumberList.add(operatorNumber3);
        viewModel.setHistoryList(operatorNumberList);
        viewModel.removeHistoryList(operatorNumber2);
        float result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(15f));
    }

    @Test
    public void calculateThreeAdded_TwoEqualFiveAndShouldEqualSignDisabled()
    {
        viewModel.setOperatorNumber(operatorNumber);
        viewModel.calculation();
        float result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(3f));
        viewModel.setOperatorNumber(operatorNumber2);
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(5f));
        assertThat(viewModel.getIsEqualButtonActive().getValue(),Matchers.equalTo(false));
        assertThat(viewModel.getIsAddButtonActive().getValue(),Matchers.equalTo(true));
        assertThat(viewModel.getIsMinusButtonActive().getValue(),Matchers.equalTo(true));
        assertThat(viewModel.getIsMultiplyButtonActive().getValue(),Matchers.equalTo(true));
        assertThat(viewModel.getIsDivisionButtonActive().getValue(),Matchers.equalTo(true));
    }

    @Test
    public void undoThreeTimesAndResultEqualThree()
    {
        viewModel.setOperatorNumber(operatorNumber);
        viewModel.calculation();
        float result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(3f));
        viewModel.setOperatorNumber(operatorNumber2);
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(5f));
        viewModel.setOperatorNumber(operatorNumber3);
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(25f));
        viewModel.setOperatorNumber(new OperatorNumber(CalculatorOperation.Minus,5f));
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(20f));
        for (int i=0;i<3;i++)
        {
            viewModel.undo();
        }
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(3f));
    }

    @Test
    public void undoTwoTimesAndRedoOneTimeAndAddedThreeThenUndoFourTimesResultEqualTwentyFive()
    {
        viewModel.setOperatorNumber(operatorNumber);
        viewModel.calculation();
        float result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(3f));
        viewModel.setOperatorNumber(operatorNumber2);
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(5f));
        viewModel.setOperatorNumber(operatorNumber3);
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(25f));
        for (int i=0;i<2;i++)
        {
            viewModel.undo();
        }
        for (int i=0;i<1;i++)
        {
            viewModel.redo();
        }
        viewModel.setOperatorNumber(new OperatorNumber(CalculatorOperation.Add,3f));
        viewModel.calculation();
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(8f));
        for (int i=0;i<4;i++)
        {
            viewModel.undo();
        }
        result = viewModel.getResultMutableLiveData().getValue();
        assertThat(result,Matchers.equalTo(25f));
    }
}