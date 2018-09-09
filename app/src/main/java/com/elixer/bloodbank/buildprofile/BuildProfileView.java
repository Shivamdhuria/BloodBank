package com.elixer.bloodbank.buildprofile;

public interface BuildProfileView {


    void detailsIncorrect();
    void internetError();
    void databaseNotWritten();
    void databaseSuccessfullyWritten();
    void progressBarView();
    void progressBarHide();
}
