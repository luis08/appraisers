package com.appraisers.app.gbuckets;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public class GDriveListFilesResponse {

    private String kind;
    private boolean incompleteSearch;
    private GDriveCommonResponse[] files;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean isIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public GDriveCommonResponse[] getFiles() {
        return files;
    }

    public void setFiles(GDriveCommonResponse[] files) {
        this.files = files;
    }
}
