package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    Bitmap bitmap;
    ImageView bestImage;
    TextView bestname,bestintroduction,besttime,bestcalorie,bestcapacity,bestdifficulty;
    public TextView[] item_name;
    public TextView[] item_introduction;

    public TextView[] item_time;
    public TextView[] item_calorie;

    public TextView[] item_capacity;
    public TextView[] item_difficulty;
    public ImageView[] item_image;

    public HomeFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment1 newInstance(String param1, String param2) {
        HomeFragment1 fragment = new HomeFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag_view = inflater.inflate(R.layout.fragment_home,container,false);
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        String access_token = bundle.getString("access_token");
       // gridView = (GridView) frag_view.findViewById(R.id.gridView);

        Integer listview[]={R.id.list1,R.id.list2,R.id.list3,R.id.list4};
        View list[]=new View[4];
        item_name=new TextView[4];
        item_introduction=new TextView[4];
        item_time=new TextView[4];
        item_calorie=new TextView[4];
        item_capacity=new TextView[4];
        item_difficulty=new TextView[4];
        item_image=new ImageView[4];
        for(int i=0;i<listview.length;i++){
            list[i]=frag_view.findViewById(listview[i]);
            item_name[i]=(TextView) list[i].findViewById(R.id.name);
            item_introduction[i] = (TextView) list[i].findViewById(R.id.introduction);
            item_time[i] = (TextView) list[i].findViewById(R.id.Utime);
            item_calorie[i] = (TextView) list[i].findViewById(R.id.calorie);
            item_capacity[i] = (TextView) list[i].findViewById(R.id.capacity);
            item_difficulty[i] = (TextView) list[i].findViewById(R.id.difficulty);
            item_image[i]= (ImageView) list[i].findViewById(R.id.Image);
        }

        final View best = frag_view.findViewById(R.id.best);
        bestImage=best.findViewById(R.id.bestImage);
        bestname=best.findViewById(R.id.bestname);
        bestintroduction=best.findViewById(R.id.bestintroduction);
//        besttime=best.findViewById(R.id.besttime);
//        bestcalorie=best.findViewById(R.id.bestcalorie);
//        bestcapacity=best.findViewById(R.id.bestcapacity);
//        bestdifficulty=best.findViewById(R.id.bestdifficulty);

        ProductRepository dao = ProductRepository.getInstance();
        ArrayList<Product> listOfProducts = dao.getAllProducts();
        for (int i = 0; i < listOfProducts.size(); i++) {
            Product product = listOfProducts.get(i);
            if (i == 0) {
                bestname.setText(product.getPname());
                bestintroduction.setText(product.getIntroduction());
//                besttime.setText(product.gettime());
//                bestcalorie.setText(product.getcalorie());
//                bestcapacity.setText(product.getcapacity());
//                bestdifficulty.setText(product.getdifficulty());
            }else{
                item_name[i-1].setText(product.getPname());
                item_introduction[i-1].setText(product.getIntroduction());
                item_time[i-1].setText(product.gettime());
                item_calorie[i-1].setText(product.getcalorie());
                item_capacity[i-1].setText(product.getcapacity());
                item_difficulty[i-1].setText(product.getdifficulty());
            }

            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(product.getimage_link());
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
                if(i==0) {
                    bestImage.setImageBitmap(bitmap);
//                    bestImage.clipToOutline = true;
                }
                else {
                    item_image[i-1].setImageBitmap(bitmap);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return frag_view;
    }
}