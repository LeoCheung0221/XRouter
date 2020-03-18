package com.tufusi.xroutersample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tufusi.xrouter.XRouter;

/**
 * Created by 鼠夏目 on 2020/3/17.
 *
 * @See
 * @Description
 */
public class TopFragment extends Fragment implements Event.TestMultiReceivers {

    private TextView mMsgTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XRouter.instance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_top, container, false);
        mMsgTv = (TextView)root.findViewById(R.id.top_tv);
        return root;
    }

    @Override
    public void testMulti(String msg) {

    }
}
