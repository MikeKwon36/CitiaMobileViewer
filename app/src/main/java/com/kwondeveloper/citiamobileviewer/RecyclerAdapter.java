package com.kwondeveloper.citiamobileviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mike on 8/27/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<String> mList;
    private Context mContext;

    public RecyclerAdapter(ArrayList<String> array) {
        mList = array;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private WebView widget;

        public ViewHolder(View itemView) {
            super(itemView);
            widget = (WebView) itemView.findViewById(R.id.xmlWebView);
            WebSettings webSettings = widget.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.widget_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int pPosition) {
        final int position = pPosition;
        holder.widget.loadUrl(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
