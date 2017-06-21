package com.example.tibo.swapdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Tibo on 31/05/2017.
 */

public class FragmentThree extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListContacts listeContacts;

    Context applicationContext;

    FloatingActionButton btn_add;
    ListView vListContacts;
    ArrayAdapter<String> listeNomAdapter;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.e("Fragment onCreateView ","3");

        View rootView = inflater.inflate(R.layout.fragment_three_layout,container, false);
        vListContacts = (ListView)rootView.findViewById(R.id.liste_contacts);
        btn_add = (FloatingActionButton)rootView.findViewById(R.id.btn_add_contact);
        btn_add.setOnClickListener(this);
        vListContacts.setOnItemClickListener(this);
        vListContacts.setOnItemLongClickListener(this);

        listeContacts = MainActivity.getListContacts();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate ","3");

        applicationContext = MainActivity.getContextOfApplication();
    }

    @Override
    public void onStart()
    {
        Log.e("onStart","3");
        super.onStart();

        if(listeNomAdapter == null)
        {
            listeNomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        }

        listeNomAdapter.clear();
        for(int i=0;i<listeContacts.getListContacts().size();i++)
        {
            listeNomAdapter.add(listeContacts.getListContacts().get(i).getName());
        }

        vListContacts.setAdapter(listeNomAdapter);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == btn_add.getId())
        {
            boiteDialogueModifSupp();
        }
    }

    private void boiteDialogueModifSupp()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.boite_dialogue_contact, null);
        dialogView.findViewById(R.id.btn_creer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentAddConntactActivity = new Intent(applicationContext,AjouterContactActivity.class);
                intentAddConntactActivity.putExtra("listeContacts", listeContacts);
                startActivity(intentAddConntactActivity);
            }
        });
        dialogView.findViewById(R.id.btn_importer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentImportConntactActivity = new Intent(applicationContext,ImporterContact.class);
                intentImportConntactActivity.putExtra("listeContacts", listeContacts);
                startActivity(intentImportConntactActivity);
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        Intent intentEditConntactActivity = new Intent(applicationContext,ContactEdit.class);
        intentEditConntactActivity.putExtra("listeContacts", listeContacts);
        intentEditConntactActivity.putExtra("position",position);
        startActivity(intentEditConntactActivity);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Supprimer ce contact ?");
        alertDialogBuilder.setNegativeButton("Annuler",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Toast.makeText(getActivity(), "Annulé",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

                listeContacts.getListContacts().remove(position);
                saveListContacts(listeContacts);
                Toast.makeText(getActivity(), "Contact supprimé",Toast.LENGTH_SHORT).show();
                onStart();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        return true;

    }

    public void saveListContacts(ListContacts listContacts)
    {
        try
        {
            FileOutputStream output = getActivity().openFileOutput("listeContacts.txt", Context.MODE_PRIVATE);
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
