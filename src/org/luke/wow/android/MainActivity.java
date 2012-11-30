package org.luke.wow.android;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.aerogear.android.Pipeline;
import org.aerogear.android.impl.pipeline.PipeConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity
{
    private FragmentTransaction fragmentTransaction;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.main);

        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.realms, new RealmFragement());

        fragmentTransaction.commit();



    }

}
