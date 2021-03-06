package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText saveEditText;
    String lvTxt;
    int lvPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        lvTxt = getIntent().getStringExtra("lvtxt");
        // lvPosition = getIntent().getIntExtra("lvposition");  // this generates an error

        saveEditText = (EditText) findViewById(R.id.update_editText);
        saveEditText.setText(lvTxt);

    }

    public void onSaveEditItem(View view) {
        // grab our new text and pass it back to previous activity
        Intent data = new Intent();
        data.putExtra("newText", saveEditText.getText().toString());
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onDeleteBtn(View view) {
        Intent data = new Intent();
        data.putExtra("code", 201);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onCancelBtn(View view) {
        Intent data = new Intent();
        data.putExtra("code", 202);
        setResult(RESULT_OK, data);
        finish();
    }
}
