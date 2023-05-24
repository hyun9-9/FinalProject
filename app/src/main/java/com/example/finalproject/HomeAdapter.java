package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<String> id;
    private List<String> name;
    private  List<String> introduction;
    private  List<String> time;
    private  List<String> calorie;
    private  List<String>capacity;
    private  List<String>difficulty;
    private  List<String>image_link;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    Bitmap bitmap;

    private GridView gridView;
    public HomeAdapter(List<String> id, List<String> name, List<String> introduction,
                       List<String> time, List<String> calorie, List<String> capacity, List<String> difficulty, List<String> image_link, Context context, GridView gridView){
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.time=time;
        this.calorie=calorie;
        this.capacity=capacity;
        this.difficulty=difficulty;
        this.image_link=image_link;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.gridView=gridView;
        for(int i=0;i<id.size();i++) Log.e("id",id.get(i));
    }
    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        if(position==0) {
//            gridView.setNumColumns(1);
//
//        }
//        else{
//        gridView.setNumColumns(2);
//        }
        if(convertView == null){
            convertView = inflate.inflate(R.layout.listview,null);

            viewHolder = new ViewHolder();
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.item_introduction = (TextView) convertView.findViewById(R.id.introduction);
            viewHolder.item_time = (TextView) convertView.findViewById(R.id.Utime);
            viewHolder.item_calorie = (TextView) convertView.findViewById(R.id.calorie);
            viewHolder.item_capacity = (TextView) convertView.findViewById(R.id.capacity);
            viewHolder.item_difficulty = (TextView) convertView.findViewById(R.id.difficulty);

            viewHolder.item_image= (ImageView) convertView.findViewById(R.id.Image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


//            if(convertView == null){
//                convertView = inflate.inflate(R.layout.bestlistview,null);
//
//                viewHolder = new ViewHolder();
//                viewHolder.item_name = (TextView) convertView.findViewById(R.id.name);
//                viewHolder.item_introduction = (TextView) convertView.findViewById(R.id.introduction);
//                viewHolder.item_time = (TextView) convertView.findViewById(R.id.Utime);
//                viewHolder.item_calorie = (TextView) convertView.findViewById(R.id.calorie);
//                viewHolder.item_capacity = (TextView) convertView.findViewById(R.id.capacity);
//                viewHolder.item_difficulty = (TextView) convertView.findViewById(R.id.difficulty);
//
//                viewHolder.item_image= (ImageView) convertView.findViewById(R.id.Image);
//                convertView.setTag(viewHolder);
//            }else{
//                viewHolder = (ViewHolder)convertView.getTag();
//            }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        Log.e("item",id.get(position));
        Log.e("position", String.valueOf(position));
        viewHolder.item_name.setText(name.get(position));
        viewHolder.item_introduction.setText(introduction.get(position));
        viewHolder.item_time.setText(time.get(position));
        viewHolder.item_calorie.setText(calorie.get(position));
        viewHolder.item_capacity.setText(capacity.get(position));
        viewHolder.item_difficulty.setText(difficulty.get(position));

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String l=image_link.get(position);

                    URL url = new URL(l);

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
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

        mThread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            viewHolder.item_image.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder{

        public TextView item_name;
        public TextView item_introduction;

        public TextView item_time;
        public TextView item_calorie;

        public TextView item_capacity;
        public TextView item_difficulty;
        public ImageView item_image;
    }

}