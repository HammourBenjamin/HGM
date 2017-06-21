package com.example.tibo.swapdemo;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends FragmentActivity{

    ViewPager viewPager;

    int index_fragment;

    static Context contextOfApplication;
    TabLayout tabLayout;

    static ListContacts listeContacts;
    static ListeNotifications listeNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("onCreateMain","Main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        contextOfApplication = getApplicationContext();

        tabLayout = (TabLayout)findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        index_fragment = getIntent().getIntExtra("index_fragment",1);

        listeContacts = (ListContacts)loadListeContact();
        listeNotifications = (ListeNotifications)loadListeNotification();

        if(getIntent().getSerializableExtra("listeContacts") != null)
        {
            listeContacts = (ListContacts)getIntent().getSerializableExtra("listeContacts");
        }

            viewPager.setCurrentItem(index_fragment);

        //Vérifier si le service est déjà lancé.
        if(!isMyServiceRunning(NotificationService.class))
        {
            Intent serviceNotif = new Intent(this, NotificationService.class);
            startService(serviceNotif);
        }

    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    public static ListContacts getListContacts()
    {
        return listeContacts;
    }

    public static ListeNotifications getListeNotifications()
    {
        return listeNotifications;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("listeContacts",listeContacts);
    }

    public Object loadListeContact()
    {
        try
        {
            FileInputStream input = this.openFileInput("listeContacts.txt");
            ObjectInputStream ois = new ObjectInputStream(input);
            Object o = ois.readObject();
            return o;

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return listeContacts = new ListContacts();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Object loadListeNotification()
    {
        try
        {
            FileInputStream input = this.openFileInput("listeNotifications.txt");
            ObjectInputStream ois = new ObjectInputStream(input);
            Object o = ois.readObject();
            return o;

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return listeNotifications = new ListeNotifications();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
}
