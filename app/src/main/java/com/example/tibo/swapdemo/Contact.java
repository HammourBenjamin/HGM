package com.example.tibo.swapdemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ben on 31/05/2017.
 */

public class Contact implements Serializable
{

    private String name;
    private String number;
    private String dateAnniversaire;
    private String id;

    public Contact(String name, String number, String dateAnniversaire)
    {
        this.name = name;
        this.number = number;
        this.dateAnniversaire = dateAnniversaire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateAnniversaire() {
        return dateAnniversaire;
    }

    public void setDateAnniversaire(String dateAnniversaire) {
        this.dateAnniversaire = dateAnniversaire;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
