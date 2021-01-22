package com.projects.thirdwayvcalculatorchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.projects.thirdwayvcalculatorchallenge.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements HistoryAdapter.OnItemClickListener {
    private ActivityMainBinding binding;
    private MainCalculatorViewModel viewModel;
    private HistoryAdapter historyAdapter;
    private CalculatorOperation calculatorOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel= new ViewModelProvider(this).get(MainCalculatorViewModel.class);
        binding.setViewModel(viewModel);
       binding.setCalculatorOperation(calculatorOperation);
        binding.setLifecycleOwner(this);
        historyAdapter = new HistoryAdapter(this,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        binding.historyRecyclerview.setLayoutManager(gridLayoutManager);
        binding.historyRecyclerview.setAdapter(historyAdapter);


//        binding.addButton.setOnClickListener(v -> {
//            viewModel.setCurrentOperation(CalculatorOperation.Add);
//        });
//        binding.minusButton.setOnClickListener(v -> {
//            viewModel.setCurrentOperation(CalculatorOperation.Minus);
//        });
//        binding.multiplyButton.setOnClickListener(v -> {
//            viewModel.setCurrentOperation(CalculatorOperation.Multiply);
//        });
//        binding.dividingButton.setOnClickListener(v -> {
//            viewModel.setCurrentOperation(CalculatorOperation.Division);
//        });

        binding.equalButton.setOnClickListener(v->{
            if(!TextUtils.isEmpty(binding.editTextNumber.getText().toString()))
            {
                viewModel.setCurrentInputNum(Float.parseFloat(binding.editTextNumber.getText().toString()));
                viewModel.setIsEqualButtonActive(false);
                viewModel.Calculation();
                binding.editTextNumber.setText("");
            }
        });
        binding.undoButton.setOnClickListener(v->{
            viewModel.undo();
        });
        binding.redoButton.setOnClickListener(v->{
            viewModel.redo();
        });

//        viewModel.resultMutableLiveData.observe(this, result -> {
//            binding.resultTextView.setText(result.toString());
//        });

        viewModel.historyListMutableLiveData.observe(this,historylist->{
            historyAdapter.setList(historylist);
        });
        viewModel.isEqualButtonActive.observe(this,isActive->{
            binding.equalButton.setEnabled(isActive);
        });

        viewModel.isUndoButtonActive.observe(this,isActive->{
            binding.undoButton.setEnabled(isActive);
        });
        viewModel.isRedoButtonActive.observe(this,isActive->{
            binding.redoButton.setEnabled(isActive);
        });

        viewModel.isAddButtonActive.observe(this,isActive->{
            binding.addButton.setEnabled(isActive);
        });
        viewModel.isMinusButtonActive.observe(this,isActive->{
            binding.minusButton.setEnabled(isActive);
        });
        viewModel.isMultiplyButtonActive.observe(this,isActive->{
            binding.multiplyButton.setEnabled(isActive);
        });
        viewModel.isDivisionButtonActive.observe(this,isActive->{
            binding.dividingButton.setEnabled(isActive);
        });

    }

    @Override
    public void onItemClick(OperatorNumber operatorNumber) {
        viewModel.removeHistoryList(operatorNumber);
    }
}