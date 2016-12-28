package com.example.ahmaadyunus.task4.realm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ahmaadyunus.task4.model.Bill;
import com.example.ahmaadyunus.task4.model.BillModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class RealmHelper {
    private static final String TAG = "RealmHelper";
    private static int sum_income=0;
    private int id;
    private Realm realm;
    private RealmResults<Bill> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */

    public RealmHelper(Context context) {
        realm = Realm.getInstance(context);
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param id
     * @param description
     * @param amount
     */
    public void addBill(int id, String type, String description, String date_time,int amount) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setType(type);
        bill.setDescription(description);
        bill.setDate_time(date_time);
        bill.setAmount(amount);

        realm.beginTransaction();
        realm.copyToRealm(bill);
        realm.commitTransaction();

        showLog("Added ; " + description);
        showToast(description + " Saved");
    }


    /**
     * method mencari semua article
     */
    public List<BillModel> findAllIncome() {
        List<BillModel> dataIncome = new ArrayList<>();

        realmResult = realm.where(Bill.class).findAll();
        realmResult.sort("date_time", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());

            for (int i = 0; i < realmResult.size(); i++) {
                String description;
                String type;
                String date_time;
                int amount;
                int id = realmResult.get(i).getId();
                type= realmResult.get(i).getType();
                description = realmResult.get(i).getDescription();
                date_time = realmResult.get(i).getDate_time();
                amount = realmResult.get(i).getAmount();

                dataIncome.add(new BillModel(id, type, description, date_time, amount));
            }

        } else {
            showLog("Size : 0");
            showToast("No Data");
        }
        return dataIncome;
    }


    /**
     * method update article
     *
     * @param id
     * @param description
     * @param amount
     */
    public void updateBill(int id, String description, String date_time, int amount) {
        realm.beginTransaction();
        Bill bill = realm.where(Bill.class).equalTo("id", id).findFirst();
        bill.setDescription(description);
        bill.setDate_time(date_time);
        bill.setAmount(amount);
        realm.commitTransaction();
        showLog("Updated : " + description);
        showToast(description + " updated");
    }

    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteBill(int id) {
        RealmResults<Bill> dataResultsDelIncome = realm.where(Bill.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        dataResultsDelIncome.remove(0);
        dataResultsDelIncome.removeLast();
        dataResultsDelIncome.clear();
        realm.commitTransaction();
        showToast("Delete Succesfully");
    }
    public int sumValue(String type){
        realmResult = realm.where(Bill.class).equalTo("type",type).findAll();
        realmResult.sort("amount", Sort.DESCENDING);
        try {
            showLog("Size : " + realmResult.size());
            sum_income=0;
            for (int i = 0; i < realmResult.size(); i++) {
                int amount;
                amount = realmResult.get(i).getAmount();
                sum_income = sum_income+amount;
                showLog("Sum income : " + sum_income);
            }

        } catch (Exception e){

        }
        return sum_income;

    }
    public int getNextKey() {
        id=1;
        realmResult = realm.where(Bill.class).findAll();
        if(realmResult.size()>0){
            id = realm.where(Bill.class).max("id").intValue() + 1;
        }
            return id;

    }

    /**
     * membuat log
     * @param s
     */
    private void showLog(String s) {
        Log.d(TAG, s);
    }

    /**
     * Membuat Toast Informasi
     */
    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
