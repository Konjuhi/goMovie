package com.ardit.gomovie.response;

import com.ardit.gomovie.models.TVShowDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    @Expose
    private TVShowDetails tvShow;

    public TVShowDetailsResponse() {
    }

    public TVShowDetailsResponse(TVShowDetails tvShow) {
        this.tvShow = tvShow;
    }

    public TVShowDetails getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShowDetails tvShow) {
        this.tvShow = tvShow;
    }
}
