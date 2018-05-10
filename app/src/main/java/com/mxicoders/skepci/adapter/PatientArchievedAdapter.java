package com.mxicoders.skepci.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mxicoders.skepci.Extension;
import com.mxicoders.skepci.R;
import com.mxicoders.skepci.activity.ActivityMenuPatientList;
import com.mxicoders.skepci.activity.EditProfilePatientPsychologistSide;
import com.mxicoders.skepci.network.CommanClass;
import com.mxicoders.skepci.utils.ItemModel;
import com.mxicoders.skepci.utils.ItemModelTwo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mxicoders on 15/7/17.
 */

public class PatientArchievedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    CommanClass cc;

    private List<ItemModelTwo> itemModels;
    private Context context;
    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;

    public PatientArchievedAdapter(Context context, List<ItemModelTwo> wallTalls) {

        cc = new CommanClass(context);

        this.itemModels = wallTalls;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_archieved_item_main, viewGroup, false);
       /* if (viewType == ITEM_TYPE_ACTION_WIDTH)
            return new ItemSwipeWithActionWidthViewHolder(view);*/
       /* if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_archieved_single_delete_item, viewGroup, false);
            return new ItemViewHolderWithRecyclerWidth(view);
        }*/
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ItemModelTwo model = itemModels.get(position);
        initializeViews(model, holder, position);

      /*  if (holder instanceof ItemViewHolderWithRecyclerWidth) {
            ItemViewHolderWithRecyclerWidth viewHolder = (ItemViewHolderWithRecyclerWidth) holder;
            viewHolder.mActionViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDelete(holder.getAdapterPosition());
                }
            });
        } else */if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
            viewHolder.mActionViewRefresh.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.e("folder clicked","............");

                            Intent intent = new Intent(context, ActivityMenuPatientList.class);
                            getPosList(holder.getAdapterPosition());
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    }

            );
        }

    }

    private void doDelete(int adapterPosition) {
        itemModels.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }


    /*@Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
        }
       *//* if (position == 2) {
            return ITEM_TYPE_RECYCLER_WIDTH;
        }*//*
        return ITEM_TYPE_ACTION_WIDTH;
    }
*/


    private void initializeViews(ItemModelTwo model, final RecyclerView.ViewHolder holder, int position) {

        int imageUrl = model.getImagePath();

            Glide.with(context)
                    .load(imageUrl)
                    .into(((ItemViewHolder)holder).imageView);


        ((ItemViewHolder)holder).name.setText(model.getName());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.view_list_repo_action_container)
        public
        View mActionContainer;

        @BindView(R.id.view_list_main_content)
        public
        View mViewContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, ActivityMenuPatientList.class);
            getPosList(getAdapterPosition());
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }

    public void getPosList(int adapterPosition) {

        ItemModelTwo modelTwo = itemModels.get(adapterPosition);

        cc.savePrefString("patient_id_main",modelTwo.getId());
        cc.savePrefString("p_namee",modelTwo.getName());
        cc.savePrefString("p_namee2",modelTwo.getName2());
        cc.savePrefString("p_namee_last",modelTwo.getLname());
        cc.savePrefString("p_dob",modelTwo.getP_birthdate());
        cc.savePrefString("p_email",modelTwo.getEmail());
        cc.savePrefString("p_city",modelTwo.getCity());
        cc.savePrefString("p_gender",modelTwo.getGender());

    }



    class ItemViewHolderWithRecyclerWidth extends ItemViewHolder {

        @BindView(R.id.view_list_repo_action_delete)
        View mActionViewDelete;

        public ItemViewHolderWithRecyclerWidth(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class ItemSwipeWithActionWidthViewHolder extends ItemViewHolder implements Extension {

        @BindView(R.id.view_list_repo_action_delete)
        View mActionViewDelete;
        @BindView(R.id.view_list_repo_action_update)
        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    public class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }
}