package com.projects.thirdwayvcalculatorchallenge.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.thirdwayvcalculatorchallenge.data.CalculatorOperation;
import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;
import com.projects.thirdwayvcalculatorchallenge.databinding.ActivityMainBinding;

/**
 * Main Activity Hold Main Calculator screen
 */
public class MainActivity extends AppCompatActivity implements HistoryAdapter.OnItemClickListener {
    /**
     * For implement View Binding
     */
    private ActivityMainBinding binding;
    /**
     * Declare Main view model for this activity
     */
    private MainCalculatorViewModel viewModel;
    /**
     * Recyclerview Adapter for converting results to cells of results
     */
    private HistoryAdapter historyAdapter;
    /**
     * Variable tha hold calculatorOperation type to pass it for data binding
     */
    private CalculatorOperation calculatorOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Implement view binding to get views from xml
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        /**
         * Instantiate MainCalculatorViewModel
         */
        viewModel = new ViewModelProvider(this).get(MainCalculatorViewModel.class);
        /**
         * Set viewModel in activity_main xml variable
         */
        binding.setViewModel(viewModel);
        /**
         * Set calculatorOperation in activity_main xml variable
         */
        binding.setCalculatorOperation(calculatorOperation);
        binding.setLifecycleOwner(this);
        /**
         * Instantiate Recyclerview adapter and set recycler layout manager to be grid with 3 columns scrolling vertically
         */
        historyAdapter = new HistoryAdapter( this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        binding.historyRecyclerview.setLayoutManager(gridLayoutManager);
        binding.historyRecyclerview.setAdapter(historyAdapter);

        /**
         * equal button to calculate all operations after adding Calculator Operation and input Number from user
         */
        binding.equalButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.editTextNumber.getText().toString())) {
                viewModel.setCurrentInputNum(Float.parseFloat(binding.editTextNumber.getText().toString()));
                viewModel.calculation();
                binding.editTextNumber.setText("");
            }
        });

        /**
         * Live data to observe the change that happened in history of operations performed
         * and pass the changed value to recycler adapter as argument
         */
        viewModel.getHistoryListMutableLiveData().observe(this, historylist -> {
            historyAdapter.setList(historylist);
        });


    }

    /**
     * Recyclerview interface method to track items when clicked
     * after user touch the item would deleted this item from recycler view adapter list
     * @param operatorNumber one of the operations that would be deleted
     */
    @Override
    public void onItemClick(OperatorNumber operatorNumber) {
        viewModel.removeHistoryList(operatorNumber);
    }
}