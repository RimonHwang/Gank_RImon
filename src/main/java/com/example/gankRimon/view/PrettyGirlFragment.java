package com.example.gankRimon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gankRimon.R;
import com.example.gankRimon.adapter.PrettyGirlAdapter;
import com.example.gankRimon.model.Gank;
import com.example.gankRimon.retrofit.GankRetrofit;
import com.example.gankRimon.retrofit.GankService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rimon on 2016/5/24.
 */
public class PrettyGirlFragment extends Fragment {
    @BindView(R.id.no_network)
    LinearLayout noNetwork;
    @BindView(R.id.testView)
    ImageView imageView;
    @BindView(R.id.prettyGirlView)
    RecyclerView prettyGirlView;

//    private String url="http://ww4.sinaimg.cn/large/610dc034jw1f41lxgc3x3j20jh0tcn14.jpg";
    PrettyGirlAdapter prettyGirlAdapter;
    private Gank.Result result;
    private List<Gank.Result> resultList;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prettygirl, container, false);
        ButterKnife.bind(this, view);


        getDatas("福利",50,1);

        Log.e("444", Thread.currentThread().getName()+"Log");


        prettyGirlAdapter = new PrettyGirlAdapter(getActivity(), new PrettyGirlAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getActivity(),PrettyGirlShow.class);
                intent.putExtra("url",resultList.get(position).getUrl());
                startActivity(intent);
            }
        });
        prettyGirlView.setAdapter(prettyGirlAdapter);
        prettyGirlView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        prettyGirlView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }


    private void getDatas(String type, int count, int page) {
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGank(type, count, page)//下面两行代码适用于多数的 后台线程取数据，主线程显示』的程序策略
                .subscribeOn(Schedulers.newThread())//指定 subscribe() 发生在 IO 线程（创建的事件的内容 1、2、3、4 将会在 IO 线程发出Schedulers.io()    ）
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onNext(Gank gank) {
                        resultList=gank.getResults();
                        for (Gank.Result result:resultList){
                            prettyGirlAdapter.addData(result);
                        }

                        Log.e("111", Thread.currentThread().getName()+"onNext");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("222", Thread.currentThread().getName()+"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("333", "onError");
                        noNetwork.setVisibility(View.VISIBLE);
                        Snackbar.make(prettyGirlView, "NO WIFI，不能愉快的看妹纸啦..", Snackbar.LENGTH_LONG).show();
                    }

                });
    }



}
