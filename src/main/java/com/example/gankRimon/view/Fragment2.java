package com.example.gankRimon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gankRimon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rimon on 2016/5/24.
 */
public class Fragment2 extends Fragment {

    @BindView(R.id.button)
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        ButterKnife.bind(this, view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "你点击了第2个Fragment中的按钮", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
