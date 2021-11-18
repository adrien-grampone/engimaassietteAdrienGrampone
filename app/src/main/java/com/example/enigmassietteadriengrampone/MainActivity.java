package com.example.enigmassietteadriengrampone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    Database db;
    ArrayList<String> listItem;
    ArrayAdapter<String> arrayAdapter;

    ListView simpleList;
    Button buttonAjouter;
    Button buttonRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView)findViewById(R.id.listCritiques);
        db = new Database(this);
        listItem = new ArrayList<>();
        viewData();



        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String text = simpleList.getItemAtPosition(i).toString();
                int number = text.indexOf(" -");
                String subString = "";
                if (number != -1)
                {
                    subString= text.substring(0 , number); //this will give abc
                }
                Intent myIntent = new Intent(MainActivity.this, modifierSupprimer.class);
                myIntent.putExtra("idCritique",subString);
                startActivity(myIntent);
                //Toast.makeText(MainActivity.this, ""+subString, Toast.LENGTH_SHORT).show();
            }
        });

        buttonAjouter = (Button) findViewById(R.id.buttonAjouterDonnees);
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ajoutCritique.class);
                startActivity(i);

            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItem.clear();
                viewData();
            }
        });

    }

   private void viewData(){
       Cursor cursor = db.getAllData();

       if(cursor.getCount() == 0){
           Toast.makeText(this, "Pas encore de critique enregistré", Toast.LENGTH_SHORT).show();
       }
       else{
           while(cursor.moveToNext()){
               listItem.add(cursor.getString(0) + " - "+ cursor.getString(1));
           }
           arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, listItem);
           simpleList.setAdapter(arrayAdapter);
       }
   }
}