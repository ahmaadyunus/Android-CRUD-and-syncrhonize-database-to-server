package com.example.ahmaadyunus.task4.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.adapter.BillAdapter;
import com.example.ahmaadyunus.task4.model.BillModel;
import com.example.ahmaadyunus.task4.realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends MainActivity {
    private RecyclerView recyclerViewIncome;
    private RealmHelper realmHelper;
    private TextView income_TV, expense_TV;
    int sum_income, sum_expense;
    private List<BillModel> dataIncome= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_dashboard,null);
        drawerLayout.addView(contentView,1);
        income_TV = (TextView)findViewById(R.id.income_tv);
        expense_TV = (TextView)findViewById(R.id.expense_tv);
        realmHelper = new RealmHelper(DashboardActivity.this);
        recyclerViewIncome = (RecyclerView) findViewById(R.id.rv_dashboard);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();
        setValue();
    }
    public void setRecyclerView() {
        try {
            dataIncome = realmHelper.findAllIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BillAdapter adapterIncome = new BillAdapter(dataIncome);
        recyclerViewIncome.setAdapter(adapterIncome);
    }
    public void setValue(){
        sum_income = realmHelper.sumIncome();
        income_TV.setText("Rp. "+String.valueOf(sum_income));
    }

}
