package org.luke.wow.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.aerogear.android.Callback;
import org.aerogear.android.Pipeline;
import org.aerogear.android.impl.pipeline.PipeConfig;
import org.aerogear.android.pipeline.Pipe;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lholmquist
 * Date: 11/29/12
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class RealmFragement extends Fragment {

    private Pipeline pipeline;
    private Pipe<RealmReturn> realmPipe;
    private List<Realm> realmList = new ArrayList<Realm>();
    private ArrayAdapter<Realm> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list,null);
        adapter = new ArrayAdapter<Realm>(getActivity(),android.R.layout.simple_list_item_1,realmList);
        ListView realmListView = (ListView) view.findViewById(R.id.list);
        realmListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            //Setup our pipeline
            URL baseURL = new URL("http://us.battle.net/api/wow/");
            pipeline = new Pipeline(baseURL);

            PipeConfig pipeConfig = new PipeConfig(baseURL, RealmReturn.class);
            pipeConfig.setName("realms");
            pipeConfig.setEndpoint("realm/status");
            pipeline.pipe(RealmReturn.class, pipeConfig);

            realmPipe = pipeline.get("realms");

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getRealms();
    }

    private void getRealms() {

        realmPipe.read(new Callback<List<RealmReturn>>() {
            @Override
            public void onSuccess(List<RealmReturn> data) {
                realmList.clear();
                realmList.addAll(data.get(0).getRealms());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                //To change body of implemented methods use File | Settings | File Templates.
                e.printStackTrace();
            }
        });
    }
}
