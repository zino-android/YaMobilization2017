package com.zino.translatermobilizationmvp.screens.translate.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zino.translatermobilizationmvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullScreenActivity extends AppCompatActivity {

    public final static String TEXT_KEY = "text";

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.close_image_button)
    ImageButton closeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            String text = getIntent().getStringExtra(TEXT_KEY);
            textView.setText(text);
        }
    }


    @OnClick(R.id.close_image_button)
    void onCloseClicked() {
        finish();
    }
}
