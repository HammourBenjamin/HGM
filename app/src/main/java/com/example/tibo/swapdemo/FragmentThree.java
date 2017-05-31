package com.example.tibo.swapdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class FragmentThree extends Fragment implements View.OnClickListener {

    FloatingActionButton btn_add;
    Context applicationContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_three_layout,container, false);

        btn_add = (FloatingActionButton)rootView.findViewById(R.id.btn_add_contact);
        btn_add.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationContext = MainActivity.getContextOfApplication();
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == btn_add.getId())
        {
            Intent intentSmsActivity = new Intent(applicationContext,AjouterContactActivity.class);
            startActivity(intentSmsActivity);
        }
    }
}
