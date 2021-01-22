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




        binding.equalButton.setOnClickListener(v->{
            if(!TextUtils.isEmpty(binding.editTextNumber.getText().toString()))
            {
                viewModel.setCurrentInputNum(Float.parseFloat(binding.editTextNumber.getText().toString()));
                viewModel.setIsEqualButtonActive(false);
                viewModel.Calculation();
                binding.editTextNumber.setText("");
            }
        });

        viewModel.getHistoryListMutableLiveData().observe(this,historylist->{
            historyAdapter.setList(historylist);
        });
        viewModel.getIsEqualButtonActive().observe(this,isActive->{
            binding.equalButton.setEnabled(isActive);
        });

        viewModel.getIsUndoButtonActive().observe(this,isActive->{
            binding.undoButton.setEnabled(isActive);
        });
        viewModel.getIsRedoButtonActive().observe(this,isActive->{
            binding.redoButton.setEnabled(isActive);
        });

        viewModel.getIsAddButtonActive().observe(this,isActive->{
            binding.addButton.setEnabled(isActive);
        });
        viewModel.getIsMinusButtonActive().observe(this,isActive->{
            binding.minusButton.setEnabled(isActive);
        });
        viewModel.getIsMultiplyButtonActive().observe(this,isActive->{
            binding.multiplyButton.setEnabled(isActive);
        });
        viewModel.getIsDivisionButtonActive().observe(this,isActive->{
            binding.dividingButton.setEnabled(isActive);
        });

    }

    @Override
    public void onItemClick(OperatorNumber operatorNumber) {
        viewModel.removeHistoryList(operatorNumber);
    }
}