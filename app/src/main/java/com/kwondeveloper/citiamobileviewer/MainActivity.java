package com.kwondeveloper.citiamobileviewer;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDACallback;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_BUNDLE = "KEY";

    private static final String CDA_SPACE = "40ayu9gczyz8";
    private static final String CDA_TOKEN = "9f56c5a04a2fb76dcbe1e9044c24a08a05c944a2e61ad8d9c74624bdaef97792";
    private static final String CDA_ENTRY_ID = "jFlp5rk83m242Ou0CMgcs";

    private TabLayout mTabLayout;
    private static ViewPager mViewPager;
    private static CitiaPagerAdapter mSectionsPagerAdapter;
    private static CDAClient mClient;
    private static ArrayList<String> mGe;
    private static ArrayList<String> mViacom;
    private static ArrayList<String> mMastercard;
    private static RecyclerAdapter mAdapter;
    private static ArrayList<String> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mGe = new ArrayList<>();
        mViacom = new ArrayList<>();
        mMastercard = new ArrayList<>();

        mClient = CDAClient.builder().setSpace(CDA_SPACE).setToken(CDA_TOKEN).build();
        mClient.fetch(CDAEntry.class).one(CDA_ENTRY_ID, new CDACallback<CDAEntry>() {
            @Override
            protected void onSuccess(CDAEntry result) {
                mGe = result.getField("ge");
                mViacom = result.getField("viacom");
                mMastercard = result.getField("mastercard");
                mSectionsPagerAdapter = new CitiaPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                Log.d("***Main", "Contentful API call successful");
                Log.d("***Main", "GE list size: " + mGe.size());
                Log.d("***Main", "Viacom list size: " + mViacom.size());
                Log.d("***Main", "Mastercard list size: " + mMastercard.size());
            }
        });
    }

    public static class PlaceholderFragment extends Fragment {
        public RecyclerView mRecycler;
        public RecyclerView.LayoutManager mManager;

        public PlaceholderFragment() {}

        public static PlaceholderFragment newInstance(String client) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(FRAGMENT_BUNDLE,client);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            String client = getArguments().getString(FRAGMENT_BUNDLE);
            mRecycler = (RecyclerView) rootView.findViewById(R.id.xmlRecyclerView);
            mManager = new LinearLayoutManager(getContext());
            mRecycler.setLayoutManager(mManager);

            if(client.equals(getResources().getString(R.string.ge))){
                mList = mGe;
                Log.d("***Main", "GE fragment built");
            } else if (client.equals(getResources().getString(R.string.viacom))){
                mList = mViacom;
                Log.d("***Main", "Viacom fragment built");
            } else {
                mList = mMastercard;
                Log.d("***Main", "Mastercard fragment built");
            }
            mAdapter = new RecyclerAdapter(mList);
            mRecycler.setAdapter(mAdapter);

            return rootView;
        }
    }

    public class CitiaPagerAdapter extends FragmentStatePagerAdapter {

        int numOfClients = 3;

        public CitiaPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String client = "";
            switch (position) {
                case 0:
                    client = getResources().getString(R.string.ge);
                    break;
                case 1:
                    client = getResources().getString(R.string.viacom);
                    break;
                case 2:
                    client = getResources().getString(R.string.mastercard);
                    break;
            }
            return PlaceholderFragment.newInstance(client);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position) {
                case 0:
                    title = getResources().getString(R.string.ge);
                    return title;
                case 1:
                    title = getResources().getString(R.string.viacom);
                    return title;
                case 2:
                    title = getResources().getString(R.string.mastercard);
                    return title;
            }
            return null;
        }

        @Override
        public int getCount() {
            return numOfClients;
        }
    }
}
