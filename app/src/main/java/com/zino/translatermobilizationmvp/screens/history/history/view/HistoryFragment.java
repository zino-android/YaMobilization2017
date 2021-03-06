package com.zino.translatermobilizationmvp.screens.history.history.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zino.translatermobilizationmvp.MainActivity;
import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.history.HistoryViewPagerFragment;
import com.zino.translatermobilizationmvp.screens.history.favorites.presenter.FavoritesPresenterImp;
import com.zino.translatermobilizationmvp.screens.history.favorites.view.FavoritesAdapter;
import com.zino.translatermobilizationmvp.screens.history.favorites.view.FavoritesFragment;
import com.zino.translatermobilizationmvp.screens.history.history.presenter.HistoryPresenter;
import com.zino.translatermobilizationmvp.screens.history.history.presenter.HistoryPresenterImp;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.screens.translate.view.TranslateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HistoryFragment extends Fragment implements HistoryView {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.find_edit_text)
    EditText findEditText;
    @BindView(R.id.clear_image_view)
    ImageView clearImageView;
    @BindView(R.id.no_history_text_view)
    TextView noHistoryTextView;

    private FavoritesAdapter adapter;

    private HistoryPresenter presenter;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new HistoryPresenterImp(this);


        adapter = new FavoritesAdapter(getContext(), new ArrayList<>(), new FavoritesFragment.OnAddOrRemoveFavorites() {
            @Override
            public void onAddOnRemoveFavoritesClicked(Word word) {
                presenter.addOrRemoveFavoritesClicked(word);

                FavoritesFragment favoritesFragment = (FavoritesFragment) getTargetFragment();
                if (favoritesFragment != null) {
                    favoritesFragment.updateFragment();
                }
            }
        }, new FavoritesFragment.OnItemClickListener() {
            @Override
            public void onItemClick(Word word) {
                Fragment fragment = TranslateFragment.newInstance(word);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, MainActivity.TRANSLATE_FRAGMENT_TAG).commit();
                //Обновляем bottomNavigationView
                ((MainActivity)getActivity()).selectFirstItem();
            }
        });

        listView.setAdapter(adapter);



        Drawable findDrawable = getResources().getDrawable(R.drawable.magnify);
        findDrawable.setBounds(0, 0, (int)getResources().getDimension(R.dimen.one_dp) * 24, (int)getResources().getDimension(R.dimen.one_dp) * 24);
        findEditText.setCompoundDrawables(findDrawable, null, null, null);

        findEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    clearImageView.setVisibility(View.INVISIBLE);
                    presenter.getHistory();
                } else {
                    clearImageView.setVisibility(View.VISIBLE);
                    presenter.getFindedHistory(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        presenter.getHistory();

    }

    @OnClick(R.id.clear_image_view)
    void onClearButtonClicked() {
        findEditText.setText("");
    }

    @Override
    public void showHistory(List<Word> history) {
        adapter.setItems(history);
        adapter.notifyDataSetChanged();
        noHistoryTextView.setVisibility(View.GONE);
        findEditText.setVisibility(View.VISIBLE);

    }

    @Override
    public void showNoHistory() {
        noHistoryTextView.setVisibility(View.VISIBLE);
        adapter.setItems(new ArrayList<>(0));
        findEditText.setVisibility(View.GONE);
    }

    public void updateFragment() {
        presenter.getHistory();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listView = null;
        findEditText = null;
        clearImageView = null;
        noHistoryTextView = null;

        presenter.onDestroy();
    }
}
