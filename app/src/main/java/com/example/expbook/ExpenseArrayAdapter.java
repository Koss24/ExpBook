package com.example.expbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseArrayAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private ArrayList<Expense> expenses;

    public ExpenseArrayAdapter(Context context, int resource, ArrayList<Expense> objects) {
        super(context, resource, objects);

        this.context = context;
        this.expenses = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = expenses.get(position);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.expense_list_layout, null);

        TextView name = view.findViewById(R.id.expense_name);
        TextView comment = view.findViewById(R.id.expense_comment);
        TextView price = view.findViewById(R.id.price);
        TextView date = view.findViewById(R.id.expense_date);


        comment.setText(String.valueOf(expense.getComment()));
        price.setText("$" + String.valueOf(expense.getCharge()));
        name.setText(String.valueOf(expense.getName()));
        date.setText(String.valueOf(expense.getMonthStarted()));

        return view;
    }
}

