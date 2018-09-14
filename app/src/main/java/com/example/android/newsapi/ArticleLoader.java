package com.example.android.newsapi;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private static final String LOG_TAG = ArticleLoader.class.getName();
    //String searchUrl;

    public ArticleLoader(Context context) {
        super(context);
        //searchUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        List<Article> articleList = null;

        try {
            URL url = QueryUtils.createUrl();
            String jsonResponse = QueryUtils.makeHttpRequest(url);
            articleList = QueryUtils.parseJson(jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error to LoadInBackground: ", e);
        }
        return articleList;
    }
}
