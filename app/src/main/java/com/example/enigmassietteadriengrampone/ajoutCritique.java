package com.example.enigmassietteadriengrampone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ajoutCritique extends AppCompatActivity{
    Database myDb;
    EditText editTextNom, editTextDateHeure, editTextDeco, editTextNourriture, editTextService, editTextCrit, editTextId,editTextMail;
    Button buttonV;
    Button buttonRetour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_critique);
        myDb = new Database(this);

        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextDateHeure = (EditText) findViewById(R.id.editTextDateHeure);
        editTextDeco = (EditText) findViewById(R.id.editTextDeco);
        editTextNourriture = (EditText) findViewById(R.id.editTextNourriture);
        editTextService = (EditText) findViewById(R.id.editTextService);
        editTextCrit = (EditText) findViewById(R.id.editTextCrit);
        buttonV = (Button) findViewById(R.id.buttonV);
        buttonRetour=(Button)findViewById(R.id.buttonRetour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AjouterDonnees();


    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public void AjouterDonnees() {
        buttonV.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editTextNom.getText().toString().trim().length() == 0){
                            Toast.makeText(ajoutCritique.this, "Le nom du restaurant ne peut pas être vide", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(editTextDateHeure.getText().toString().trim().length() != 0){
                                if(!isValidDate(editTextDateHeure.getText().toString())){
                                    Toast.makeText(ajoutCritique.this, "Le date et l'heure doivent être valide", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    boolean isInserted = myDb.insertData(editTextNom.getText().toString(), editTextDateHeure.getText().toString(), editTextDeco.getText().toString(), editTextNourriture.getText().toString(), editTextService.getText().toString(), editTextCrit.getText().toString());
                                    if (isInserted == true) {
                                        Toast.makeText(ajoutCritique.this, "Données insérées", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    } else {
                                        Toast.makeText(ajoutCritique.this, "Données non insérées", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            else{
                                boolean isInserted = myDb.insertData(editTextNom.getText().toString(), editTextDateHeure.getText().toString(), editTextDeco.getText().toString(), editTextNourriture.getText().toString(), editTextService.getText().toString(), editTextCrit.getText().toString());
                                if (isInserted == true) {
                                    Toast.makeText(ajoutCritique.this, "Données insérées", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(ajoutCritique.this, "Données non insérées", Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                    }
                }
        );
    }
}
