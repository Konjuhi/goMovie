package com.ardit.gomovie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ardit.gomovie.repositories.SearchTVShowRepository;
import com.ardit.gomovie.response.TVShowsResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel(){
        searchTVShowRepository = new SearchTVShowRepository();
    }
    
    public LiveData<TVShowsResponse> searchTVShow(String query,int page){
        return searchTVShowRepository.searchTVShow(query,page);
    }
}
