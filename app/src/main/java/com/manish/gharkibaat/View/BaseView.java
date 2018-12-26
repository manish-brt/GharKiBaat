package com.manish.gharkibaat.View;

/**********************************
 * Created by Manish on 01-Oct-18
 ***********************************/
public interface BaseView {

    void showLoading();

    void hideLoading();

    void showSnackBar(String message);

    void showToastMessage(String message);

    void setTitle(String title);

    void setSupportToolbar();

    void setToolbarLeftIconClickListener();

}
