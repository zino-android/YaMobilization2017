package com.zino.translatermobilizationmvp.screens.change_language;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;

import java.util.List;



public class LanguageAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private List<Language> languages;
    private int selectedItem = -1;

    public LanguageAdapter(Context context, List<Language> items) {
        this.context = context;
        languages = items;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Language getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.language_list_item, parent, false);
        }

        Language item = getItem(position);

        ImageView selectedImageView = (ImageView) view.findViewById(R.id.selected_image_view);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);

        nameTextView.setText(item.getName());

        if (position == selectedItem) {
            view.setBackgroundColor(context.getResources().getColor(R.color.languageSelectedColor));
            selectedImageView.setVisibility(View.VISIBLE);
        } else {
            view.setBackgroundColor(Color.WHITE);
            selectedImageView.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    public void setItems(List<Language> items) {
        languages = items;
        notifyDataSetChanged();
    }

    public List<Language> getItems() {
        return languages;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
}
