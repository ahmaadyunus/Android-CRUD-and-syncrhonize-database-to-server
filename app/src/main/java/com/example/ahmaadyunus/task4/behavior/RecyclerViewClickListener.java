package com.example.ahmaadyunus.task4.behavior;

import android.view.View;

/**
 * Created by ahmaadyunus on 27/12/16.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
