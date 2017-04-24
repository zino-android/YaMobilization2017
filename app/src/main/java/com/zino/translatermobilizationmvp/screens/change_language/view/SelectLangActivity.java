package com.zino.translatermobilizationmvp.screens.change_language.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.change_language.LanguageAdapter;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.presenter.SelectLangPresenter;
import com.zino.translatermobilizationmvp.screens.change_language.presenter.SelectLangPresenterImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectLangActivity extends AppCompatActivity implements SelectLangView, ListView.OnItemClickListener{

    public final static String TITLE_KEY = "title";
    public final static String IS_FIRST_LANGUAGE_KEY = "is_first_language";

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Language> langsList = new ArrayList<>(0);
    private LanguageAdapter adapter;

    private SelectLangPresenter presenter;
    private boolean isFirstLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);
        ButterKnife.bind(this);
        adapter = new LanguageAdapter(this, langsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        presenter = new SelectLangPresenterImp(this);

        presenter.loadLangs();

        String title = getIntent().getStringExtra(TITLE_KEY);

        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        isFirstLanguage = getIntent().getBooleanExtra(IS_FIRST_LANGUAGE_KEY, false);

    }



    @Override
    public void showError() {
        Toast.makeText(this, getResources().getString(R.string.error_text), Toast.LENGTH_LONG).show();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapter.setSelectedItem(i);
        adapter.notifyDataSetInvalidated();

        Language lan = adapter.getItem(i);

        presenter.onSelectLanguage(lan, isFirstLanguage);

        finish();
    }

    @Override
    public void showLangs(List<Language> languages) {
        adapter.setItems(languages);
        int selected = presenter.getSelectedItem(languages, isFirstLanguage);
        adapter.setSelectedItem(selected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listView = null;
        toolbar = null;
        presenter.onDestroy();
    }
}
