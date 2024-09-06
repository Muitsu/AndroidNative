package com.example.messageapp.widgtes;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.messageapp.R;

import java.util.Calendar;

public class CustomDatetimeField extends LinearLayout {

    private TextView fieldLabel;
    private EditText inputEditText;
    private ImageView fieldIcon;

    public CustomDatetimeField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //Connect Xml layout into Java
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_datetime_field, this);
        // Initialize UI elements
        fieldLabel = findViewById(R.id.dt_label);
        inputEditText = findViewById(R.id.dt_field);
        fieldIcon = findViewById(R.id.dt_icon);

        //Initialize text from initial values from user declare on XML attribute
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomDatetimeField, 0, 0);
        try {
            String labelText = attributes.getString(R.styleable.CustomDatetimeField_dtLabel);
            String hint = attributes.getString(R.styleable.CustomDatetimeField_dtHint);
            Drawable icon = attributes.getDrawable(R.styleable.CustomDatetimeField_dtIcon);

            fieldLabel.setText(labelText);
            inputEditText.setHint(hint);
            inputEditText.setLongClickable(false);

            if (icon != null) {
                fieldIcon.setImageDrawable(icon);
                fieldIcon.setVisibility(View.VISIBLE);
            }
        } finally {
            attributes.recycle();
        }
        // Disable manual typing and handle clicks to show DateTime picker
        inputEditText.setOnClickListener(v -> showDatePickerDialog(context));
    }

    private void showDatePickerDialog(Context context) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it in the EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    inputEditText.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }


    public void setLabel(String label) {
        fieldLabel.setText(label);
    }

    public void setIcon(int resourceId) {
        fieldIcon.setImageResource(resourceId);
    }

    public String getSelectedDate() {
        return inputEditText.getText().toString();
    }
}
