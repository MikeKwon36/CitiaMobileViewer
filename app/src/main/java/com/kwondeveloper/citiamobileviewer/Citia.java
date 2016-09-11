package com.kwondeveloper.citiamobileviewer;

import android.util.Log;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDACallback;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mike on 8/27/2016.
 */
public class Citia {
    private ArrayList<String> mGeUrls, mMastercardUrls, mViacomUrls;
    private static Citia mInstance;

    //Contentful API
    public static CDAClient mClient;
    private static final String CDA_SPACE = "40ayu9gczyz8";
    private static final String CDA_TOKEN = "9f56c5a04a2fb76dcbe1e9044c24a08a05c944a2e61ad8d9c74624bdaef97792";
    private static final String CDA_ENTRY_ID = "jFlp5rk83m242Ou0CMgcs";

    private Citia() {
        mGeUrls = new ArrayList<>();
        mViacomUrls = new ArrayList<>();
        mMastercardUrls = new ArrayList<>();

        mClient = CDAClient.builder().setSpace(CDA_SPACE).setToken(CDA_TOKEN).build();
        mClient.fetch(CDAEntry.class).one(CDA_ENTRY_ID, new CDACallback<CDAEntry>() {
            @Override
            protected void onSuccess(CDAEntry result) {
                mGeUrls = result.getField("ge");
                mViacomUrls = result.getField("viacom");
                mMastercardUrls = result.getField("mastercard");
                MainActivity.mAdapter.notifyDataSetChanged();
            }
        });

    }

    public static Citia getmInstance(){
        if(mInstance == null){
            mInstance = new Citia();
        }
        return mInstance;
    }

    public ArrayList<String> getmGE() {
        return mGeUrls;
    }

    public void setmGE(ArrayList<String> mGE) {
        this.mGeUrls = mGE;
    }

    public ArrayList<String> getmMastercardUrls() {
        return mMastercardUrls;
    }

    public void setmMastercardUrls(ArrayList<String> mMastercardUrls) {
        this.mMastercardUrls = mMastercardUrls;
    }

    public ArrayList<String> getmViacomUrls() {
        return mViacomUrls;
    }

    public void setmViacomUrls(ArrayList<String> mViacomUrls) {
        this.mViacomUrls = mViacomUrls;
    }
}
