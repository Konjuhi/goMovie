package com.ardit.gomovie.listeners;

import com.ardit.gomovie.models.TVShow;

public interface WatchlistListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchlist(TVShow tvShow,int position);
}
