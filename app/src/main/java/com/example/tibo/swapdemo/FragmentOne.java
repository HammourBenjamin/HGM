package com.example.tibo.swapdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tibo on 31/05/2017.
 */

public class FragmentOne extends Fragment implements AdapterView.OnItemClickListener{

    ListContacts listContacts;

    ListView listConversation;
    ArrayList<Sms> listeLastSms;
    Context applicationContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.e("Fragment onCreateView ","1");
        View rootView = inflater.inflate(R.layout.fragment_one_layout,container, false);
        listConversation = (ListView)rootView.findViewById(R.id.list_conversation);

        initialisation();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        applicationContext = MainActivity.getContextOfApplication();
        Log.e("onCreate ","1");
    }

    public void initialisation()
    {
        listContacts = MainActivity.getListContacts();

        listeLastSms = new ArrayList<Sms>();

        if(listContacts.getListContacts().size()>0)
        {
            for(int i=0;i<listContacts.getListContacts().size();i++)
            {
                listeLastSms.add(getLastSMS(listContacts.getListContacts().get(i).getNumber()));
            }
        }

        CustomerAdapter customerAdapter = new CustomerAdapter();

        listConversation.setAdapter(customerAdapter);
        listConversation.setSelection(customerAdapter.getCount()-1);
        listConversation.setOnItemClickListener(this);
    }

    @Override
    public void onStart()
    {
        Log.e("onStart","1");
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String numero = listContacts.getListContacts().get(position).getNumber();
        Intent intentSmsActivity = new Intent(applicationContext,SmsActivity.class);
        intentSmsActivity.putExtra("numero",numero);
        startActivity(intentSmsActivity);
    }

    private Sms getLastSMS(String numero)
    {
        Uri uri = Uri.parse("content://sms");
        Sms msg_recup = null;

        if(numero.charAt(0)=='+')
        {
            numero = numero.toString().substring(3,numero.length());
        }
        else
        {
            numero = numero.toString().substring(1,numero.length());
        }

        String[] arg = {"%"+numero};


        Cursor cursor = applicationContext.getContentResolver().query(uri, null, "address LIKE ?",arg, "date DESC");

        if(cursor.moveToFirst())
        {
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
            String type = cursor.getString(cursor.getColumnIndex("type")).toString();
            String date = cursor.getString(cursor.getColumnIndex("date")).toString();

            String SMStype = null;

            switch (Integer.parseInt(type))
            {
                case 1 : SMStype = "reçu"; break;
                case 2 : SMStype = "envoyé"; break;
                case 3 : SMStype = "brouillon"; break;
            }

            msg_recup = new Sms(body, date, SMStype);
        }
        else
        {
            msg_recup = new Sms(null, null, null);
        }

        cursor.close();
        return msg_recup;
    }

    class CustomerAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return listContacts.getListContacts().size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.item_liste_conversation,null);
            TextView num = (TextView)convertView.findViewById(R.id.lbl_name);
            TextView sms = (TextView)convertView.findViewById(R.id.lbl_lastMessage);

            num.setText(listContacts.getListContacts().get(position).getName());
            if(listeLastSms.get(position).getBody() != null)
            {
                sms.setText(listeLastSms.get(position).getBody());
            }
            else
            {
                sms.setText("Aucune conversation");
            }

            return convertView;
        }
    }
}
