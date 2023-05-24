package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public List<String> l_id;
    private List<String> name;
    private  List<String> introduction;
    private  List<String> time;
    private  List<String> calorie;
    private  List<String>capacity;
    private  List<String>difficulty;
    private  List<String>image_link;

    private ArrayList<String> arraylistid;
    private ArrayList<String> arraylistname;
    private ArrayList<String> arraylistintroduction;
    private ArrayList<String> arraylisttime;
    private ArrayList<String> arraylistcalorie;
    private ArrayList<String> arraylistcapacity;
    private ArrayList<String> arraylistdifficulty;
    private ArrayList<String> arraylistimage_link;
    Bitmap bitmap;
    ImageView bestImage;
    TextView bestname,bestintroduction,besttime,bestcalorie,bestcapacity,bestdifficulty;
    public TextView item_name,item_name2,item_name3,item_name4;
    public TextView item_introduction,item_introduction2,item_introduction3,item_introduction4;

    public TextView item_time,item_time2,item_time3,item_time4;
    public TextView item_calorie,item_calorie2,item_calorie3,item_calorie4;

    public TextView item_capacity,item_capacity2,item_capacity3,item_capacity4;
    public TextView item_difficulty,item_difficulty2,item_difficulty3,item_difficulty4;
    public ImageView item_image,item_image2,item_image3,item_image4;

    private HomeAdapter adapter;
    private GridView gridView;


    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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


        l_id = new ArrayList<String>();
        name = new ArrayList<String>();
        introduction = new ArrayList<String>();
        time = new ArrayList<String>();
        calorie=new ArrayList<String>();
        capacity=new ArrayList<String>();
        difficulty=new ArrayList<String>();
        image_link=new ArrayList<String>();

        arraylistid= new ArrayList<String>();
       arraylistname= new ArrayList<String>();
        arraylistintroduction= new ArrayList<String>();
        arraylisttime= new ArrayList<String>();
         arraylistcalorie= new ArrayList<String>();
        arraylistcapacity= new ArrayList<String>();
        arraylistdifficulty= new ArrayList<String>();
        arraylistimage_link=new ArrayList<>();

        final View best = frag_view.findViewById(R.id.best);

        final View list1 = frag_view.findViewById(R.id.list1);
        final View list2 = frag_view.findViewById(R.id.list2);
        final View list3 = frag_view.findViewById(R.id.list3);
        final View list4 = frag_view.findViewById(R.id.list4);

        bestImage=best.findViewById(R.id.bestImage);
        bestname=best.findViewById(R.id.bestname);
        bestintroduction=best.findViewById(R.id.bestintroduction);
        besttime=best.findViewById(R.id.besttime);
        bestcalorie=best.findViewById(R.id.bestcalorie);
        bestcapacity=best.findViewById(R.id.bestcapacity);
        bestdifficulty=best.findViewById(R.id.bestdifficulty);

        item_name = (TextView) list1.findViewById(R.id.name);
        item_introduction = (TextView) list1.findViewById(R.id.introduction);
        item_time = (TextView) list1.findViewById(R.id.Utime);
        item_calorie = (TextView) list1.findViewById(R.id.calorie);
        item_capacity = (TextView) list1.findViewById(R.id.capacity);
        item_difficulty = (TextView) list1.findViewById(R.id.difficulty);
        item_image= (ImageView) list1.findViewById(R.id.Image);

        item_name2 = (TextView) list2.findViewById(R.id.name);
        item_introduction2 = (TextView) list2.findViewById(R.id.introduction);
        item_time2 = (TextView) list2.findViewById(R.id.Utime);
        item_calorie2 = (TextView) list2.findViewById(R.id.calorie);
        item_capacity2 = (TextView) list2.findViewById(R.id.capacity);
        item_difficulty2 = (TextView) list2.findViewById(R.id.difficulty);
        item_image2= (ImageView) list2.findViewById(R.id.Image);

        item_name3 = (TextView) list3.findViewById(R.id.name);
        item_introduction3 = (TextView) list3.findViewById(R.id.introduction);
        item_time3 = (TextView) list3.findViewById(R.id.Utime);
        item_calorie3 = (TextView) list3.findViewById(R.id.calorie);
        item_capacity3 = (TextView) list3.findViewById(R.id.capacity);
        item_difficulty3 = (TextView) list3.findViewById(R.id.difficulty);
        item_image3= (ImageView) list3.findViewById(R.id.Image);

        item_name4 = (TextView) list4.findViewById(R.id.name);
        item_introduction4 = (TextView) list4.findViewById(R.id.introduction);
        item_time4 = (TextView) list4.findViewById(R.id.Utime);
        item_calorie4 = (TextView) list4.findViewById(R.id.calorie);
        item_capacity4 = (TextView) list4.findViewById(R.id.capacity);
        item_difficulty4 = (TextView) list4.findViewById(R.id.difficulty);
        item_image4= (ImageView) list4.findViewById(R.id.Image);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    Log.e("Test123",jsonObject.toString());
                    JSONArray data_json=jsonObject.getJSONArray("data");
                    for(int i=0;i<data_json.length();i++){
                        JSONObject jsonObject1=data_json.getJSONObject(i);
                        l_id.add(jsonObject1.getString("id"));
                        name.add(jsonObject1.getString("name"));
                        introduction.add(jsonObject1.getString("introduction"));
                        time.add(jsonObject1.getString("time"));
                        calorie.add(jsonObject1.getString("calorie"));
                        capacity.add(jsonObject1.getString("capacity"));
                        difficulty.add(jsonObject1.getString("difficulty"));
                        image_link.add(jsonObject1.getString("image_link"));

                        Log.e("name",jsonObject1.getString("id"));
                    }
                    arraylistid.add(l_id.get(0));
                    arraylistname.add(name.get(0));
                    arraylistintroduction.add(introduction.get(0));
                    arraylisttime.add(time.get(0));
                    arraylistcalorie.add(calorie.get(0));
                    arraylistcapacity.add(capacity.get(0));
                    arraylistdifficulty.add(difficulty.get(0));
                    arraylistimage_link.add(image_link.get(0));

                    for(int i=0;i<l_id.size();i++) {
                        if (i == 0) {
                            bestname.setText(name.get(i));
                            bestintroduction.setText(introduction.get(i));
                            besttime.setText(time.get(i));
                            bestcalorie.setText(calorie.get(i));
                            bestcapacity.setText(capacity.get(i));
                            bestdifficulty.setText(difficulty.get(i));
                        }
                        if (i == 1) {
                            item_name.setText(name.get(i));
                            item_introduction.setText(introduction.get(i));
                            item_time.setText(time.get(i));
                            item_calorie.setText(calorie.get(i));
                            item_capacity.setText(capacity.get(i));
                            item_difficulty.setText(difficulty.get(i));
                        }
                        if (i == 2) {
                            item_name2.setText(name.get(i));
                            item_introduction2.setText(introduction.get(i));
                            item_time2.setText(time.get(i));
                            item_calorie2.setText(calorie.get(i));
                            item_capacity2.setText(capacity.get(i));
                            item_difficulty2.setText(difficulty.get(i));
                        }
                        if (i == 3) {
                            item_name3.setText(name.get(i));
                            item_introduction3.setText(introduction.get(i));
                            item_time3.setText(time.get(i));
                            item_calorie3.setText(calorie.get(i));
                            item_capacity3.setText(capacity.get(i));
                            item_difficulty3.setText(difficulty.get(i));
                        }
                        if (i == 4) {
                            item_name4.setText(name.get(i));
                            item_introduction4.setText(introduction.get(i));
                            item_time4.setText(time.get(i));
                            item_calorie4.setText(calorie.get(i));
                            item_capacity4.setText(capacity.get(i));
                            item_difficulty4.setText(difficulty.get(i));
                        }
                        int finalI = i;
                        Thread mThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL(image_link.get(finalI));
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
                            }
                            if(i==1) {
                                item_image.setImageBitmap(bitmap);
                            }
                            if(i==2) {
                                item_image2.setImageBitmap(bitmap);
                            }
                            if(i==3) {
                                item_image3.setImageBitmap(bitmap);
                            }
                            if(i==4) {
                                item_image4.setImageBitmap(bitmap);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    gridView.setNumColumns(1);
//                    adapter = new HomeAdapter(arraylistid,arraylistname,arraylistintroduction,arraylisttime,arraylistcalorie,arraylistcapacity,arraylistdifficulty,arraylistimage_link,container.getContext() );
//                    gridView.setAdapter(adapter);


//                    l_id.remove(0);
//                    name.remove(0);
//                    introduction.remove(0);
//                    time.remove(0);
//                    calorie.remove(0);
//                    capacity.remove(0);
//                    difficulty.remove(0);
//                    image_link.remove(0);

                    //gridView.setNumColumns(2);
                    adapter = new HomeAdapter(l_id,name,introduction,time,calorie,capacity,difficulty,image_link,container.getContext(),gridView);
                    // 리스트뷰에 아답터를 연결한다.
                   // gridView.setAdapter(adapter);
                   // gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //    @Override
                     //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //        Log.d(TAG, "여기야 : "+l_id.get(position));
                    //        Intent intent =new Intent(getActivity(),MainActivity.class);
                    //        intent.putExtra("id",l_id.get(position));
                    //        startActivity(intent);
                    //    }
                  //  });
//                    JSONObject User_json=data_json.getJSONObject("user");
//                    JSONObject Token_json=data_json.getJSONObject("token");
//
//                    String Email=User_json.getString("email");
//
//
//                    if(UserEmail.equals(Email)) {//로그인 성공시
//
//                        String access_token=Token_json.getString("access_token");
//                        String refresh_token=Token_json.getString("refresh_token");
//                        String User_mobile = User_json.getString( "mobile" );
//                        String UserName = User_json.getString( "name" );
//
//                        //Toast.makeText( frag_view.getContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT ).show();
//
//
//                    } else {//로그인 실패시
//                        //Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
//                       return;
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err","json err");
                }
            }
        };


        HomeRequest homeRequest = new HomeRequest(access_token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(frag_view.getContext());
        queue.add(homeRequest);


        return frag_view;
    }
}