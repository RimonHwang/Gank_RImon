package com.example.gankRimon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gankRimon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rimon on 2016/5/24.
 */
public class Fragment3 extends Fragment {


    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.textView3)
    TextView textView3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        ButterKnife.bind(this, view);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView3.setText(editText.getText());
            }
        });

        return view;
    }
}
