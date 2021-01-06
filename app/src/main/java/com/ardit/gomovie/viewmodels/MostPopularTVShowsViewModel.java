package com.ardit.gomovie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ardit.gomovie.repositories.MostPopularTVShowsRepository;
import com.ardit.gomovie.response.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {


    public MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page){
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }


}
