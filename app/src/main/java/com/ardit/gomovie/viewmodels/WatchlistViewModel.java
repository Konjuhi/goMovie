package com.ardit.gomovie.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ardit.gomovie.database.TVShowsDatabase;
import com.ardit.gomovie.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


public class WatchlistViewModel extends AndroidViewModel {

    private TVShowsDatabase tvShowsDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    //Flowable is used when an observable is emitting huge amounts of data
    // but observer is not able to handle data,This is known Back pressure
    public Flowable<List<TVShow>> loadWatchlist(){
        return tvShowsDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow){
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShow);
    }
}
