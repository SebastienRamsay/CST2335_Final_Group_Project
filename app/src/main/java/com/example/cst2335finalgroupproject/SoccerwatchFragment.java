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

/**
 * This class creates the view where users can watch the game highlights of their selected matches
 * @see android.content.ComponentCallbacks
 * @see android.view.View.OnCreateContextMenuListener
 * @see androidx.fragment.app.Fragment
 * @see androidx.activity.result.ActivityResultCaller
 * @see androidx.lifecycle.HasDefaultViewModelProviderFactory
 * @see androidx.lifecycle.LifecycleOwner
 * @see androidx.lifecycle.ViewModelStoreOwner
 * @see androidx.savedstate.SavedStateRegistryOwner
 */
public class SoccerwatchFragment extends Fragment {
    WatchmatchlayoutBinding binding;
    ListJSON._Embedded.Title title;
    WebView wv;
    String url;

    /**
     * Constructor that passes the match name to the url which
     * directs the user to the game highlights webpage of the selected team.
     * @param titles Match names of current games being played.
     */
    public SoccerwatchFragment(ListJSON._Embedded.Title titles){
        this.title = titles;
    }

    /**
     * Creates the View of the webpage.
     * @param inflater gets the layout of the game highlights page.
     * @param container Holds the view that is being implemented
     * @param savedInstanceState passing data between various Android activities.
     * @return
     */
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
