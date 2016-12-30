package com.example.ahmaadyunus.task4.realm;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ahmaadyunus.task4.API.BillApi;
import com.example.ahmaadyunus.task4.activity.MainActivity;
import com.example.ahmaadyunus.task4.model.BillModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public class RealmHelper {
    private static final String TAG = "RealmHelper";
    private static int sum_income=0;
    private int id;
    private String status;
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
        String month = date_time.substring(3,5);
        String year = date_time.substring(6,8);
        bill.setId(id);
        bill.setType(type);
        bill.setDescription(description);
        bill.setDate_time(date_time);
        bill.setMonth(month);
        bill.setYear(year);
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
    public List<BillModel> findAllBill() {
        List<BillModel> dataIncome = new ArrayList<>();

        realmResult = realm.where(Bill.class).findAll();
        realmResult.sort("date_time", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());

            for (int i = 0; i < realmResult.size(); i++) {
                String description;
                String type;
                String date_time;
                String month;
                String year;
                int amount;
                int id = realmResult.get(i).getId();
                type= realmResult.get(i).getType();
                description = realmResult.get(i).getDescription();
                date_time = realmResult.get(i).getDate_time();
                month = realmResult.get(i).getMonth();
                year = realmResult.get(i).getYear();
                amount = realmResult.get(i).getAmount();

                dataIncome.add(new BillModel(id, type, description, date_time, month, year, amount));
            }

        } else {
            showLog("Size : 0");
            showToast("No Data");
        }
        return dataIncome;
    }
    public List<BillModel> findAllByMonth(String month) {
        List<BillModel> dataBill = new ArrayList<>();

        realmResult = realm.where(Bill.class).equalTo("month",month).findAll();
        realmResult.sort("date_time", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());

            for (int i = 0; i < realmResult.size(); i++) {
                String description;
                String type;
                String date_time;
                String month1;
                String year;
                int amount;
                int id = realmResult.get(i).getId();
                type= realmResult.get(i).getType();
                description = realmResult.get(i).getDescription();
                date_time = realmResult.get(i).getDate_time();
                month1 = realmResult.get(i).getMonth();
                year = realmResult.get(i).getYear();
                amount = realmResult.get(i).getAmount();

                dataBill.add(new BillModel(id, type, description, date_time, month1, year, amount));
            }

        } else {
            showLog("Size : 0");
            showToast("No Data");
        }
        return dataBill;
    }


    /**
     * method update article
     *
     * @param id
     * @param description
     * @param amount
     */
    public void updateBill(final int id, final String description, final String date_time,final String month, final String year, final int amount) {
        realm.beginTransaction();
//        Bill bill = realm.where(Bill.class).equalTo("id", id).findFirst();
//        bill.setDescription(description);
//        bill.setDate_time(date_time);
//        bill.setMonth(month);
//        bill.setYear(year);
//        bill.setAmount(amount);

        realm.executeTransaction(new Realm.Transaction(){
                                     @Override
                                     public void execute(Realm realm) {
                                         Bill bill = realm.where(Bill.class).equalTo("id", id).findFirst();
                                         bill.setDescription(description);
                                         bill.setDate_time(date_time);
                                         bill.setMonth(month);
                                         bill.setYear(year);
                                         bill.setAmount(amount);

                                     }

                                 },new Realm.Transaction.Callback(){
                                        @Override
                                        public void onSuccess() {
                                            super.onSuccess();
                                            showLog("Updated : " + description);
                                            showToast(description + " updated");

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            super.onError(e);
                                            showLog("Updated : " + description);
                                            showToast(description + " update failure");
                                        }
                                 });
        realm.commitTransaction();
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
    public String synchronize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-80e9a-android23.apiary-mock.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BillApi user_api = retrofit.create(BillApi.class);
        BillModel bill = new BillModel();
        realmResult = realm.where(Bill.class).findAll();
        realmResult.sort("date_time", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());

            for (int i = 0; i < realmResult.size(); i++) {

                bill.setId(realmResult.get(i).getId());
                bill.setDescription(realmResult.get(i).getDescription());
                bill.setType(realmResult.get(i).getType());
                bill.setDate_time(realmResult.get(i).getDate_time());
                bill.setMonth(realmResult.get(i).getMonth());
                bill.setYear(realmResult.get(i).getYear());
                bill.setAmount(realmResult.get(i).getAmount());
                bill.getStatus();
                Call<BillModel> userSave = user_api.postBill(bill);
                userSave.enqueue(new Callback<BillModel>() {
                    @Override
                    public void onResponse(Call<BillModel> call, Response<BillModel> response) {
                        // Toast.makeText(MainActivity.this,""+response.body().getStatus(), LENGTH_SHORT).show();
                        status = String.valueOf(response.body().getStatus());
                    }

                    @Override
                    public void onFailure(Call<BillModel> call, Throwable t) {
                        Log.d("onFailure", t.toString());
                        status = t.toString();
                    }
                });
            }


        }
        return status;
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
