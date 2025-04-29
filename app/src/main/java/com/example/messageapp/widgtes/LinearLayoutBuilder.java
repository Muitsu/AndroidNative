package com.example.messageapp.widgtes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.function.BiConsumer;

public class LinearLayoutBuilder {
    private final LinearLayout container;

    public LinearLayoutBuilder(int containerId, View rootView) {
        this.container = rootView.findViewById(containerId);
        if (this.container == null) {
            throw new RuntimeException("Error: container is null. Check if the provided containerId exists in the layout.");
        }
    }

    public void build(int itemCount, BiConsumer<Integer, ViewGroup> builder) {
        container.removeAllViews(); // Clear existing views
        for (int i = 0; i < itemCount; i++) {
            builder.accept(i, container);
        }
    }


    public ViewGroup getViewParent() {
        return container;
    }

    public void addItem(View item) {
        container.addView(item);
    }

    public void removeItem(View item) {
        container.removeView(item);
    }

    public int getItemIndex(View item) {
        return container.indexOfChild(item);
    }

    public void addAll(int itemCount, BiConsumer<Integer, ViewGroup> builder) {
        for (int i = 0; i < itemCount; i++) {
            builder.accept(i, container);
        }
    }


    public void removeAt(int index) {
        if (index >= 0 && index < container.getChildCount()) {
            container.removeViewAt(index);
        } else {
            throw new IndexOutOfBoundsException("Error: Index " + index + " is out of bounds.");
        }
    }

    public void clear() {
        container.removeAllViews();
    }
}

// ****************** HOW TO USE ******************
/*
 LinearLayoutBuilder listBuilder;
 listBuilder = new LinearLayoutBuilder(R.id.linear_layout_example, findViewById(android.R.id.content));

 List<T> items =  .....
 listBuilder.addAll(items.size(), (index, parent) -> {

     T item = items.get(index);

     View listItem = LayoutInflater.from(this).inflate(R.layout.list_tile_street, parent, false);
     TextView itemTitle = listItem.findViewById(R.id.item_title);
     itemTitle.setText(item.name);
     listItem.setOnClickListener(v -> {
         Navigator.pop(this);
     });

     parent.addView(listItem); //MUST ADD THIS
 });

 */