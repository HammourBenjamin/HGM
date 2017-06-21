package com.example.tibo.swapdemo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ben on 20/06/2017.
 */

public class ListeNotifications implements Serializable
{
    ArrayList<NotificationAppli> listeNotifications = new ArrayList<NotificationAppli>();

    public ArrayList<NotificationAppli> getListeNotifications()
    {
        return listeNotifications;
    }

    public void setListeNotifications(ArrayList<NotificationAppli> listeNotifications)
    {
        this.listeNotifications = listeNotifications;
    }
}
