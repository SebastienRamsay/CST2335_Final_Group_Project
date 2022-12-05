package com.example.cst2335finalgroupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335finalgroupproject.databinding.WatchmatchlayoutBinding;

public class SoccerwatchFragment extends Fragment {
    WatchmatchlayoutBinding binding;
    ListJSON._Embedded.Title title;
    WebView wv;
    String url;

    public SoccerwatchFragment(ListJSON._Embedded.Title titles){
        this.title = titles;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = WatchmatchlayoutBinding.inflate(inflater);

        url = title.getUrl().toString();
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl(url);

        return binding.getRoot();
    }


}
