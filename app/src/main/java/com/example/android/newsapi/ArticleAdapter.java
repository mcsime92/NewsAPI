package com.example.android.newsapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * An {@link ArticleAdapter} knows how to create a list item layout for each article
 * in the data source (a list of {@link Article} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    /**
     * Constructs a new {@link ArticleAdapter}.
     *
     * @param context of the app
     * @param articles   is the list of earthquakes, which is the data source of the adapter
     */
    public ArticleAdapter(Context context, int articles) {
        super(context, articles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        // Find the article at the given position in the list of articles
        Article article = getItem(position);
        assert article != null;
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView authorView = (TextView) listItemView.findViewById(R.id.section);
        authorView.setText(article.getSection());

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(article.getTitle());

        return listItemView;
    }
}
