package com.manish.gharkibaat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manish.gharkibaat.Model.NavOptionModel;
import com.manish.gharkibaat.R;
import com.manish.gharkibaat.Utility.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**********************************
 * Created by Manish on 17-Oct-18
 ***********************************/
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private List<NavOptionModel> navOptionList;
    private Context context;
    private NavItemClickListener navItemClickListener;

    public NavAdapter(Context context, NavItemClickListener navItemClickListener) {
        this.context = context;
        this.navItemClickListener = navItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_nav_drawer_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        NavOptionModel navOption = this.navOptionList.get(pos);

        viewHolder.drawerItemText.setText(navOption.getTitle());
        viewHolder.drawerItemText.setTextColor(context.getResources().getColor(R.color.black));

        viewHolder.onClick(navOption, navItemClickListener);
    }

    @Override
    public int getItemCount() {
        return navOptionList.size();
    }

    public void setNavOptionsList(List<NavOptionModel> navOptionList){
        this.navOptionList = navOptionList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.drawer_item_tv)
        TextView drawerItemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onClick(final NavOptionModel navOption, final NavItemClickListener navItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navItemClickListener.onNavItemClick(navOption);
                }
            });
        }
    }

    public interface NavItemClickListener{
        void onNavItemClick(NavOptionModel navOption);
    }
}
