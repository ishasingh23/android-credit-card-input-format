package com.example.androidcreditcardinputmorrassignment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private TextInputLayout securityCode;
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputEditText cardNumber;
    private TextInputEditText dateEdit;
    private TextInputEditText securityCodeEdit;
    private TextInputEditText firstNameEdit;
    private TextInputEditText lastNameEdit;
    private TextInputLayout card;
    private TextInputLayout date;
    private String cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card = findViewById(R.id.cardNumber);
        date = findViewById(R.id.mm_yy);
        securityCode = findViewById(R.id.security_code);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        cardNumber = findViewById(R.id.card_number_edit);
        dateEdit = findViewById(R.id.mm_yy_edit);
        securityCodeEdit = findViewById(R.id.security_code_edit);
        firstNameEdit = findViewById(R.id.first_name_edit);
        lastNameEdit = findViewById(R.id.last_name_edit);
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setErrorFalse();
                clear_focus();
                String cvv = securityCodeEdit.getText().toString();
                String card_num = cardNumber.getText().toString();
                String last_name = lastNameEdit.getText().toString();
                String first_name = firstNameEdit.getText().toString();
                String dt = dateEdit.getText().toString();
                if (checkCvvSecurityCode(cvv, cardName) && checkNameField(first_name, last_name) && dateCheck(dt)) {
                    display_alert();
                }

            }
        });
    }


    //clear error messages and  before displaying alert

    public void setErrorFalse() {
        card.setErrorEnabled(false);
        date.setErrorEnabled(false);
        securityCode.setErrorEnabled(false);
        firstName.setErrorEnabled(false);
        lastName.setErrorEnabled(false);
    }


    //check if cvv field is valid
    public boolean checkCvvSecurityCode(String cvv, String card_name) {
        int length = cvv.length();
        if(length<3){
            securityCode.setError("enter valid CVV");

        }

        if (!field_empty(cvv)) {
            securityCode.setError("Enter security code");
            return false;
        } else {
            if (card_name != null) {
                if (card_name.equals("Master") || card_name.equals("Visa") || card_name.equals("Discover") || card_name.equals("American")) {
                    if (length == 3) {
                        cardNumber.setError(null);
                        return true;

                    } else {
                        securityCode.setError("Invalid length");
                        return false;
                    }


                }
            }
        }
        return true;
    }

    //check if name field is empty
    public boolean field_empty(String name) {
        int length = name.length();
        boolean result = true;

        if (length > 0) {
            result = true;
        } else if (length <= 0) {
            result = false;
        }
        return result;
    }

    //testing name field check
    public boolean name_matches(String name) {
        int len = name.length();
        boolean result = true;
        if (!name.matches("[a-zA-Z ]+")) {
            result = false;
        }
        return result;
    }

    //check for the name field if it is not empty
    public boolean checkNameField(String first, String last) {
        if (!field_empty(first)) {
            firstName.setError("enter first name");
            return false;
        }
        if (!field_empty(last)) {
            lastName.setError("enter last name");
            return false;
        }
        if (!name_matches(first)) {
            firstName.setError("Invalid name");
            return false;
        }
        if (!name_matches(last)) {
            lastName.setError("Invalid name");
            return false;
        }
        return true;
    }

    public boolean date_matches(String date_text) {
        boolean result = true;
        if (!date_text.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
            result = false;
        }
        return result;
    }

    //check for the valid date length.
    public boolean date_field_length(String date_text) {
        int length = date_text.length();
        boolean result = true;
        if (length != 5) {
            result = false;
        }
        return result;
    }

    //check the date field for valid format and if date field is not empty.
    public boolean dateCheck(String dateText) {
        if (!field_empty(dateText)) {
            date.setError("Enter expiry date");
            return false;
        }
        if (!date_field_length(dateText)) {
            date.setError("Invalid Length");
            return false;
        }
        if (!date_matches(dateText)) {
            date.setError("Incorrect format");
            return false;
        }
        if (!checkExpiration(dateText)) {
            date.setError("Expired");
            return false;
        }
        return true;
    }

    //check expiration date of the card
    public boolean checkExpiration(String date_text) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int y2 = Integer.parseInt(String.valueOf(year).substring(2, 4));
        int d1 = Integer.parseInt(date_text.substring(0, 2));
        int y1 = Integer.parseInt(date_text.substring(3, 5));

        if (y1 > 0 && y1 < 20) {
            return false;
        }
        if (y1 < 100 && y1 > 50) {
            return false;
        }
        if (y1 == y2 && d1 < month) {
            return false;
        }
        return true;
    }

    //clear focus from all the fields before displaying the alert
    public void clear_focus() {
        lastName.clearFocus();
        cardNumber.clearFocus();
        dateEdit.clearFocus();
        securityCodeEdit.clearFocus();
        firstNameEdit.clearFocus();
        lastNameEdit.clearFocus();
        submitButton.clearFocus();
    }

    //alert display after successful completion of payment
    public void display_alert() {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Payment Successful");
        dialog.setPositiveButton("OK",
                (dialog1, which) -> Toast.makeText(getApplicationContext(), "Isha singh's Morr assignment done", Toast.LENGTH_LONG).show());
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}