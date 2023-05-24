package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment{
//    SettingList settingList;
    private List<String> list;          // 데이터를 넣은 리스트변수
    Context context ;


    //private ListView listView;          // 검색을 보여줄 리스트변수

    private GridView gridView;
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

   private ArrayList<String> arraylist;

    private SearchView searchView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag_view = inflater.inflate(R.layout.fragment_search,container,false);
        //editSearch = (EditText) frag_view.findViewById(R.id.editSearch);
        //listView = (ListView) frag_view.findViewById(R.id.listView);
        Bundle bundle = getArguments();
        String access_token = bundle.getString("access_token");
        list = new ArrayList<String>();

        gridView = (GridView) frag_view.findViewById(R.id.gridView);
        searchView=frag_view.findViewById(R.id.search);
        context=container.getContext();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );

                    Log.e("Test123",jsonObject.toString());
                    JSONArray data_json=jsonObject.getJSONArray("data");
                    Log.e("Test123",data_json.toString());
                    for(int i=0;i<data_json.length();i++){
                        // jsonObject1=data_json.getJSONObject(i);
                        //Log.e("Test123",jsonObject1.toString());
                        list.add(data_json.getString(i));
                        Log.e("name",data_json.getString(i));
                    }

                    //settingList();
                    arraylist = new ArrayList<String>();
                    arraylist.addAll(list);
                    adapter = new SearchAdapter(list,container.getContext());
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Log.d(TAG, "여기야 : "+l_id.get(position));
//                                Intent intent =new Intent(getActivity(),ProductActivity.class);
//                                intent.putExtra("id",l_id.get(position));
//                                startActivity(intent);
                        }
                    });

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {


                            Log.d(TAG,"rrrrrrrrrrrrrrrrrrrrrr");
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            search(newText);
                            return false;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SearchRequest searchRequest = new SearchRequest( access_token, responseListener );
        RequestQueue queue = Volley.newRequestQueue( frag_view.getContext() );
        queue.add( searchRequest );


        return frag_view;


    }
    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
//            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
//                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
//                    // 검색된 데이터를 리스트에 추가한다.

                    list.add(arraylist.get(i));
                }

            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        //settingList = new SettingList(context);


        //list = settingList.getlist();
//        l_brand.addAll(list.get(0));
//        l_name.addAll(list.get(1));
//        l_price.addAll(list.get(2));
//        l_image.addAll(list.get(3));




       // l_id.addAll(settingList.l_id);
       // l_brand.addAll(settingList.l_brand);
       // l_name.addAll(settingList.l_name);
      //  l_price.addAll(settingList.l_price);
       //  l_image.addAll(settingList.l_image);

    }
}