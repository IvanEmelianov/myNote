package com.ivan.mynote;

import com.ivan.mynote.entity.Record;

public interface CustomerClickListener {
    void onCustomerClick(String title, String text, String date, int id);
}
