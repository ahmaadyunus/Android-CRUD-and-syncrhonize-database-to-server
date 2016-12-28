package com.example.ahmaadyunus.task4.adapter;

import android.view.View;

/**
 * Created by ahmaadyunus on 28/12/16.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
