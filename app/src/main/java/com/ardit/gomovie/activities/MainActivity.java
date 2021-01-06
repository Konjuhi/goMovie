package com.ardit.gomovie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.ardit.gomovie.R;
import com.ardit.gomovie.adapters.TVShowsAdapter;
import com.ardit.gomovie.databinding.ActivityMainBinding;
import com.ardit.gomovie.listeners.TVShowsListener;
import com.ardit.gomovie.models.TVShow;
import com.ardit.gomovie.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;

    private int currentPage =1;
    private int totalAvailablePages =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
       // tvShowsAdapter = new TVShowsAdapter(tvShows);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)){
                    if(currentPage <=totalAvailablePages){
                        currentPage +=1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        activityMainBinding.imageWatchList.setOnClickListener(view->
                startActivity(new Intent(getApplicationContext(),WatchlistActivity.class)));
         activityMainBinding.imageSearch.setOnClickListener(view->
                startActivity(new Intent(getApplicationContext(),SearchActivity.class)));

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
       // activityMainBinding.setIsLoading(true);
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mosPopularTVShowsResponse -> {
            //Toast.makeText(getApplicationContext(),"Total Pages: " + mostPopularTVShowsResponse.getTotalPages(),Toast.LENGTH_SHORT).show();
            //activityMainBinding.setIsLoading(false);
            toggleLoading();
            if (mosPopularTVShowsResponse != null) {
                totalAvailablePages = mosPopularTVShowsResponse.getPage();
                if (mosPopularTVShowsResponse.getTvShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(mosPopularTVShowsResponse.getTvShows());
                   // tvShowsAdapter.notifyDataSetChanged();
                    tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });

    }
        private void toggleLoading(){
            if (currentPage ==1){
                if(activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                    activityMainBinding.setIsLoading(false);
                }else{
                    activityMainBinding.setIsLoading(true);
                }
            }else{
                if(activityMainBinding.getIsLoadingMore()!=null && activityMainBinding.getIsLoadingMore()){
                    activityMainBinding.setIsLoadingMore(false);
                }else{
                    activityMainBinding.setIsLoadingMore(true);
                }
            }
        }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        /*intent.putExtra("id",tvShow.getId());
        intent.putExtra("name",tvShow.getName());
        intent.putExtra("startDate",tvShow.getStartDate());
        intent.putExtra("country",tvShow.getCountry());
        intent.putExtra("network",tvShow.getNetwork());
        intent.putExtra("status",tvShow.getStatus());*/
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}
