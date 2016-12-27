package com.example.ahmaadyunus.task4.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.model.BillModel;

import java.util.List;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.IncomeViewHolder> {
    private List<BillModel> incomeList;
    public BillAdapter(List<BillModel>incomeList) {
        this.incomeList =incomeList;
    }
    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content, parent, false);

        return new IncomeViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(IncomeViewHolder holder, int position) {
        holder.description.setText(incomeList.get(position).getDescription());
        holder.amount.setText("Rp. " + String.valueOf(incomeList.get(position).getAmount()));
        holder.amount.setTextColor(Color.GREEN);
    }
    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView amount;

        public IncomeViewHolder(View view) {
            super(view);
            description =(TextView)view.findViewById(R.id.description_tv);
            amount = (TextView) view.findViewById(R.id.amount_tv);
        }
    }
}
