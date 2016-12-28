package com.example.ahmaadyunus.task4.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.adapter.BillAdapter;
import com.example.ahmaadyunus.task4.model.BillModel;
import com.example.ahmaadyunus.task4.realm.RealmHelper;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends MainActivity {
    private RecyclerView recyclerViewIncome;
    private RealmHelper realmHelper;
    private TextView income_TV, expense_TV, balance_TV;
    int sum_income, sum_expense;
    private com.github.clans.fab.FloatingActionButton fab_income, fab_expense;
    private FloatingActionMenu fab_menu;
    String type;
    private SimpleDateFormat format;
    Calendar myCalendar;
    java.sql.Time timeValue;
    EditText description_input,amount_input,date_input,time_input;
    private List<BillModel> dataIncome= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_dashboard,null);
        drawerLayout.addView(contentView,1);
        fab_menu = (FloatingActionMenu) findViewById(R.id.menu);
        fab_income = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_income);
        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "income";
                add_bill(type);

            }
        });
        fab_expense = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_expense);
        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "expense";
                add_bill(type);

            }
        });
        income_TV = (TextView) findViewById(R.id.income_tv);
        expense_TV = (TextView)findViewById(R.id.expense_tv);
        balance_TV = (TextView) findViewById(R.id.balance_TV);
        realmHelper = new RealmHelper(DashboardActivity.this);
        recyclerViewIncome = (RecyclerView) findViewById(R.id.rv_dashboard);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();
        setValue();
    }
    public void setRecyclerView() {
        dataIncome.clear();
        try {
            dataIncome = realmHelper.findAllIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BillAdapter adapterIncome = new BillAdapter(dataIncome);
        recyclerViewIncome.setAdapter(adapterIncome);
    }
    public void setValue(){
        final DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        sum_income = realmHelper.sumValue("income");
        sum_expense = realmHelper.sumValue("expense");
        int balance = sum_income-sum_expense;
        income_TV.setTextColor(Color.GREEN);
        expense_TV.setTextColor(Color.RED);
        income_TV.setText("Rp. "+String.valueOf(df.format(sum_income))+",-");
        expense_TV.setText("Rp. "+String.valueOf(df.format(sum_expense))+",-");
        balance_TV.setText("Balance : Rp. "+String.valueOf(df.format(balance))+",-");

    }
    public void add_bill(final String type){


        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout1 = inflater.inflate(R.layout.layout_input, null);
        date_input = (EditText) dialoglayout1.findViewById(R.id.date_input);
        time_input = (EditText) dialoglayout1.findViewById(R.id.time_input);
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        if(type.equals("income")) {
            builder.setTitle(R.string.add_income);
        }else{
            builder.setTitle(R.string.add_expense);
        }
        builder.setView(dialoglayout1);
        datePicker();
        timePicker();
        builder.setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialog1 = (Dialog) dialog;
                int id = realmHelper.getNextKey();
                String date_time = (date_input.getText().toString()+" "+time_input.getText().toString());
                description_input = (EditText) dialog1.findViewById(R.id.description_input_ET);
                amount_input = (EditText) dialog1.findViewById(R.id.amount_input_ET);
                realmHelper.addBill(id,type,description_input.getText().toString(),date_time, Integer.parseInt(amount_input.getText().toString()));
                setRecyclerView();
                setValue();
                fab_menu.close(true);
            }
        });
        builder.setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
    private void timePicker(){
        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DashboardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            String dtStart = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
                            format = new SimpleDateFormat("HH:mm");
                            timeValue = new java.sql.Time(format.parse(dtStart).getTime());
                            time_input.setText(String.valueOf(timeValue));
                        } catch (Exception ex) {
                            time_input.setText(ex.getMessage().toString());
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
    private  void datePicker(){
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        date_input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DashboardActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_input.setText(sdf.format(myCalendar.getTime()));
    }

}
