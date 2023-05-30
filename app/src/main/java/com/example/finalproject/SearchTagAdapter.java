package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchTagAdapter extends BaseAdapter {

    private Context context;
   // private List<String> list;
    private List<String> jsonname;
    private List<String> jsonintroduction;
    private List<String> jsontime;
    private List<String> jsoncalorie;
    private List<String> jsoncapacity;
    private List<String> jsondifficulty;
    private List<String> jsonimage_link;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    Bitmap bitmap;
    public SearchTagAdapter(List<String> jsonname,List<String> jsonintroduction,List<String> jsontime,List<String> jsoncalorie ,List<String> jsoncapacity,List<String> jsondifficulty,List<String> jsonimage_link, Context context){
        this.jsonname = jsonname;
        this.jsonintroduction = jsonintroduction;
        this.jsontime = jsontime;
        this.jsoncalorie = jsoncalorie;
        this.jsoncapacity = jsoncapacity;
        this.jsondifficulty = jsondifficulty;
        this.jsonimage_link=jsonimage_link;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return jsonname.size();
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
            convertView = inflate.inflate(R.layout.listview,null);
//
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
          viewHolder.introduction = (TextView) convertView.findViewById(R.id.introduction);
          viewHolder.Utime = (TextView) convertView.findViewById(R.id.Utime);
          viewHolder.calorie = (TextView) convertView.findViewById(R.id.calorie);
          viewHolder.capacity = (TextView) convertView.findViewById(R.id.capacity);
          viewHolder.difficulty = (TextView) convertView.findViewById(R.id.difficulty);
          viewHolder.imageView=(ImageView)convertView.findViewById(R.id.Image) ;
//            viewHolder.item_name = (TextView) convertView.findViewById(R.id.search_item_name);
//            viewHolder.item_price = (TextView) convertView.findViewById(R.id.search_item_price);
//            viewHolder.item_image= (ImageView) convertView.findViewById(R.id.search_item_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
//
//        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
         viewHolder.name.setText(jsonname.get(position));
        viewHolder.introduction.setText(jsonintroduction.get(position));
        viewHolder.Utime.setText(jsontime.get(position));
        viewHolder.calorie.setText(jsoncalorie.get(position));
        viewHolder.capacity.setText(jsoncapacity.get(position));
        viewHolder.difficulty.setText(jsondifficulty.get(position));


        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(jsonimage_link.get(position));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        };
        mThread.start();
        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다

                viewHolder.imageView.setImageBitmap(bitmap);



        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
//        viewHolder.item_name.setText(lname.get(position));
//
//        viewHolder.item_price.setText(lprice.get(position));


        return convertView;
    }

    class ViewHolder{
        public TextView name;
        public TextView introduction;
        public TextView Utime;
        public TextView calorie;
        public TextView capacity;
        public TextView difficulty;
        public ImageView imageView;
    }

}
