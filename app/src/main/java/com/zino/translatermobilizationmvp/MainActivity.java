package com.zino.translatermobilizationmvp;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.zino.translatermobilizationmvp.screens.history.HistoryViewPagerFragment;
import com.zino.translatermobilizationmvp.screens.translate.view.TranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TRANSLATE_FRAGMENT_TAG = "Translate";
    public static final String HISTORY_FRAGMENT_TAG = "history";
    public static final String MENU_ITEM_KEY = "menu_item";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (item.getItemId()) {
                    case R.id.action_translate:


                        fragment = fragmentManager.findFragmentByTag(TRANSLATE_FRAGMENT_TAG);
                        if (fragment == null) {
                            fragment = new TranslateFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment, TRANSLATE_FRAGMENT_TAG).commit();

                        break;

                    case R.id.action_history:

                        fragment = fragmentManager.findFragmentByTag(HISTORY_FRAGMENT_TAG);
                        if (fragment == null) {
                            fragment = new HistoryViewPagerFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment, HISTORY_FRAGMENT_TAG).commit();

                        break;

                    case R.id.action_settings:

                        break;
                }

                return fragment != null;
            }

        });


        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new TranslateFragment(), TRANSLATE_FRAGMENT_TAG).commit();
        } else {
            int itemId = savedInstanceState.getInt(MENU_ITEM_KEY);
            bottomNavigationView.setSelectedItemId(itemId);
        }


    }

    /*
        Используется, чтобы задать позицию bottomNavigationView
        при переходе с истории на экран перевода
     */
    public void selectFirstItem() {
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(MENU_ITEM_KEY, bottomNavigationView.getSelectedItemId());
    }
}
