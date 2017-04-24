package com.zino.translatermobilizationmvp.screens.history;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.history.favorites.view.FavoritesFragment;
import com.zino.translatermobilizationmvp.screens.history.history.view.HistoryFragment;
import com.zino.translatermobilizationmvp.screens.translate.model.WordsHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryViewPagerFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.delete_image_button)
    ImageButton deleteImageButton;

    private PagerAdapter adapter;
    private WordsHelper helper;


    public HistoryViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.history_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.favorites_tab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new WordsHelper();

                //Удалаем историю или избранное в зависимости от того,
                // какая вкладка выбрана
                int id = tabLayout.getSelectedTabPosition();
                if (id == 0) {
                    helper.deleteAllFromHistory();
                } else {
                    helper.deleteAllFromFavorites();
                }

                updateFragments();
            }
        });

    }

    /*
        Обновляем информацию, чтобы когда мы перейдем на фрагмент
        информация была актуальной
     */
    public void updateFragments() {
        int id = tabLayout.getSelectedTabPosition();
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabLayout = null;
        viewPager = null;
        deleteImageButton = null;
        if (helper != null) {
            helper.onDestroy();
        }
    }
}
