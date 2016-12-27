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
    public void addIncome(int id, String type, String description, int amount) {
        Bill income = new Bill();
        income.setId(id);
        income.setDescription(description);
        income.setAmount(amount);

        realm.beginTransaction();
        realm.copyToRealm(income);
        realm.commitTransaction();

        showLog("Added ; " + description);
        showToast(description + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public List<BillModel> findAllIncome() {
        List<BillModel> dataIncome = new ArrayList<>();

        realmResult = realm.where(Bill.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());

            for (int i = 0; i < realmResult.size(); i++) {
                String description;
                String type;
                int amount;
                int id = realmResult.get(i).getId();
                type= realmResult.get(i).getType();
                description = realmResult.get(i).getDescription();
                amount = realmResult.get(i).getAmount();

                dataIncome.add(new BillModel(id, type, description, amount));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
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
    public void updateIncome(int id, String type, String description, int amount) {
        realm.beginTransaction();
        Bill article = realm.where(Bill.class).equalTo("id", id).findFirst();

        article.setDescription(description);
        article.setAmount(amount);
        realm.commitTransaction();
        showLog("Updated : " + description);
        showToast(description + " berhasil diupdate.");
    }

    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteIncome(int id) {
        RealmResults<Bill> dataResultsDelIncome = realm.where(Bill.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        dataResultsDelIncome.remove(0);
        dataResultsDelIncome.removeLast();
        dataResultsDelIncome.clear();
        realm.commitTransaction();
        showToast("Hapus data berhasil.");
    }
    public int sumIncome(){

        realmResult = realm.where(Bill.class).findAll();
        realmResult.sort("amount", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            sum_income=0;
            for (int i = 0; i < realmResult.size(); i++) {
                int amount;
                amount = realmResult.get(i).getAmount();
                sum_income = sum_income+amount;
                showLog("Sum income : " + sum_income);
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }
        return sum_income;

    }
    public int getNextKey()
    {
        return realm.where(Bill.class).max("id").intValue() + 1;
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
