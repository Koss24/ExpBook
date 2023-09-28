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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView expenseList;
    ArrayAdapter<Expense> expenseAdapter;
    ArrayList<Expense> dataList;
    EditText form_expense_name;

    Expense edited_expense = null;
    SimpleDateFormat formatter;
    private static final int ADD_EXPENSE_REQUEST_CODE = 1;
    private static final int EDIT_EXPENSE_REQUEST_CODE = 2;
    private static final int DEL_EXPENSE_REQUEST_CODE = 3;

    Expense selectedFromList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        form_expense_name = this.findViewById(R.id.expense_name_field);

        expenseList = findViewById(R.id.expense_list);

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");

        // catch an error maybe?

        Expense exp1 = new Expense("Expense 1", "2023-3", 123, "comment");
        Expense []expenses = {exp1};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(expenses));

        expenseAdapter = new ExpenseArrayAdapter(this, R.layout.content, dataList);

        expenseList.setAdapter(expenseAdapter);

        FloatingActionButton go_to_add_expense = findViewById(R.id.go_to_add_expense);
        go_to_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE);
            }
        });

        expenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = (Expense) expenseList.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("Edit_Expense", expense);
                intent.putExtra("position", position);
                startActivityForResult(intent, EDIT_EXPENSE_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(ADD_EXPENSE_REQUEST_CODE == requestCode) {
                if (resultCode == RESULT_OK) {

                    String expense_name = data.getStringExtra("Expense_name");
                    int expense_charge = Integer.parseInt(data.getStringExtra("Expense_charge"));
                    String expense_comment = data.getStringExtra("Expense_comment");
                    String expense_date_year = data.getStringExtra("Expense_date_year");
                    String expense_date_month = data.getStringExtra("Expense_date_month");


                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
                    String formattedDate = expense_date_year + "-" + expense_date_month;

                    Expense added_expense = new Expense(expense_name, formattedDate, expense_charge, expense_comment);

                    // Add the user input to your dataList (expense list)
                    dataList.add(added_expense);

                    // Notify the adapter that the data set has changed
                    expenseAdapter.notifyDataSetChanged();
                }
            } else if(EDIT_EXPENSE_REQUEST_CODE == requestCode) {
                if (resultCode == RESULT_OK) {
                    if(data.hasExtra("isDeleted")){
                        int position = data.getIntExtra("Position", -1);
                        if (position != -1) {
                            // Update the expenseList and refresh the ListView
                            dataList.remove(position);
                            expenseAdapter.notifyDataSetChanged();  // Update the ListView
                        }
                    } else {

                        String expense_name = data.getStringExtra("Expense_name");
                        int edit_expense_charge = Integer.parseInt(data.getStringExtra("Expense_charge"));
                        String expense_comment = data.getStringExtra("Expense_comment");
                        String expense_date_year = data.getStringExtra("Expense_date_year");
                        String expense_date_month = data.getStringExtra("Expense_date_month");


                        int position = data.getIntExtra("Position", -1);

                        String formattedDate = expense_date_year + "-" + expense_date_month;
                        Expense edited_expense = new Expense(expense_name, formattedDate, edit_expense_charge, expense_comment);


                        if (position != -1) {
                            // Update the expenseList and refresh the ListView
                            dataList.set(position, edited_expense);
                            expenseAdapter.notifyDataSetChanged();  // Update the ListView
                        }
                    }

                }
            } else if (DEL_EXPENSE_REQUEST_CODE == requestCode) {
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("Position", -1);
                    if (position != -1) {
                        // Update the expenseList and refresh the ListView
                        dataList.remove(position);
                        expenseAdapter.notifyDataSetChanged();  // Update the ListView
                    }

                }
            }

    }
}


