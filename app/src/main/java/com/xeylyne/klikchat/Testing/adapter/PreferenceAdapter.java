package com.xeylyne.klikchat.Testing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Testing.holder.CategoryPreferenceHolder;
import com.xeylyne.klikchat.Testing.model.preference.CategoryPreference;

import java.util.List;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class PreferenceAdapter extends RecyclerView.Adapter<CategoryPreferenceHolder> {
    private static final int TYPE_UNKNOWN = 0, TYPE_STANDARD = 1, TYPE_MEDIA = 2, TYPE_SEARCH = 3, TYPE_WITH_SUMMARY = 4;
    private List<CategoryPreference> categoryPreferences;

    public PreferenceAdapter(List<CategoryPreference> categoryPreferences) {
        this.categoryPreferences = categoryPreferences;
    }

    @NonNull
    @Override
    public CategoryPreferenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemCategoryPreference = null;
        switch (viewType) {
            case TYPE_STANDARD:
                itemCategoryPreference = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_standard, parent, false);
                break;
            case TYPE_MEDIA:
                itemCategoryPreference = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_media, parent, false);
                break;
            case TYPE_SEARCH:
                itemCategoryPreference = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_search, parent, false);
                break;
            case TYPE_WITH_SUMMARY:
                itemCategoryPreference = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_summary, parent, false);
        }

        return new CategoryPreferenceHolder(itemCategoryPreference);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPreferenceHolder holder, int position) {
        holder.init(categoryPreferences.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryPreferences.size();
    }

    @Override
    public int getItemViewType(int position) {
        CategoryPreference.TYPE type = categoryPreferences.get(position).getType();
        if (type == CategoryPreference.TYPE.STANDARD) return TYPE_STANDARD;
        else if (type == CategoryPreference.TYPE.MEDIA) return TYPE_MEDIA;
        else if (type == CategoryPreference.TYPE.SEARCH) return TYPE_SEARCH;
        else if (type == CategoryPreference.TYPE.WITH_SUMMARY) return TYPE_WITH_SUMMARY;
        return TYPE_UNKNOWN;
    }
}
