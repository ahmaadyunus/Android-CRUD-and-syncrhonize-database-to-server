package com.example.ahmaadyunus.task4.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.realm.RealmHelper;

public class TransactionActivity extends MainActivity {
    EditText description_income_ET, amount_income_ET;
    Button submit_income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_transaction,null);
        drawerLayout.addView(contentView,1);
        description_income_ET = (EditText) contentView.findViewById(R.id.description_income_ET);
        amount_income_ET = (EditText)contentView.findViewById(R.id.amount_income_ET);
        submit_income = (Button) contentView.findViewById(R.id.submit_income);
        submit_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= realmHelper.getNextKey();
                String type="income";
                String description =  description_income_ET.getText().toString();
                int amount =  Integer.parseInt(amount_income_ET.getText().toString());
                    //realmHelper.addBill(id, type, description, amount);

            }
        });

    }
}
