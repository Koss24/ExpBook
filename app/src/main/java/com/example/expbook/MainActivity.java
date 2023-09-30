package com.example.expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AddExpenseFragment.OnFragmentInteractionListener {
    ListView expenseList;
    ArrayAdapter<Expense> expenseAdapter;
    ArrayList<Expense> dataList;
    private Expense selectedExpense;
    private TextView total_expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BigDecimal charge = BigDecimal.valueOf(123.00);

        total_expense = findViewById(R.id.total_expense_val);

        dataList = new ArrayList<Expense>();

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");

        expenseList = findViewById(R.id.expense_list);

        expenseAdapter = new ExpenseArrayAdapter(this, dataList);

        expenseList.setAdapter(expenseAdapter);

        expenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedExpense = (Expense) parent.getItemAtPosition(position);

                AddExpenseFragment editExpenseFragment = AddExpenseFragment.newInstance(selectedExpense);
                editExpenseFragment.show(getSupportFragmentManager(),"EDIT_CITY");
            }
        });

        final FloatingActionButton addButton = findViewById(R.id.go_to_add_expense);
        addButton.setOnClickListener(v -> {
            new AddExpenseFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

    }

    @Override
    public void onOKPressed(Expense expense) {
        if (selectedExpense != null) {

            selectedExpense.setName(expense.getName());
            selectedExpense.setCharge(expense.getCharge());
            selectedExpense.setComment(expense.getComment());
            selectedExpense.setMonth(expense.getMonth());
            selectedExpense.setYear(expense.getYear());

            selectedExpense = null;
        } else {

            dataList.add(expense);
        }
        BigDecimal sum = new BigDecimal(0);

        for(Expense exp: dataList){

            sum = sum.add(exp.getCharge());
        }
        String total = "$"+String.format("%.02f",sum);
        total_expense.setText(total);
        expenseAdapter.notifyDataSetChanged();

    }
}


