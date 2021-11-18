package com.example.enigmassietteadriengrampone;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;


public class modifierSupprimer extends AppCompatActivity {
    private static final String TAG = "Main";
    Database myDb;
    EditText editTextNom,editTextDateHeure,editTextDeco,editTextNourriture,editTextService,editTextCrit;
    Button shareData;
    Button buttonUp;
    Button buttonSupp;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_supprimer_critique);
        Intent myIntent = getIntent();
        myDb = new Database(this);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextDateHeure = (EditText) findViewById(R.id.editTextDateHeure);
        editTextDeco = (EditText) findViewById(R.id.editTextDeco);
        editTextNourriture = (EditText) findViewById(R.id.editTextNourriture);
        editTextService = (EditText) findViewById(R.id.editTextService);
        editTextCrit = (EditText) findViewById(R.id.editTextCrit);
        shareData = (Button) findViewById((R.id.shareData));
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonSupp = (Button) findViewById(R.id.buttonDelete);

        buttonBack=(Button)findViewById(R.id.buttonRetour);

        String idToFind =myIntent.getStringExtra("idCritique");
        Cursor critique = myDb.getDataById(idToFind);
        if(critique.getCount()==0)
        {
            return;
        }

        while(critique.moveToNext())
        {
            editTextNom.setText(critique.getString(1));
            editTextDateHeure.setText(critique.getString(2));
            editTextDeco.setText(critique.getString(3));
            editTextNourriture.setText(critique.getString(4));
            editTextService.setText(critique.getString(5));
            editTextCrit.setText(critique.getString(6));

        }


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shareData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Partage de ma critique");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Nom du restaurant : " + editTextNom.getText().toString() + "\n" + "Date et heure: "  + editTextDateHeure.getText().toString() + "\n" + "Note décoration : " + editTextDeco.getText().toString() + "\n" + "Note nourriture : " + editTextNourriture.getText().toString() + "\n" + "Note service : " + editTextService.getText().toString() + "\n" + "Critique : " + editTextCrit.getText().toString());

                try {
                    startActivity(Intent.createChooser(emailIntent, "Envoie de l'email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(modifierSupprimer.this,
                            "Erreur", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ModifierDonnees(idToFind);
        SupprimerDonnee(idToFind);
    }

    public void SupprimerDonnee(String idToFind)
    {
        buttonSupp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows=myDb.SupprimerDonnee(idToFind);
                        if(deletedRows > 0)
                        {
                            Toast.makeText(modifierSupprimer.this,"Donnée supprimée",Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(modifierSupprimer.this,"Donnée non supprimée",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
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


    public void ModifierDonnees(String idToFind)
    {
        buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(editTextNom.getText().toString().trim().length() == 0){
                    Toast.makeText(modifierSupprimer.this, "Le nom du restaurant ne peut pas être vide", Toast.LENGTH_LONG).show();
                }
                else{
                    if(editTextDateHeure.getText().toString().trim().length() != 0){
                        if(!isValidDate(editTextDateHeure.getText().toString())){
                            Toast.makeText(modifierSupprimer.this, "Le date et l'heure doivent être valide", Toast.LENGTH_LONG).show();
                        }
                        else{
                            boolean isUpdate=myDb.ModifierDonnees(idToFind,
                                    editTextNom.getText().toString(),
                                    editTextDateHeure.getText().toString(),
                                    editTextDeco.getText().toString(),
                                    editTextNourriture.getText().toString(),
                                    editTextService.getText().toString(),
                                    editTextCrit.getText().toString());
                            if(isUpdate==true)
                            {
                                Toast.makeText(modifierSupprimer.this,"Données modifiées",Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(modifierSupprimer.this,"Données non modifiées",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else{
                        boolean isUpdate=myDb.ModifierDonnees(idToFind,
                                editTextNom.getText().toString(),
                                editTextDateHeure.getText().toString(),
                                editTextDeco.getText().toString(),
                                editTextNourriture.getText().toString(),
                                editTextService.getText().toString(),
                                editTextCrit.getText().toString());
                        if(isUpdate==true)
                        {
                            Toast.makeText(modifierSupprimer.this,"Données modifiées",Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(modifierSupprimer.this,"Données non modifiées",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });
    }


    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}