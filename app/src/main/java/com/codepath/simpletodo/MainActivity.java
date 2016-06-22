package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    int lvPosition;
    String txt;
    String updateTxt;

    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove (position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvPosition = position;
                txt = aToDoAdapter.getItem(lvPosition);
 //               etEditText.setText(txt);  // Remove to call new edit activity instead
                launchActivityEditItem(lvPosition, txt);
            }
        });

    }

    public void launchActivityEditItem(int position, String txt) {
        Intent i = new Intent(this, EditItemActivity.class);

        // list view position and list view text to new activity
        i.putExtra("lvposition", position);
        i.putExtra("lvtxt", txt);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void populateArrayItems() {
//        todoItems = new ArrayList<String> ();
//        todoItems.add("Item 1");
//        todoItems.add("Item 2");
//        todoItems.add("Item 3");
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            todoItems = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();

    }

    public void onEditItem(View view) {
        txt = aToDoAdapter.getItem(lvPosition);
        aToDoAdapter.remove(txt);
        aToDoAdapter.insert(etEditText.getText().toString(), lvPosition);
        etEditText.setText("");
        writeItems();

    }

    public void updateEditItem() {
        txt = aToDoAdapter.getItem(lvPosition);
        aToDoAdapter.remove(txt);
        aToDoAdapter.insert(updateTxt, lvPosition);
        writeItems();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int code = data.getExtras().getInt("code", 0);
            if (code == 200) {
                updateTxt = data.getExtras().getString("newText");
                updateEditItem();
            } else if (code == 201) {
                // delete
                txt = aToDoAdapter.getItem(lvPosition);
                aToDoAdapter.remove(txt);
                writeItems();
            } else if (code == 202) {
                // cancel or do nothing
            }
        }
    }
}

