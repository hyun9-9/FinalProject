package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchTagAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    Bitmap bitmap;
    public SearchTagAdapter(List<String> list , Context context){
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
      if(convertView == null){
            convertView = inflate.inflate(R.layout.listingredient,null);
//
            viewHolder = new ViewHolder();
            viewHolder.jsonname = (TextView) convertView.findViewById(R.id.listingredient);
//            viewHolder.item_name = (TextView) convertView.findViewById(R.id.search_item_name);
//            viewHolder.item_price = (TextView) convertView.findViewById(R.id.search_item_price);
//            viewHolder.item_image= (ImageView) convertView.findViewById(R.id.search_item_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
//
//        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
         viewHolder.jsonname.setText(list.get(position));
//
//        viewHolder.item_name.setText(lname.get(position));
//
//        viewHolder.item_price.setText(lprice.get(position));


        return convertView;
    }

    class ViewHolder{
        public TextView jsonname;

    }

}
