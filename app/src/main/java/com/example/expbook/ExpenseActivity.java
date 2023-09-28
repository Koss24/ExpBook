package com.example.expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.text.DateFormatSymbols;

public class ExpenseActivity extends AppCompatActivity {
    EditText form_expense_name;
    EditText form_expense_comment;
    EditText form_expense_charge;
    Expense expense_to_edit;
    Button del_expense;
    Button add_expense;
    private int position;
    private DatePickerDialog datePickerDialog;
    private Spinner year_spinner, month_spinner;
    private String year_selected;
    private String month_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_page);

        Intent intent = getIntent();

        expense_to_edit = null;

        year_selected = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        month_selected = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
        add_expense = findViewById(R.id.add_expense);

        del_expense = findViewById(R.id.del_expense);
        del_expense.setVisibility(View.INVISIBLE);

        year_spinner = findViewById(R.id.year_spinner);
        month_spinner = findViewById(R.id.month_spinner);

        setUpYearSpinner();
        setUpMonthSpinner();

        form_expense_name = this.findViewById(R.id.expense_name_field);
        form_expense_comment = this.findViewById(R.id.expense_comment_field);
        form_expense_charge = this.findViewById(R.id.expense_charge_field);

        if (intent != null && intent.hasExtra("Edit_Expense")) {
            del_expense.setVisibility(View.VISIBLE);

            expense_to_edit = (Expense) intent.getSerializableExtra("Edit_Expense");
            position = intent.getIntExtra("position", position);

            form_expense_name.setText(expense_to_edit.getName());
            form_expense_comment.setText(String.valueOf(expense_to_edit.getComment()));
            form_expense_charge.setText(String.valueOf(expense_to_edit.getCharge()));

            year_spinner.setSelection(Integer. parseInt(expense_to_edit.getYear()) - (Calendar.getInstance().get(Calendar.YEAR) - 10));
            month_spinner.setSelection(Integer. parseInt(expense_to_edit.getMonth())- 1);

            this.month_selected = expense_to_edit.getMonth();
            this.year_selected = expense_to_edit.getYear();

            add_expense.setText("Edit Expense");

            add_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String expense_name = form_expense_name.getText().toString().trim();
                    String expense_comment = form_expense_comment.getText().toString().trim();
                    String expense_charge = form_expense_charge.getText().toString().trim();

                    if (validate_user_input(expense_name,expense_comment, expense_charge)){
                        Intent edit_expense_intent = new Intent();
                        send_data(edit_expense_intent, "false");
//                        edit_expense_intent.putExtra("Edit_Expense_name", expense_name);
//                        edit_expense_intent.putExtra("Edit_Expense_comment", expense_comment);
//                        edit_expense_intent.putExtra("Edit_Expense_date_year", getYear_selected());
//                        edit_expense_intent.putExtra("Edit_Expense_date_month", getMonth_selected());
//                        edit_expense_intent.putExtra("Edit_Expense_charge", expense_charge);
//                        edit_expense_intent.putExtra("Position", position);
//                        setResult(RESULT_OK, edit_expense_intent);
//                        finish();
                    } else {
                        form_expense_name.setHint("Name can only be 15 character long");
                        form_expense_name.setHintTextColor(getColor(R.color.incorrect_input));

                        form_expense_comment.setHint("Comment can only be 20 character long");
                        form_expense_comment.setHintTextColor(getColor(R.color.incorrect_input));

                        form_expense_charge.setHint("Charge cannot be negative");
                        form_expense_charge.setHintTextColor(getColor(R.color.incorrect_input));
                    }


                }
            });

            del_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent delete_expense_intent = new Intent();
                    send_data(delete_expense_intent, "true");
//                    delete_expense_intent.putExtra("isDeleted", "true");
//                    delete_expense_intent.putExtra("Position", position);
//                    setResult(RESULT_OK, delete_expense_intent);
//                    finish();
                }
            });

        } else {
            month_spinner.setSelection(Calendar.getInstance().get(Calendar.MONTH));
            year_spinner.setSelection(Calendar.getInstance().get(Calendar.YEAR) - (Calendar.getInstance().get(Calendar.YEAR) - 10));

            add_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String expense_name = form_expense_name.getText().toString().trim();
                    String expense_comment = form_expense_comment.getText().toString().trim();
                    String expense_charge = form_expense_charge.getText().toString().trim();

                    if (validate_user_input(expense_name,expense_comment, expense_charge)) {
                        Intent add_expense_intent = new Intent();
                        send_data(add_expense_intent, "false");
//                        add_expense_intent.putExtra("Expense_name", expense_name);
//                        add_expense_intent.putExtra("Expense_comment", expense_comment);
//                        add_expense_intent.putExtra("Expense_date_year", getYear_selected());
//                        add_expense_intent.putExtra("Expense_date_month", getMonth_selected());
//                        add_expense_intent.putExtra("Expense_charge", expense_charge);
//                        setResult(RESULT_OK, add_expense_intent);
//                        finish();
                    } else {
                        form_expense_name.setHint("Name can only be 15 character long");
                        form_expense_name.setText("");
                        form_expense_name.setHintTextColor(getColor(R.color.incorrect_input));

                        form_expense_comment.setHint("Comment can only be 20 character long");
                        form_expense_comment.setText("");
                        form_expense_comment.setHintTextColor(getColor(R.color.incorrect_input));

                        form_expense_charge.setHint("Charge cannot be negative");
                        form_expense_charge.setText("");
                        form_expense_charge.setHintTextColor(getColor(R.color.incorrect_input));
                    }
                }
            });

            Button back = findViewById(R.id.button2);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    year_selected = parent.getItemAtPosition(pos).toString();
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    month_selected = parent.getItemAtPosition(pos).toString();
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setUpYearSpinner() {
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year_spinner.setAdapter(yearAdapter);
    }

    private void setUpMonthSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        month_spinner.setAdapter(adapter);
    }

    private boolean validate_user_input(String name, String comment, String charge){
        if (name.length() > 15 || comment.length() > 20){
            return false;
        }
        try {
            Integer.parseInt(charge);

        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    private String numericMonth(String month){
        HashMap<String, String> monthMap = new HashMap<>();
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");

        return monthMap.get(month_spinner.getSelectedItem().toString());
    }
    private void send_data(Intent intent, String isDeleted){
        intent.putExtra("Expense_name", form_expense_name.getText().toString().trim());
        intent.putExtra("Expense_comment", form_expense_comment.getText().toString().trim());
        intent.putExtra("Expense_date_year", year_spinner.getSelectedItem().toString());
        intent.putExtra("Expense_date_month", numericMonth(month_spinner.getSelectedItem().toString()));
        intent.putExtra("Expense_charge", form_expense_charge.getText().toString().trim());
        intent.putExtra("Position", position);

        if (isDeleted == "true"){
            intent.putExtra("isDeleted", isDeleted);
        }

        setResult(RESULT_OK, intent);
        finish();

    }
}