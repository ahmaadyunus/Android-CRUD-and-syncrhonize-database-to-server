package com.example.ahmaadyunus.task4.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.ahmaadyunus.task4.adapter.RecyclerViewClickListener;
import com.example.ahmaadyunus.task4.adapter.RecyclerViewTouchListener;
import com.example.ahmaadyunus.task4.model.BillModel;
import com.example.ahmaadyunus.task4.realm.RealmHelper;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends MainActivity {
    private RecyclerView recyclerViewDashboard;
    private RealmHelper realmHelper;
    private TextView income_TV, expense_TV, balance_TV;
    private int sum_income, sum_expense;
    private com.github.clans.fab.FloatingActionButton fab_income, fab_expense;
    private FloatingActionMenu fab_menu;
    private String type;
    private String description_update,date_update,time_update;
    private int amount_update;
    private SimpleDateFormat format;
    private Spinner spinner_month;
    ButtonBarLayout toggle;
    Calendar myCalendar;
    java.sql.Time timeValue;
    EditText description_input,amount_input,date_input,time_input;
    //private List<BillModel> dataBill= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_dashboard,null);
        drawerLayout.addView(contentView,1);
        fab_menu = (FloatingActionMenu) findViewById(R.id.menu);
        toggle = (ButtonBarLayout) findViewById(R.id.toggle_btn_dashboard);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
        recyclerViewDashboard = (RecyclerView) findViewById(R.id.rv_dashboard);
        spinner_month = (Spinner) findViewById(R.id.month_spinner);
        spinnerMonth();
        final String itemSpinnerMonth =spinner_month.getSelectedItem().toString();
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               setRecyclerView(spinner_month.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recyclerViewDashboard.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewDashboard.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerViewDashboard, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), studentList.get(position).getId() + " is clicked!", Toast.LENGTH_SHORT).show();
                description_update=dataBill.get(position).getDescription();
                date_update = dataBill.get(position).getDate_time();
                time_update = dataBill.get(position).getDate_time();
                amount_update = dataBill.get(position).getAmount();

                update_Bill(dataBill.get(position).getId(),description_update,date_update,time_update,amount_update);


            }

            @Override
            public void onLongClick(View view, final int position) {
              AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.confirm_delete);
                builder.setPositiveButton(R.string.delete_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realmHelper.deleteBill(dataBill.get(position).getId());
                        setValue();
                        setRecyclerView("-");
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();


            }
        }));
        setRecyclerView(itemSpinnerMonth);
        setValue();
    }
    public void setRecyclerView(String month) {
        dataBill.clear();
        String monthFilter;
        if(month.equals("January")){
            monthFilter="1";
        }else if(month.equals("February")){
            monthFilter="2";
        }else if(month.equals("March")){
            monthFilter="3";
        }else if(month.equals("April")){
            monthFilter="4";
        }else if(month.equals("May")){
            monthFilter="5";
        }else if(month.equals("June")){
            monthFilter="6";
        }else if(month.equals("July")){
            monthFilter="7";
        }else if(month.equals("August")){
            monthFilter="8";
        }else if(month.equals("September")){
            monthFilter="9";
        }else if(month.equals("October")){
            monthFilter="10";
        }else if(month.equals("November")){
            monthFilter="11";
        }else if(month.equals("December")){
            monthFilter="12";
        }else {
            monthFilter="-";
        }
        if(monthFilter.equals("-")) {
            try {
                dataBill = realmHelper.findAllBill();
            } catch (Exception e) {
                e.printStackTrace();
            }
            BillAdapter adapterIncome = new BillAdapter(dataBill);
            recyclerViewDashboard.setAdapter(adapterIncome);
        }else{
            try {
                dataBill = realmHelper.findAllByMonth(monthFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BillAdapter adapterIncome = new BillAdapter(dataBill);
            recyclerViewDashboard.setAdapter(adapterIncome);

        }
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
                setRecyclerView("-");
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
    public void update_Bill(final int id, final String description, String date, String time, int amount){
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_input, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        description_input = (EditText) dialoglayout.findViewById(R.id.description_input_ET);
        date_input = (EditText) dialoglayout.findViewById(R.id.date_input);
        time_input = (EditText) dialoglayout.findViewById(R.id.time_input);
        amount_input = (EditText) dialoglayout.findViewById(R.id.amount_input_ET);

        description_input.setText(description);
        String newdate = date.substring(0, 10);
        String newtime = time.substring(11, 19);
        date_input.setText(newdate);
        time_input.setText(newtime);
        amount_input.setText(String.valueOf(amount));
        datePicker();
        timePicker();
        builder.setTitle(R.string.update);
        builder.setView(dialoglayout);

        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialog2 = (Dialog) dialog;

                description_input = (EditText) dialog2.findViewById(R.id.description_input_ET);
                date_input = (EditText) dialog2.findViewById(R.id.date_input);
                time_input = (EditText) dialog2.findViewById(R.id.time_input);
                amount_input = (EditText) dialog2.findViewById(R.id.amount_input_ET);
                String date_time = date_input.getText().toString();
                String month = date_time.substring(3,5);
                String year = date_time.substring(6,8);
                realmHelper.updateBill(id
                        ,description_input.getText().toString()
                        ,date_input.getText().toString()+" "+time_input.getText().toString()
                        ,month
                        ,year
                        ,Integer.parseInt(amount_input.getText().toString()));
                setValue();
                setRecyclerView("-");
            }
        });
        builder.show();
    }
    public void spinnerMonth() {
        String[] month_array = new String[]{"-","January","February","March","April","May","June","July","August","September","October","November","December"};
        List<String> list = Arrays.asList(month_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(dataAdapter);
    }

}
