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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CitiaPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public static Citia mCitia;
    public static final String FRAGMENT_BUNDLE = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mCitia = new Citia();

        mSectionsPagerAdapter = new CitiaPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public static class PlaceholderFragment extends Fragment {

        public ArrayList<String> mList;

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
            RecyclerView list = (RecyclerView) rootView.findViewById(R.id.xmlRecyclerView);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
            list.setLayoutManager(manager);

            if(client.equals(getResources().getString(R.string.ge))){
                mList = mCitia.mGE;
            } else if (client.equals(getResources().getString(R.string.viacom))){
                mList = mCitia.mViacom;
            } else {
                mList = mCitia.mMastercard;
            }
            RecyclerAdapter adapter = new RecyclerAdapter(mList);
            list.setAdapter(adapter);

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
