package com.example.messageapp.widgtes;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.messageapp.R;

public class CustomDropdown<T> extends LinearLayout {
    private TextView labelTextView;
    private Spinner customDropdown;
    private ImageView iconImageView;


    public CustomDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /*
   --------------      -----------   ->   ----------
  |   Attribute  | -> |   XML IDs  |      |  JAVA  |
   --------------      -----------   <-   ----------
  */
    private void init(Context context, AttributeSet attrs) {
        // Inflate the custom view layout
        LayoutInflater.from(context).inflate(R.layout.custom_dropdown, this, true);

        // Get references to the views
        labelTextView = findViewById(R.id.labelTextView);
        customDropdown = findViewById(R.id.customSpinner);
        iconImageView = findViewById(R.id.iconImageView);

        // Obtain custom attributes from XML
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomDropdown);
        String labelText = attributes.getString(R.styleable.CustomDropdown_dropdownLabelText);
        String hint = attributes.getString(R.styleable.CustomDropdown_dropdownHint);
        int iconResId = attributes.getResourceId(R.styleable.CustomDropdown_dropdownIcon, 0);

        // Set label text
        if (labelText != null) {
            labelTextView.setText(labelText);
        }
        // Set label hint
        if (hint != null) {
            labelTextView.setHint(labelText);
        }

        // Set icon if provided
        if (iconResId != 0) {
            iconImageView.setImageResource(iconResId);
            iconImageView.setVisibility(VISIBLE);
        }

        attributes.recycle();
    }

    // Method to set the data for the spinner using a generic array
    public void setData(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customDropdown.setAdapter(adapter);
    }

    public void setInitHint(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customDropdown.setAdapter(adapter);
    }

    // You can add additional methods to manipulate the Spinner
    public Spinner getSpinner() {
        return customDropdown;
    }

    @FunctionalInterface
    public interface OnItemSelected {
        void onItemSelected(int position);
    }

    public void onChange(OnItemSelected listener) {
        customDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional, can be left empty if not needed
            }
        });
    }

    public T getItem(T[] items, String value) {
        // Set the default selection to the defaultData item
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(value)) {
                return items[i];
            }
        }
        return null;
    }
}
