package com.example.gankRimon.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gankRimon.R;
import com.example.gankRimon.adapter.PrettyGirlAdapter;
import com.example.gankRimon.model.Gank;
import com.example.gankRimon.model.Result;
import com.example.gankRimon.retrofit.GankRetrofit;
import com.example.gankRimon.retrofit.GankService;

import java.util.ArrayList;
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
    @BindView(R.id.prettyGirlView)
    RecyclerView prettyGirlView;
    @BindView(R.id.prettyGirlRefresh)
    SwipeRefreshLayout prettyGirlRefresh;

    private PrettyGirlAdapter prettyGirlAdapter;
    private List<Result> resultList;
    int COUNT=20;
    private int DEFAULT_PAGE=1;
    private int page=DEFAULT_PAGE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prettygirl, container, false);
        ButterKnife.bind(this, view);

        prettyGirlRefresh.setColorSchemeColors(R.color.red);
        getDatas("福利", COUNT, DEFAULT_PAGE);

        Log.e("444", Thread.currentThread().getName() + "Log");


        prettyGirlAdapter = new PrettyGirlAdapter(getActivity(), new PrettyGirlAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v,  int position) {
                //TODO:接受来自PrettyGirlAdapter的resultList。
                resultList=prettyGirlAdapter.getResultList();
                Log.e("result是否空",String.valueOf(resultList==null));
                Intent intent = new Intent(getActivity(), PrettyGirlShow.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("resultList", (ArrayList<? extends Parcelable>) resultList);
                startActivity(intent);
            }

        });
        prettyGirlView.setAdapter(prettyGirlAdapter);
        final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        prettyGirlView.setLayoutManager(staggeredGridLayoutManager);

        prettyGirlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.e("清空前",String.valueOf(resultList.isEmpty()));
//                resultList.clear();
                //Log.e("清空后",String.valueOf(resultList.isEmpty()));
                page=DEFAULT_PAGE;
                getDatas("福利",COUNT, DEFAULT_PAGE);
            }
        });
        prettyGirlView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                CardView cardView= (CardView) getActivity().findViewById(R.id.cardView);
                RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) cardView.getLayoutParams();
                View lastView=recyclerView.getChildAt(recyclerView.getChildCount()-1);
                int last=recyclerView.getChildAdapterPosition(lastView);
                if ((last==prettyGirlAdapter.getItemCount()-1) && dy>0 && (lastView.getBottom()==prettyGirlRefresh.getBottom()
                        -(prettyGirlView.getPaddingBottom()+layoutParams.bottomMargin))){
                    page++;
                    Log.e("页数",String.valueOf(page));
                    getDatas("福利",COUNT, page);
                    Log.e("底部刷新","prettyGirlView.getPaddingBottom()"+"+"+"layoutParams.bottomMargin");
                }
                Log.e("last",String.valueOf(last)+"+"+String.valueOf(resultList.size()-1)+"+"+String.valueOf(prettyGirlAdapter.getItemCount()));
                Log.e("最后位置对比",String.valueOf(last==resultList.size()-1)+"+"+String.valueOf(prettyGirlRefresh
                        .getBottom()
                        -lastView.getBottom())+"+"+String
                        .valueOf
                        (layoutParams.bottomMargin)+"+"+String.valueOf(dy)+"+"+String.valueOf(page));
            }
        });
        return view;
    }


    protected void getDatas(String type, int count, final int page) {
        Log.e("getDatas",type);
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGank(type, count, page)//下面两行代码适用于多数的 后台线程取数据，主线程显示』的程序策略
                .subscribeOn(Schedulers.io())//指定 subscribe() 发生在 IO 线程（创建的事件的内容 1、2、3、4 将会在 IO 线程发出Schedulers
                // .io()    ）
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onNext(Gank gank) {
                        //刷新后resultView数据清零
                        prettyGirlRefresh.setRefreshing(true);
                        if (page==DEFAULT_PAGE){
                            prettyGirlAdapter.clear();
                        }
                        Log.e("检查","true");
                        resultList = gank.getResults();
                        for (Result result : resultList) {
                            prettyGirlAdapter.addData(result);
                        }


                        Log.e("111", Thread.currentThread().getName() + "onNext");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("222", Thread.currentThread().getName() + "onCompleted");
                        prettyGirlRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("333", "onError");
                        prettyGirlRefresh.setVisibility(View.GONE);
                        noNetwork.setVisibility(View.VISIBLE);
                        Snackbar.make(prettyGirlView, "NO WIFI，不能愉快的看妹纸啦..", Snackbar.LENGTH_LONG).show();
                        noNetwork.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getDatas("福利", COUNT, DEFAULT_PAGE);
                                noNetwork.setVisibility(View.GONE);
                            }
                        });
                    }

                });

    }
}
