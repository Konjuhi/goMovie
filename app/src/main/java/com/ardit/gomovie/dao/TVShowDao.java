package com.ardit.gomovie.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ardit.gomovie.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShows")
    //Flowable emit a stream of element(emit exactly one element,emits zero or one element)
    Flowable<List<TVShow>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //Completable emits a complete event(without any data type just success/failure)
    Completable addToWatchList(TVShow tvShow);

    @Delete
    Completable removeFromWatchlist(TVShow tvShow);

    @Query("Select * From tvShows Where id = :tvShowId")
    Flowable<TVShow> getTVShowFromWatchlist(String tvShowId);

}
