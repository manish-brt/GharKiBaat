package com.manish.gharkibaat.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.manish.gharkibaat.Adapter.NavAdapter;
import com.manish.gharkibaat.Model.NavOptionModel;
import com.manish.gharkibaat.Presenter.NavPresenter;
import com.manish.gharkibaat.R;
import com.manish.gharkibaat.Utility.AppSharedPreference;
import com.manish.gharkibaat.View.NavDrawerInterface;
import com.manish.gharkibaat.View.NavView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements NavView, NavAdapter.NavItemClickListener {

    private static final String TAG = "NavFragment";

    NavAdapter navAdapter;
    NavPresenter navPresenter;
    NavDrawerInterface navDrawerInterface;
    FirebaseAuth mAuth;

    @BindView(R.id.recycler_view)RecyclerView recyclerView;

    public NavigationFragment() {
        // Required empty public constructor
    }

    public void setNavDrawerInterface(NavDrawerInterface navDrawerInterface){
        this.navDrawerInterface = navDrawerInterface;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        navAdapter = new NavAdapter(getActivity(), this);
        recyclerView.setAdapter(navAdapter);

        navPresenter = new NavPresenter(this, getActivity());
        navPresenter.setNavOptionList();
    }

    @Override
    public void onNavItemClick(NavOptionModel navOption) {
        if(null != navOption){
            if(null != navOption.getActivotyName()){
                navDrawerInterface.closeDrawer();

                Intent i = new Intent(getActivity(), navOption.getActivotyName());
                if(navOption.getTitle().equalsIgnoreCase("Logout")){
                    mAuth.signOut();

                    if(null != getActivity()) {
                        getActivity().finish();
                    }
                }

                startActivity(i);
            }
        }
    }

    @Override
    public void setNavOptions(List<NavOptionModel> navOptionList) {
        if(null != navAdapter){
            navAdapter.setNavOptionsList(navOptionList);
        }
    }
}
