package com.example.android.newsapi;

public class Article {

    private String mSection;
    private String mTitle;
    private String mUrl;

    /**
     * Constructs a new object {@link Article}.
     *
     */

    public Article(String title, String section, String urlArticle) {
        mTitle = title;
        mSection = section;
        mUrl = urlArticle;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
