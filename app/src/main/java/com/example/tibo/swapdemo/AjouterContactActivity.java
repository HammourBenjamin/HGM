package com.example.tibo.swapdemo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ben on 31/05/2017.
 */

public class AjouterContactActivity extends AppCompatActivity implements View.OnClickListener
{
    ListContacts listeContacts;

    EditText edt_nom;
    EditText edt_numero;
    EditText edt_date_anniv;
    Button btn_enregistrer;
    Button btn_annuler;
    DatePickerDialog dateAnniv;
    SimpleDateFormat dateFormatter;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_contact_layout);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        contact = null;

        findViewsById();
        setDateTimeField();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        btn_enregistrer.setOnClickListener(this);
        btn_annuler.setOnClickListener(this);

        listeContacts = (ListContacts)getIntent().getSerializableExtra("listeContacts");
        contact = (Contact)getIntent().getSerializableExtra("contact");

        if(contact!=null)
        {
            edt_nom.setText(contact.getName());
            edt_numero.setText(contact.getNumber());
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == btn_enregistrer.getId())
        {
            String name = edt_nom.getText().toString();
            String number = edt_numero.getText().toString();
            String date_anniv = edt_date_anniv.getText().toString();

            //Sauvegarde dans le fichier interne du téléphone
            Contact contact = new Contact(name,number,date_anniv);
            listeContacts.getListContacts().add(contact);

            saveListContacts(listeContacts);

            //ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI);

            Intent intentActivity = new Intent(this, MainActivity.class);
            intentActivity.putExtra("listeContacts", listeContacts);
            intentActivity.putExtra("index_fragment",2);
            startActivity(intentActivity);
        }

        if(v.getId() == edt_date_anniv.getId())
        {
            dateAnniv.show();
        }

        if(v.getId() == btn_annuler.getId())
        {
            Intent intentActivity = new Intent(this, MainActivity.class);
            intentActivity.putExtra("listeContacts", listeContacts);
            intentActivity.putExtra("index_fragment",2);
            startActivity(intentActivity);
        }
    }

    private void findViewsById()
    {
        edt_date_anniv = (EditText) findViewById(R.id.edt_anniv);
        edt_date_anniv.setInputType(InputType.TYPE_NULL);
        edt_nom =(EditText)findViewById(R.id.edt_nom);
        edt_numero =(EditText)findViewById(R.id.edt_numero);
        btn_enregistrer =(Button)findViewById(R.id.btn_enregistrer);
        btn_annuler =(Button)findViewById(R.id.btn_annuler);
    }

    private void setDateTimeField()
    {
        edt_date_anniv.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dateAnniv = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edt_date_anniv.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void saveListContacts(ListContacts listContacts)
    {
        try
        {
            FileOutputStream output = this.openFileOutput("listeContacts.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(listContacts);
            oos.flush();
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
