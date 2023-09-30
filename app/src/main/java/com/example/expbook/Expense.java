package com.example.expbook;
import android.annotation.SuppressLint;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Expense implements Serializable {
    private String name;
    private String monthStarted;
    private String year;
    private String month;
    private BigDecimal charge;
    private String comment;

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");

    public Expense(String name, String year, String month, BigDecimal charge, String comment) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.charge = charge;
        this.comment = comment;
    }

    // to be removed
    public Expense(String name, String monthStarted, BigDecimal charge, String comment) {
        this.name = name;
        this.monthStarted =  monthStarted;
        this.charge = charge;
        this.comment = comment;

//        this.split_monthStarted();
    }

    public Expense(String name, String monthStarted, BigDecimal charge) {
        this.name = name;
        this.monthStarted =  monthStarted;
        this.charge = charge;

//        this.split_monthStarted();
    }

    public String getName() {
        return name;
    }

    public String getMonthStarted() {
        return monthStarted;
    }
    public String getMonthStartedSplit(boolean request){
        String[] splitMonthStarted = this.monthStarted.split("-");

        if (request) {
            return splitMonthStarted[0];
        } else {
            return splitMonthStarted[1];
        }
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public String getComment() {
        return comment;
    }

    public String getYear() { return year; }
    public String getMonth() { return month; }


    public void setName(String name) {
        if (!isValidName(name)) throw new IllegalArgumentException("The Name of the Expense Cannot Exceed 15 Characters");
        this.name = name;
    }

    public void setMonthStarted(String monthStarted) {
        this.monthStarted = monthStarted;
//        this.split_monthStarted();
    }

//    private void split_monthStarted() {
//        String[] splitMonthStarted = this.monthStarted.split("-");
//        this.year = splitMonthStarted[0];
//        this.month = splitMonthStarted[1];
//    }
    public void setCharge(BigDecimal charge) {
        //if (!isValidCharge(charge)) throw new IllegalArgumentException("Charge Must be Non-Negative");
        this.charge = charge;
    }

    public void setComment(String comment) {
        if (!isValidComment(comment)) throw new IllegalArgumentException("Not a Valid Comment, the Limit is 20 Characters");
        this.comment = comment;
    }

    public static boolean isValidName(String name){
        // name should be at most 15 characters
        if (name.length() > 15){
            return false;
        }
        return true;
    }

    public String getYearMonth(){
        return this.year + "-" + this.month;
    }

//    public static boolean isValidExpenseDate(Date expenseDate){
//        //verify date object is in correct format
//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
//
//        // catch an error maybe?
//        f.format(monthStarted);
//        return true;
//    }

    public static boolean isValidCharge(int charge){
        // charge cannot be negative
        if (charge < 0){
            return false;
        }

        return true;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public static boolean isValidComment(String comment){
        //comment cannot exceed 20 characters
        if (comment.length() > 20){
            return false;
        }
        return true;
    }


}
