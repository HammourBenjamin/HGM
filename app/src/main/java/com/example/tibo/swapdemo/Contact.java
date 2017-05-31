package com.example.tibo.swapdemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ben on 31/05/2017.
 */

public class Contact {

    private String nom;
    private String num;
    private String id;

    public Contact(String nom, String num, String id)
    {
        this.nom = nom;
        this.num = num;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Nom : " + nom + "\n" + "Numero de Telephone :" + num;
    }

    public static ArrayList<Contact> getAllContact(ContentResolver cr)
    {

        ArrayList<Contact> listContact = new ArrayList<Contact>();

        String id = null;
        String name = null;
        String phoneNr = null;
        Cursor cursor = null;

        try
        {
            cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "DISPLAY_NAME ASC");
        }
        catch (Exception ex)
        {
            Log.e("Error on contact", ex.getMessage());
        }

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNr = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    }

                    phoneCursor.close();
                    Contact contact = new Contact(name, phoneNr, id);
                    listContact.add(contact);
                }

            }

        }
        return listContact;
    }


}
