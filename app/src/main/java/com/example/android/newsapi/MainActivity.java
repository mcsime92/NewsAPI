package com.example.android.newsapi;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.example.android.newsapi.QueryUtils.getURLForRequest;
import static com.example.android.newsapi.R.id.list;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public ArticleAdapter mAdapter;
    public TextView mEmptyStateTextView;
    public ListView articleListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        articleListView = (ListView) findViewById(list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new ArticleAdapter(this, -1);

        // Set adapter to ListView, so list populates in user interface
        articleListView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        articleListView.setEmptyView(mEmptyStateTextView);

        getLoaderManager().initLoader(0, null, this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = mAdapter.getItem(position);
                String url = article.getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle bundle) {
        return new ArticleLoader(this);
        //TODO : check if returns full class ex: return new GreatestNewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        if (articles != null) {
            mAdapter.setNotifyOnChange(false);
            mAdapter.clear();
            mAdapter.setNotifyOnChange(true);
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    private boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        try {
            return networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (isInternetConnectionAvailable()) {
            try {
                getURLForRequest();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);

        if (isInternetConnectionAvailable()) {
            try {
                getURLForRequest();
                onCompletion();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void onCompletion() {

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
