package com.xeylyne.klikchat.Testing.holder;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Testing.model.preference.CategoryPreference;
import com.xeylyne.klikchat.Testing.model.preference.SettingsPreference;
import com.xeylyne.klikchat.Testing.model.preference.ExtraButtonPreference;
import com.xeylyne.klikchat.Testing.model.preference.MediaPreference;
import com.xeylyne.klikchat.Testing.model.preference.Preference;
import com.xeylyne.klikchat.Testing.model.preference.SwitchPreference;
import com.xeylyne.klikchat.Testing.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class CategoryPreferenceHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView textTitle;

    @BindView(R.id.preferenceContainer)
    LinearLayout preferenceContainer;

    @Nullable
    @BindView(R.id.summary)
    TextView textSummary;

    @Nullable
    @BindView(R.id.search_button)
    ImageButton btnSearch;

    public CategoryPreferenceHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void init(CategoryPreference categoryPreference) {
        preferenceContainer.removeAllViews();
        textTitle.setVisibility(View.GONE);
        if (textSummary != null) textSummary.setVisibility(View.GONE);
        if (btnSearch != null) btnSearch.setVisibility(View.GONE);

        String categoryTitle = categoryPreference.getTitle();
        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(categoryTitle);

            String categorySummary = categoryPreference.getSummary();
            if (categorySummary != null && !categorySummary.trim().isEmpty() && textSummary != null) {
                textSummary.setVisibility(View.VISIBLE);
                textSummary.setText(categoryTitle);
            }
            if (btnSearch != null) btnSearch.setVisibility(View.VISIBLE);
        }

        List<Preference> preferences = categoryPreference.getPreferences();
        for (Preference preference : preferences) {
            if (preference instanceof MediaPreference) {
                MediaPreference mediaPreference = (MediaPreference) preference;
                ImageView image = new ImageView(itemView.getContext());
                int sizeDp = DensityUtils.dpToPx(24);
                preferenceContainer.addView(image, new LayoutParams(sizeDp, sizeDp));
                Glide.with(image.getContext())
                        .load(mediaPreference.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(image);
            } else /*(preference.getType() == Preference.TYPE.)*/ {
                LinearLayout linearLayout = new LinearLayout(itemView.getContext());
                linearLayout.setEnabled(preference.isEnabled());
                int padding = DensityUtils.dpToPx(16);
                linearLayout.setPadding(padding, 0, padding, 0);

                if (preference.getClickListener() != null) {
                    linearLayout.setOnClickListener(preference.getClickListener());

                    int[] attrs = new int[]{R.attr.selectableItemBackground};
                    TypedArray typedArray = linearLayout.getContext().obtainStyledAttributes(attrs);
                    int backgroundResource = typedArray.getResourceId(0, 0);
                    linearLayout.setBackgroundResource(backgroundResource);
                    typedArray.recycle();
                }

                if (preference instanceof SettingsPreference) {
                    SettingsPreference settingsPreference = (SettingsPreference) preference;
                    ImageView image = new ImageView(linearLayout.getContext());
                    int sizeDp = DensityUtils.dpToPx(42);
                    LayoutParams params = new LayoutParams(sizeDp, sizeDp);
                    params.gravity = Gravity.CENTER;
                    params.setMargins(0, padding, 0, padding);
                    linearLayout.addView(image, params);

//                    Glide.with(image.getContext())
//                            .load(settingsPreference.getImage())
//                            .diskCacheStrategy(DiskCacheStrategy.DATA)
//                            .circleCrop()
//                            .into(image);
                    image.setImageResource(settingsPreference.getImage());
                } else if (preference.getIcon() != R.drawable.drawable_null) {
                    ImageView image = new ImageView(linearLayout.getContext());
                    image.setImageResource(preference.getIcon());
                    image.setColorFilter(preference.getColorIcon(), android.graphics.PorterDuff.Mode.SRC_IN);
                    int sizeDp = DensityUtils.dpToPx(24);
                    LayoutParams params = new LayoutParams(sizeDp, sizeDp);
                    params.gravity = Gravity.CENTER;
                    params.setMargins(0, padding, 0, padding);
                    linearLayout.addView(image, params);
                }

                if ((preference.getTitle() != null && !preference.getTitle().isEmpty()) ||
                        (preference.getSummary() != null && !preference.getSummary().isEmpty())) {
                    LinearLayout layoutContent = new LinearLayout(linearLayout.getContext());
                    layoutContent.setOrientation(LinearLayout.VERTICAL);
                    TextView title = new TextView(layoutContent.getContext());
                    title.setTag("title");
                    title.setVisibility(View.GONE);
                    title.setSingleLine(true);
                    title.setTextSize(16);
                    title.setTextColor(preference.getColorTitle());
                    layoutContent.addView(title, new LayoutParams(MATCH_PARENT, WRAP_CONTENT));

                    if (preference.getTitle() != null && !preference.getTitle().isEmpty()) {
                        title.setVisibility(View.VISIBLE);
                        title.setText(preference.getTitle());
                    }

                    TextView summary = new TextView(layoutContent.getContext(), null, android.R.attr.textAppearanceSmall);
                    summary.setTag("summary");
                    summary.setVisibility(View.GONE);
                    summary.setMaxLines(10);
                    layoutContent.addView(summary, new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                    if (preference.getSummary() != null && !preference.getSummary().isEmpty()) {
                        summary.setVisibility(View.VISIBLE);
                        summary.setText(preference.getSummary());
                    }

                    LayoutParams params = new LayoutParams(0, WRAP_CONTENT);
                    params.weight = 1;
                    if (preference instanceof SettingsPreference) {
                        int margin = DensityUtils.dpToPx(8);
                        params.setMargins(margin, padding, 0, padding);
                    } else if (preference.getIcon() != R.drawable.drawable_null) {
                        int margin = DensityUtils.dpToPx(24);
                        params.setMargins(margin, padding, 0, padding);
                    } else {
                        params.setMargins(0, padding, 0, padding);
                    }
                    params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                    linearLayout.addView(layoutContent, params);
                }

                if (preference instanceof SettingsPreference) {
                    SettingsPreference settingsPreference = (SettingsPreference) preference;
                    TextView status = new TextView(linearLayout.getContext());
                    status.setBackgroundResource(R.drawable.bg_rounded_green);
                    int margin = DensityUtils.dpToPx(2);
                    int marginSide = DensityUtils.dpToPx(4);
                    status.setPadding(marginSide, margin, marginSide, margin);
                    status.setTextSize(10);
                    status.setTag("status");
                    status.setVisibility(View.GONE);
                    status.setTextColor(settingsPreference.getColorStatus());
                    LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    linearLayout.addView(status, params);
                    if (settingsPreference.getStatus() != null) {
                        status.setVisibility(View.VISIBLE);
                        status.setText(settingsPreference.getStatus());
                    }
                } else if (preference instanceof SwitchPreference) {
                    SwitchPreference switchPreference = (SwitchPreference) preference;
                    SwitchCompat switchCompat = new SwitchCompat(linearLayout.getContext());
                    switchCompat.setTag("switch");
                    switchCompat.setEnabled(switchPreference.isSwitchEnabled());
                    switchCompat.setChecked(switchPreference.getState());
                    switchCompat.setOnClickListener(switchPreference.getSwitchClickListener());
                    LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    linearLayout.addView(switchCompat, params);
                } else if (preference instanceof ExtraButtonPreference) {
                    ExtraButtonPreference buttonPreference = (ExtraButtonPreference) preference;
                    if (buttonPreference.getButtonCount() > 0) {
                        linearLayout.setPadding(padding, 0, 0, 0);
                    }

                    for (int i = 0; i < buttonPreference.getButtonCount(); i++) {
                        ExtraButtonPreference.Button btn = buttonPreference.getButton(i);
                        if (btn == null) continue;

                        ImageButton button = new ImageButton(linearLayout.getContext());
                        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
                        TypedArray typedArray = linearLayout.getContext().obtainStyledAttributes(attrs);
                        Drawable drawableFromTheme = typedArray.getDrawable(0);
                        typedArray.recycle();
                        button.setBackground(drawableFromTheme);
                        button.setImageResource(btn.getIcon());
                        button.setOnClickListener(btn.getClickListener());
                        button.setColorFilter(preference.getColorIcon(), android.graphics.PorterDuff.Mode.SRC_IN);

                        linearLayout.addView(button, new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
                    }
                }

                LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                preferenceContainer.addView(linearLayout, params);
            }
        }
    }
}
