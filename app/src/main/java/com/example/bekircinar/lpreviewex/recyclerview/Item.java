package com.example.bekircinar.lpreviewex.recyclerview;

/**
 * Created by bekir.cinar on 06/07/14.
 */
public class Item {

    public Item(String textName) {
        this.textName = textName;
    }

    private String textName;

    public void setText(String textName) {
        this.textName = textName;
    }

    public String getText() {
        return textName;
    }
}
