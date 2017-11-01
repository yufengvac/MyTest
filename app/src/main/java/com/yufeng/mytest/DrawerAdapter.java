package com.yufeng.mytest;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * 抽屉adapter
 * Created by zly on 2016/3/30.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_HEADER = 2;

    private Context mContext ;
    private ArrayList<DrawerItem> dataList = new ArrayList<>();


    public static class ItemIds{
        public static final int HOME = 1;
        public static final int RANK = 2;
        public static final int LANMU = 3;
        public static final int SEARCH = 4;
        public static final int SETTING = 5;
        public static final int MODE = 7;
        public static final int OFFLINE = 8;
    }
    public DrawerAdapter(Context context){
        this.mContext = context;
       initData();
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

    private void initData(){
        dataList.clear();
        dataList.add(new DrawerItemHeader());
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_camera, R.string.drawer_menu_home, ContextCompat.getColor(mContext , R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_gallery, R.string.drawer_menu_rank,ContextCompat.getColor(mContext , R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_manage, R.string.drawer_menu_column,ContextCompat.getColor(mContext , R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_send, R.string.drawer_menu_search,ContextCompat.getColor(mContext , R.color.black)));
        dataList.add(new DrawerItemNormal(R.drawable.ic_menu_share, R.string.drawer_menu_setting,ContextCompat.getColor(mContext , R.color.black)));
        dataList.add(new DrawerItemDivider());
        dataList.add(new DrawerItemNormal(R.string.drawer_menu_night,ContextCompat.getColor(mContext , R.color.grey)));
        dataList.add(new DrawerItemNormal(R.string.drawer_menu_offline,ContextCompat.getColor(mContext , R.color.grey)));
    }


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

    public void setCurSelected(int id){
        initData();
        DrawerItem drawerItem = dataList.get(id);
        if (drawerItem instanceof  DrawerItemNormal){
            ((DrawerItemNormal)drawerItem).color = ContextCompat.getColor(mContext , R.color.green);
        }
        notifyDataSetChanged();
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
            final NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
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
                        listener.itemClick(normalViewHolder.getLayoutPosition());
                    }
                }
            });
        }
    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }




    //-------------------------item数据模型------------------------------
    // drawerlayout item统一的数据模型
    private interface DrawerItem {
    }


    //有图片和文字的item
    private class DrawerItemNormal implements DrawerItem {
        private int iconRes;
        private int titleRes;
        private int color;

        private DrawerItemNormal(int iconRes, int titleRes , int color) {
            this.iconRes = iconRes;
            this.titleRes = titleRes;
            this.color = color;
        }
        private DrawerItemNormal(int titleRes, int color){
            this.iconRes = -1;
            this.titleRes = titleRes;
            this.color = color;
        }

    }

    //分割线item
    private class DrawerItemDivider implements DrawerItem {
        private DrawerItemDivider() {
        }
    }

    //头部item
    private class DrawerItemHeader implements DrawerItem{
        private DrawerItemHeader() {
        }
    }



    //----------------------------------ViewHolder数据模型---------------------------
    //抽屉ViewHolder模型
     class DrawerViewHolder extends RecyclerView.ViewHolder {

        private DrawerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //有图标有文字ViewHolder
    private class NormalViewHolder extends DrawerViewHolder {
        private View view;
        private TextView tv;
        private ImageView iv;

        private NormalViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    //分割线ViewHolder
    private class DividerViewHolder extends DrawerViewHolder {

        private DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //头部ViewHolder
    private class HeaderViewHolder extends DrawerViewHolder {

        private ImageView sdv_icon;
        private TextView tv_login;

        private HeaderViewHolder(View itemView) {
            super(itemView);
            sdv_icon = (ImageView) itemView.findViewById(R.id.imageView);
            tv_login = (TextView) itemView.findViewById(R.id.name);
        }
    }
}