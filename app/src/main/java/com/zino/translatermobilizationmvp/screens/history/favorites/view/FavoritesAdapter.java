package com.zino.translatermobilizationmvp.screens.history.favorites.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.utils.UtilsDrawable;

import java.util.List;



public class FavoritesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private List<Word> objects;
    private FavoritesFragment.OnAddOrRemoveFavorites listener;
    private FavoritesFragment.OnItemClickListener onClickListener;

    public FavoritesAdapter(Context context, List<Word> words, FavoritesFragment.OnAddOrRemoveFavorites listener,  FavoritesFragment.OnItemClickListener onClickListener) {
        this.context = context;
        objects = words;
        this.listener = listener;
        this.onClickListener = onClickListener;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Word getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.word_list_item, parent, false);
        }

        Word item = getItem(position);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.image_view);
        TextView langsTextView = (TextView) view.findViewById(R.id.lang_text_view);
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView translate = (TextView) view.findViewById(R.id.translate);

        String langs = item.getFirstLanguageValue().toUpperCase() + " - " + item.getSecondLanguageValue().toUpperCase();

        langsTextView.setText(langs);

        text.setText(item.getWord());
        translate.setText(item.getTranslate());

        if (item.isInFavorites()) {
            imageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    context,
                    context.getResources().getDrawable(R.drawable.bookmark),
                    R.color.colorPrimary
            ));
        } else {
            imageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    context,
                    context.getResources().getDrawable(R.drawable.bookmark),
                    R.color.iconsGray
            ));
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddOnRemoveFavoritesClicked(item);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(item);
            }
        });

        return view;
    }



    public void setItems(List<Word> items) {
        objects = items;
        notifyDataSetChanged();
    }

    public List<Word> getItems() {
        return objects;
    }
}
