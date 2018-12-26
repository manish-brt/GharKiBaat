package com.manish.gharkibaat.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.manish.gharkibaat.Fragment.NavigationFragment;
import com.manish.gharkibaat.R;
import com.manish.gharkibaat.Utility.CommonUtils;
import com.manish.gharkibaat.View.BaseView;
import com.manish.gharkibaat.View.NavDrawerInterface;

public class BaseActivity extends AppCompatActivity implements BaseView, NavDrawerInterface {

    private final static String TAG = "BaseAct";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSnackBar(String message) {
        if (null != message) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);

            View subView = snackbar.getView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                subView.setBackground(ContextCompat.getDrawable(this, R.color.black));
            }
            TextView textView = (TextView) subView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));

            snackbar.show();
        }

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void setTitle(String title) {
        TextView titleView = findViewById(R.id.title_tv);
        if(null != titleView && null != title){
            titleView.setText(title);
        }
    }

    @Override
    public void setSupportToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }

    }

    @Override
    public void setToolbarLeftIconClickListener() {
        LinearLayout navLL = findViewById(R.id.navigation_ll);
//        LinearLayout backLL = findViewById(R.id.toolbar_ll);
//        if (null != backLL) {
//            backLL.setVisibility(View.GONE);
//        }
        if (null != navLL) {
            navLL.setVisibility(View.VISIBLE);
            navLL.setOnClickListener(onToolbarLeftLayerClickListener);
        }

    }

    View.OnClickListener onToolbarLeftLayerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.navigation_ll:
                    openDrawer();
                    break;
            }
        }
    };

    @Override
    public void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        if (null != drawerLayout && null != navigationView) {
            navigationFragment = new NavigationFragment();
            navigationFragment.setNavDrawerInterface(this);

            getSupportFragmentManager().beginTransaction().add(R.id.nav_fragment_container, navigationFragment).commit();

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    CommonUtils.printErrorLog(TAG, "Drawer opened");
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    CommonUtils.printErrorLog(TAG, "Drawer closed");
                }
            };

//            drawerLayout.addDrawerListener(actionBarDrawerToggle);
//            actionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void openDrawer() {
        if (null != drawerLayout) {
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    @Override
    public void closeDrawer() {
        if (null != drawerLayout) {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                gotoLoginAct();
                return true;

            case R.id.todo:
                showSnackBar("TOO_DOO");
                return true;

            default:
                return false;
        }
    }
    private void gotoLoginAct() {
        Intent AuthIntent = new Intent(this, LoginActivity.class);
        startActivity(AuthIntent);
        finish();
    }
}
