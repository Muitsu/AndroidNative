package com.example.messageapp.widgtes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.messageapp.R;

public class CustomEditText extends LinearLayout {

    private TextView labelTextView;
    private EditText inputEditText;
    private ImageView iconImageView;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    /*
      --------------      -----------   ->   ----------
     |   Attribute  | -> |   XML IDs  |      |  JAVA  |
      --------------      -----------   <-   ----------
     */
    private void initializeViews(Context context, AttributeSet attrs) {
        //Connect Xml layout into Java
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_edit_text, this);

        labelTextView = findViewById(R.id.labelTextView);
        inputEditText = findViewById(R.id.inputEditText);
        iconImageView = findViewById(R.id.iconImageView);

        //Initialize text from initial values from user declare on XML attribute
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0);
        try {
            String labelText = attributes.getString(R.styleable.CustomEditText_labelText);
            String hint = attributes.getString(R.styleable.CustomEditText_hint);
            Drawable icon = attributes.getDrawable(R.styleable.CustomEditText_icon);
            String inputTp = attributes.getString(R.styleable.CustomEditText_etInputType);
            String option = attributes.getString(R.styleable.CustomEditText_etImeOption);
            boolean isSingle = attributes.getBoolean(R.styleable.CustomEditText_etSingleLine, true);

            labelTextView.setText(labelText);
            inputEditText.setHint(hint);
            inputEditText.setInputType(getInputType((inputTp != null) ? inputTp : ""));
            inputEditText.setImeOptions(getImeOptions((option != null) ? option : ""));
            inputEditText.setSingleLine(isSingle);

            if (icon != null) {
                iconImageView.setImageDrawable(icon);
                iconImageView.setVisibility(View.VISIBLE);
            }
        } finally {
            attributes.recycle();
        }
    }

    public String getText() {
        return inputEditText.getText().toString();
    }

    public void setText(String text) {
        inputEditText.setText(text);
    }

    public void setLabelText(String labelText) {
        labelTextView.setText(labelText);
    }

    public void setHint(String hint) {
        inputEditText.setHint(hint);
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            iconImageView.setImageDrawable(icon);
            iconImageView.setVisibility(View.VISIBLE);
        } else {
            iconImageView.setVisibility(View.GONE);
        }
    }

    private int getInputType(String inputTypeString) {
        switch (inputTypeString) {
            case "text":
                return InputType.TYPE_CLASS_TEXT;
            case "number":
                return InputType.TYPE_CLASS_NUMBER;
            case "phone":
                return InputType.TYPE_CLASS_PHONE;
            case "email":
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case "password":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            default:
                return InputType.TYPE_CLASS_TEXT; // Default to text if unknown
        }
    }


    private int getImeOptions(String imeOptionsString) {
        switch (imeOptionsString) {
            case "actionDone":
                return EditorInfo.IME_ACTION_DONE;
            case "actionNext":
                return EditorInfo.IME_ACTION_NEXT;
            case "actionSearch":
                return EditorInfo.IME_ACTION_SEARCH;
            case "actionSend":
                return EditorInfo.IME_ACTION_SEND;
            case "actionGo":
                return EditorInfo.IME_ACTION_GO;
            case "actionNone":
                return EditorInfo.IME_ACTION_NONE;
            default:
                return EditorInfo.IME_ACTION_DONE; // Default to none if unknown
        }
    }

}