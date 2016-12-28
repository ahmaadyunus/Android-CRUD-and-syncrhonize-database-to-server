package com.example.ahmaadyunus.task4.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.adapter.BillAdapter;
import com.example.ahmaadyunus.task4.model.BillModel;
import com.example.ahmaadyunus.task4.realm.RealmHelper;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends MainActivity {
    private RecyclerView recyclerViewIncome;
    private RealmHelper realmHelper;
    private TextView income_TV, expense_TV;
    int sum_income, sum_expense;
    private com.github.clans.fab.FloatingActionButton fab_income, fab_expense;
    private FloatingActionMenu fab_menu;
    String type;
    EditText description_input,amount_input;
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
                add_income(type);

            }
        });
        fab_expense = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_expense);
        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "expense";
                add_income(type);

            }
        });
        income_TV = (TextView) findViewById(R.id.income_tv);
        expense_TV = (TextView)findViewById(R.id.expense_tv);
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
        sum_income = realmHelper.sumValue("income");
        sum_expense = realmHelper.sumValue("expense");

        income_TV.setTextColor(Color.GREEN);
        expense_TV.setTextColor(Color.RED);
        income_TV.setText("Rp. "+String.valueOf(sum_income));
        expense_TV.setText("Rp. "+String.valueOf(sum_expense));
    }
    public void add_income(final String type){


        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout1 = inflater.inflate(R.layout.layout_input, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle(R.string.add_income);
        builder.setView(dialoglayout1);
        builder.setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialog1 = (Dialog) dialog;
                int id = realmHelper.getNextKey();
                description_input = (EditText) dialog1.findViewById(R.id.description_input_ET);
                amount_input = (EditText) dialog1.findViewById(R.id.amount_input_ET);
                realmHelper.addBill(id,type,description_input.getText().toString(),Integer.parseInt(amount_input.getText().toString()));
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

}
