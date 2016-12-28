package com.example.ahmaadyunus.task4.adapter;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmaadyunus.task4.R;
import com.example.ahmaadyunus.task4.model.BillModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.IncomeViewHolder> {
    private List<BillModel> billList;
    public BillAdapter(List<BillModel>billList) {
        this.billList =billList;
    }
    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content, parent, false);

        return new IncomeViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(IncomeViewHolder holder, int position) {
        final DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        if(String.valueOf(billList.get(position).getType()).equals("income")) {
            holder.amount.setTextColor(Color.GREEN);
            holder.icon_type.setImageResource(R.drawable.ic_income);
        }else{
            holder.amount.setTextColor(Color.RED);
            holder.icon_type.setImageResource(R.drawable.ic_expense);
        }
        holder.description.setText(billList.get(position).getDescription());
        holder.amount.setText("Rp. " + String.valueOf(df.format(billList.get(position).getAmount())));

    }
    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon_type;
        public TextView description;
        public TextView amount;

        public IncomeViewHolder(View view) {
            super(view);
            icon_type = (ImageView)view.findViewById(R.id.img_type_icon);
            description =(TextView)view.findViewById(R.id.description_tv);
            amount = (TextView) view.findViewById(R.id.amount_tv);
        }
    }
}
