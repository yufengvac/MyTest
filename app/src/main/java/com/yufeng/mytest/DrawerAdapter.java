package com.yufeng.mytest;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽屉adapter
 * Created by zly on 2016/3/30.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_HEADER = 2;

    private int curSelected = -1;
    private Context mContext ;
    private ArrayList<DrawerItem> dataList = new ArrayList<>();

    public DrawerAdapter(Context context){
        this.mContext = context;
        dataList.add(new DrawerItemHeader());
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_camera, R.string.drawer_menu_home, mContext.getResources().getColor(R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_gallery, R.string.drawer_menu_rank,mContext.getResources().getColor(R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_manage, R.string.drawer_menu_column,mContext.getResources().getColor(R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_send, R.string.drawer_menu_search,mContext.getResources().getColor(R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_share, R.string.drawer_menu_setting,mContext.getResources().getColor(R.color.black)));
        dataList.add(new DrawerItemDivider());
        dataList.add(new DrawerItemNormal(R.string.drawer_menu_night,mContext.getResources().getColor(R.color.grey)));
        dataList.add(new DrawerItemNormal(R.string.drawer_menu_offline,mContext.getResources().getColor(R.color.grey)));
    }

//    private List<DrawerItem> dataList = Arrays.asList(
//            new DrawerItemHeader(),
//            new DrawerItemNormal(R.drawable.ic_menu_camera, R.string.drawer_menu_home, mContext.getResources().getColor(R.color.black)),
//            new DrawerItemNormal(R.drawable.ic_menu_gallery, R.string.drawer_menu_rank,mContext.getResources().getColor(R.color.black)),
//            new DrawerItemNormal(R.drawable.ic_menu_manage, R.string.drawer_menu_column,mContext.getResources().getColor(R.color.black)),
//            new DrawerItemNormal(R.drawable.ic_menu_send, R.string.drawer_menu_search,mContext.getResources().getColor(R.color.black)),
//            new DrawerItemNormal(R.drawable.ic_menu_share, R.string.drawer_menu_setting,mContext.getResources().getColor(R.color.black)),
//            new DrawerItemDivider(),
//            new DrawerItemNormal(R.string.drawer_menu_night,mContext.getResources().getColor(R.color.grey)),
//            new DrawerItemNormal(R.string.drawer_menu_offline,mContext.getResources().getColor(R.color.grey))
//    );


    @Override
    public int getItemViewType(int position) {
        DrawerItem drawerItem = dataList.get(position);
        if (drawerItem instanceof DrawerItemDivider) {
            return TYPE_DIVIDER;
        } else if (drawerItem instanceof DrawerItemNormal) {
            return TYPE_NORMAL;
        }else if(drawerItem instanceof DrawerItemHeader){
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DrawerViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DIVIDER:
                viewHolder = new DividerViewHolder(inflater.inflate(R.layout.item_drawer_divider, parent, false));
                break;
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.nav_header_main, parent, false));
                break;
            case TYPE_NORMAL:
                viewHolder = new NormalViewHolder(inflater.inflate(R.layout.item_drawer_normal, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        final DrawerItem item = dataList.get(position);
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            final DrawerItemNormal itemNormal = (DrawerItemNormal) item;
            if(itemNormal.iconRes == -1){
                normalViewHolder.iv.setVisibility(View.GONE);
            }else {
                normalViewHolder.iv.setVisibility(View.VISIBLE);
                normalViewHolder.iv.setBackgroundResource(itemNormal.iconRes);
            }

            normalViewHolder.tv.setText(itemNormal.titleRes);
            if (itemNormal.color != -1){
                normalViewHolder.tv.setTextColor(itemNormal.color);
            }

            normalViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.itemClick(itemNormal);

                    }
                }
            });
        }else if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        }

    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void itemClick(DrawerItemNormal drawerItemNormal);
    }




    //-------------------------item数据模型------------------------------
    // drawerlayout item统一的数据模型
    public interface DrawerItem {
    }


    //有图片和文字的item
    public class DrawerItemNormal implements DrawerItem {
        public int iconRes;
        public int titleRes;
        private int color;

        public DrawerItemNormal(int iconRes, int titleRes , int color) {
            this.iconRes = iconRes;
            this.titleRes = titleRes;
            this.color = color;
        }
        public DrawerItemNormal(int titleRes, int color){
            this.iconRes = -1;
            this.titleRes = titleRes;
            this.color = color;
        }

    }

    //分割线item
    public class DrawerItemDivider implements DrawerItem {
        public DrawerItemDivider() {
        }
    }

    //头部item
    public class DrawerItemHeader implements DrawerItem{
        public DrawerItemHeader() {
        }
    }



    //----------------------------------ViewHolder数据模型---------------------------
    //抽屉ViewHolder模型
    public class DrawerViewHolder extends RecyclerView.ViewHolder {

        public DrawerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //有图标有文字ViewHolder
    public class NormalViewHolder extends DrawerViewHolder {
        public View view;
        public TextView tv;
        public ImageView iv;

        public NormalViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    //分割线ViewHolder
    public class DividerViewHolder extends DrawerViewHolder {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //头部ViewHolder
    public class HeaderViewHolder extends DrawerViewHolder {

        private ImageView sdv_icon;
        private TextView tv_login;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            sdv_icon = (ImageView) itemView.findViewById(R.id.imageView);
            tv_login = (TextView) itemView.findViewById(R.id.name);
        }
    }
}