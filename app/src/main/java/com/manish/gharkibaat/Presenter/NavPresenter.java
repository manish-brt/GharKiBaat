package com.manish.gharkibaat.Presenter;

import android.content.Context;

import com.manish.gharkibaat.Activity.HomeActivity;
import com.manish.gharkibaat.Activity.LoginActivity;
import com.manish.gharkibaat.Activity.ProfileActivity;
import com.manish.gharkibaat.Model.NavOptionModel;
import com.manish.gharkibaat.View.NavView;

import java.util.ArrayList;
import java.util.List;

/**********************************
 * Created by Manish on 17-Oct-18
 ***********************************/
public class NavPresenter {

    private NavView navView;
    private Context context;

    public NavPresenter(NavView navView, Context context) {
        this.navView = navView;
        this.context = context;
    }

    public void setNavOptionList(){
        List<NavOptionModel> navOptionList = new ArrayList<>();

        navOptionList.add(new NavOptionModel(HomeActivity.class, "Home"));
        navOptionList.add(new NavOptionModel(ProfileActivity.class, "Profile"));
        navOptionList.add(new NavOptionModel(LoginActivity.class, "Logout"));

        navView.setNavOptions(navOptionList);
    }
}
