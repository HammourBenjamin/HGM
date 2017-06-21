package com.example.tibo.swapdemo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Tibo on 31/05/2017.
 */

public class FragmentTwo extends Fragment implements AdapterView.OnItemClickListener
{

    ListeNotifications listeNotifications;

    Context applicationContext;

    TextView texteNoNotifications;
    ListView vListeNotifications;
    ArrayAdapter<String> adapterVListeNotifications;
    NotificationManager manager;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.e("Fragment onCreateView ","2");
        rootView = inflater.inflate(R.layout.fragment_two_layout,container, false);

        texteNoNotifications = (TextView) rootView.findViewById(R.id.texte_no_notifications);
        vListeNotifications = (ListView) rootView.findViewById(R.id.liste_notifications);
        vListeNotifications.setOnItemClickListener(this);


        listeNotifications = MainActivity.getListeNotifications();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e("onCreate ","2");

        applicationContext = MainActivity.getContextOfApplication();
        manager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onStart()
    {
        Log.e("onStart","2");
        super.onStart();

        if(adapterVListeNotifications == null)
        {
            adapterVListeNotifications = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        }

        adapterVListeNotifications.clear();
        for(int i=0;i<listeNotifications.getListeNotifications().size();i++)
        {
            adapterVListeNotifications.add(listeNotifications.getListeNotifications().get(i).getName());
        }

        vListeNotifications.setAdapter(adapterVListeNotifications);

        Initialisation();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.e("onActivityCreated","2");

        super.onActivityCreated(savedInstanceState);
    }

    public void Initialisation()
    {
        if(adapterVListeNotifications.getCount() == 0)
        {
            texteNoNotifications.setVisibility(View.VISIBLE);
            vListeNotifications.setVisibility(View.GONE);
        }
        else
        {
            texteNoNotifications.setVisibility(View.GONE);
            vListeNotifications.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String numero = listeNotifications.getListeNotifications().get(position).getNumber();
        String type = listeNotifications.getListeNotifications().get(position).getType();
        int age = listeNotifications.getListeNotifications().get(position).getAge();

        manager.cancel(listeNotifications.getListeNotifications().get(position).getNotification_id());
        listeNotifications.getListeNotifications().remove(position);
        saveListeNotifications(listeNotifications);

        Intent intentSmsActivity = new Intent(applicationContext,SmsActivity.class);
        intentSmsActivity.putExtra("numero",numero);
        intentSmsActivity.putExtra("age",age);
        intentSmsActivity.putExtra("type",type);
        startActivity(intentSmsActivity);
    }

    public void saveListeNotifications(ListeNotifications listeNotifications)
    {
        try
        {
            FileOutputStream output = applicationContext.openFileOutput("listeNotifications.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(listeNotifications);
            oos.flush();
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
