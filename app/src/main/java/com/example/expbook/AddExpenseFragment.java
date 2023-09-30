package com.example.expbook;

import static com.google.android.material.color.MaterialColors.getColor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddExpenseFragment extends DialogFragment {
    private TextView expenseName;
    private TextView expenseCharge;
    private TextView expenseComment;
    private TextView expenseDate;
    private Spinner expenseYear;
    private  Spinner expenseMonth;
    private Spinner month_spinner, year_spinner;
    private OnFragmentInteractionListener listener;
    private Expense editingExpense;
    private TextView totalExpense;

    private String year_selected;
    private String month_selected;
    private boolean isNameValid = true;
    private boolean isCommentValid = true;
    private boolean isChargeValid = true;

    static AddExpenseFragment newInstance(Expense expense){
        Bundle args = new Bundle();
        args.putSerializable("expense", expense);

        AddExpenseFragment fragment = new AddExpenseFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "OnFragmentInteractionListener is not implemented");
        }
    }

    public interface OnFragmentInteractionListener {
        void onOKPressed(Expense expense);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_expense_fragment_layout, null);

        expenseName = view.findViewById(R.id.expense_name_edit_text);
        expenseCharge = view.findViewById(R.id.expense_charge_edit_text);
        expenseComment = view.findViewById(R.id.expense_comment_edit_text);
        expenseYear = view.findViewById(R.id.expense_year_spinner);
        expenseMonth = view.findViewById(R.id.expense_month_spinner);

        year_selected = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        month_selected = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));

        year_spinner = view.findViewById(R.id.expense_year_spinner);
        month_spinner = view.findViewById(R.id.expense_month_spinner);

        setUpYearSpinner();
        setUpMonthSpinner();



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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        try {
            editingExpense = (Expense) getArguments().getSerializable("expense");
        } catch (NullPointerException e) {
            editingExpense = null;
        }

        if (editingExpense != null) {
            expenseName.setText(editingExpense.getName());
            expenseCharge.setText(String.valueOf(editingExpense.getCharge()));
            expenseComment.setText(editingExpense.getComment());
            expenseYear.setSelection(Integer.parseInt(editingExpense.getYear()) - (Calendar.getInstance().get(Calendar.YEAR)));
            expenseMonth.setSelection(Integer.parseInt(editingExpense.getMonth())- 1);
//            expenseDate.setText(editingExpense.getYearMonth());
        }


// source: https://stackoverflow.com/questions/27345584/how-to-prevent-alertdialog-to-close
        builder.setView(view);
        builder.setTitle("Add/Edit Expense");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String name = expenseName.getText().toString();
                String charge = expenseCharge.getText().toString();
                String comment = expenseComment.getText().toString();
                String year = expenseYear.getSelectedItem().toString();
                String month = numericMonth(expenseMonth.getSelectedItem().toString());

                if(validateInputData(name,charge,comment)) {
                    BigDecimal chargeToDecimal = new BigDecimal(charge);
                    listener.onOKPressed(new Expense(name, year, month, chargeToDecimal, comment));
                    dialog.dismiss();
                }
            }
        });
        return dialog;

//        return builder
//                .setView(view)
//                .setTitle("Add/Edit Expense")
//                .setNegativeButton("Cancel", null)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String name = expenseName.getText().toString();
//                        String charge = expenseCharge.getText().toString();
//                        String comment = expenseComment.getText().toString();
//                        String year = expenseYear.getSelectedItem().toString();
//                        String month = numericMonth(expenseMonth.getSelectedItem().toString());
//
//                        if(validateInputData(name,charge,comment)){
//                            BigDecimal chargeToDecimal = new BigDecimal(charge);
//                            listener.onOKPressed(new Expense(name, year, month, chargeToDecimal, comment));
//                        } else {
//                            expenseName.setHint("Name can only be 15 character long");
//                            expenseName.setText("");
//                            expenseName.setBackgroundColor(Color.parseColor("#ffeeee"));
//
//                            expenseCharge.setHint("Comment can only be 20 character long");
//                            expenseComment.setText("");
//                            expenseComment.setBackgroundColor(Color.parseColor("#ffeeee"));
//
//                            expenseCharge.setHint("Charge cannot be negative");
//                            expenseCharge.setText("");
//                            expenseCharge.setBackgroundColor(Color.parseColor("#ffeeee"));
//                            builder.show();
//                            //setFieldsInvalid(expenseName, expenseCharge, expenseComment);
//
//                        }
//
//                    }
//                }).create();

    }



    private boolean validateInputData(String name, String charge, String comment) {
        if (name.length() > 15 || name.length() < 1){
            expenseName.setHint("Name can be at least one or at most 15 characters");
            expenseName.setText("");
            expenseName.setBackgroundColor(Color.parseColor("#ffeeee"));

            isNameValid = false;

        } else {
            isNameValid = true;
        }

        if (comment.length() > 20) {
            expenseComment.setHint("Comment can only be 20 character long");
            expenseComment.setText("");
            expenseComment.setBackgroundColor(Color.parseColor("#ffeeee"));

            isCommentValid = false;
        } else {
            isCommentValid = true;
        }

        try {
            new BigDecimal(charge);
            isChargeValid = true;
        } catch (NumberFormatException e) {

            expenseCharge.setHint("Charge cannot be negative");
            expenseCharge.setText("");
            expenseCharge.setBackgroundColor(Color.parseColor("#ffeeee"));
            isChargeValid = false;

        }

        if (!isNameValid || !isCommentValid || !isChargeValid){
            return false;
        }
        return true;
    }

    private void setUpMonthSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        month_spinner.setAdapter(adapter);
    }

    private void setUpYearSpinner() {
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear - 10; i <= currentYear; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year_spinner.setAdapter(yearAdapter);
    }

    private String numericMonth(String month) {
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
}
