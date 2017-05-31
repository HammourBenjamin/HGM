package com.example.tibo.swapdemo;

import android.app.DatePickerDialog;
import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ben on 31/05/2017.
 */

public class AjouterContactActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText edt_nom;
    EditText edt_numero;
    EditText date_anniv;
    Button btn_enregistrer;
    Button btn_annuler;
    DatePickerDialog dateAnniv;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_contact_layout);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);

        findViewsById();
        setDateTimeField();
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_enregistrer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == btn_enregistrer.getId())
        {
            String name = edt_nom.getText().toString();
            String number = edt_numero.getText().toString();

            Log.v("name",edt_nom.getText().toString());
            Log.v("number",edt_numero.getText().toString());

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                    .build());

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());

            // Update
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(getBaseContext(), "Contact " + name + " ajouté", Toast.LENGTH_SHORT).show();

            this.finish();
        }

        if(v.getId() == date_anniv.getId())
        {
            dateAnniv.show();
        }
    }

    private void findViewsById() {
        date_anniv = (EditText) findViewById(R.id.edt_anniv);
        date_anniv.setInputType(InputType.TYPE_NULL);
        edt_nom =(EditText)findViewById(R.id.edt_nom);
        edt_numero =(EditText)findViewById(R.id.edt_numero);
        btn_enregistrer =(Button)findViewById(R.id.btn_enregistrer);
        btn_annuler =(Button)findViewById(R.id.btn_annuler);
    }

    private void setDateTimeField() {
        date_anniv.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dateAnniv = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date_anniv.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
