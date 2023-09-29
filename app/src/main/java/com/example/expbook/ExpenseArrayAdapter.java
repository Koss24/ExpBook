package com.example.expbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExpenseArrayAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private ArrayList<Expense> expenses;

    public ExpenseArrayAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, 0, expenses);

        this.context = context;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull  ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.expense_list_layout, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView name = view.findViewById(R.id.expense_name);
        TextView comment = view.findViewById(R.id.expense_comment);
        TextView price = view.findViewById(R.id.expense_charge);
        TextView date = view.findViewById(R.id.expense_date);


        comment.setText(String.valueOf(expense.getComment()));
        price.setText("$" + String.valueOf(expense.getCharge()));
        name.setText(String.valueOf(expense.getName()));

        String year = expense.getYear();
        String month = expense.getMonth();
        String dateStr = year + "-" + month;

        date.setText(dateStr);

        return view;
    }
}

