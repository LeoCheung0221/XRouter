package com.tufusi.xroutersample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by 鼠夏目 on 2020/3/17.
 *
 * @See
 * @Description
 */
public class BottomFragment extends TopFragment {

    private TextView mMsgTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_bottom, container, false);
        mMsgTv = (TextView) root.findViewById(R.id.bottom_tv);
        return root;
    }

    @Override
    public void testMulti(String msg) {
        mMsgTv.setText("Support inherit , receive the msg :" + msg);
    }
}
