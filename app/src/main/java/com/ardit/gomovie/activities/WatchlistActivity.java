package com.ardit.gomovie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ardit.gomovie.R;
import com.ardit.gomovie.adapters.WatchlistAdapter;
import com.ardit.gomovie.databinding.ActivityWatchlistBinding;
import com.ardit.gomovie.listeners.WatchlistListener;
import com.ardit.gomovie.models.TVShow;
import com.ardit.gomovie.utilities.TempDataHolder;
import com.ardit.gomovie.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListener{

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel viewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding = DataBindingUtil.setContentView(this,R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization(){
        viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchlistBinding.imageBack.setOnClickListener(view -> onBackPressed());
        watchlist = new ArrayList<>();
        loadWatchList();
    }

    private void loadWatchList(){
        activityWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShows -> {
            activityWatchlistBinding.setIsLoading(false);
            //Toast.makeText(getApplicationContext(),"WatchList: " + tvShows.size(),Toast.LENGTH_SHORT).show();
            if(watchlist.size()>0){
                //onStop()
                // Using clear will clear all, but can accept new disposable
                watchlist.clear();
            }
            watchlist.addAll(tvShows);
            watchlistAdapter = new WatchlistAdapter(watchlist,this);
            activityWatchlistBinding.watchlistRecyclerView.setAdapter(watchlistAdapter);
            activityWatchlistBinding.watchlistRecyclerView.setVisibility(View.VISIBLE);
            //onDestroy()
            // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
            compositeDisposable.dispose();
        })
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(TempDataHolder.IS_WATCHLIST_UPDATED){
            loadWatchList();
            //TempDataHolder.IS_WATCHLIST_UPDATED= false;
        }
   // }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeTVShowFromWatchList(tvShow)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
            watchlist.remove(position);
            watchlistAdapter.notifyItemRemoved(position);
            watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.getItemCount());
            compositeDisposableForDelete.dispose();
        }));
    }
}