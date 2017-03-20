package com.example.login.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by kunal on 19-Mar-17.
 */

public class InputValidation {

    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }


    /**
     * method to check InputEditText filled, means we have to put validation on textbox .
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;

        }else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * method to check InputEditText has valid email. Email Validation checking like in javascript
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * method to check InputEditText has matching passwords.
     */
    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message){

        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText1.getText().toString().trim();

        if (!value1.contentEquals(value2)){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        }else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * method to Hide keyboard
     */
    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
