package mhdmoh.marquee_recyclerview;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class MarqueeRecyclerViewAdapter extends RecyclerView.Adapter<MarqueeRecyclerViewAdapter.ViewHolder> {

    private final OnItemLoaded onItemLoaded;
    private final Context context;
    private List<MarqueeItem> items;

    //----- TEXT PROPERTIES -----//
    private Float textSize;
    private Integer textColor;
    private Integer textStyle;
    private Typeface textFontFamily;
    private int[] textPaddings = {0, 0, 0, 0};
    private int[] textMargins = {0, 0, 0, 0};

    //----- DIVIDER PROPERTIES -----//
    private boolean showDivider = true;
    private Integer dividerBackgroundColor = null;
    private Float dividerWidth = null;
    private int[] dividerMargins = {0, 16, 0, 16};

    public MarqueeRecyclerViewAdapter(Context context, OnItemLoaded onItemLoaded) {
        items = new ArrayList<>();
        this.context = context;
        this.onItemLoaded = onItemLoaded;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        notifyDataSetChanged();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        notifyDataSetChanged();
    }

    public void setTextStyle(Integer textStyle) {
        this.textStyle = textStyle;
        notifyDataSetChanged();
    }

    public void setTextFontFamily(Typeface textFontFamily) {
        this.textFontFamily = textFontFamily;
        notifyDataSetChanged();
    }

    public void setTextPaddings(int[] textPaddings) {
        this.textPaddings = textPaddings;
        notifyDataSetChanged();
    }

    public void setTextMargins(int[] textMargins) {
        this.textMargins = textMargins;
        notifyDataSetChanged();
    }

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
        notifyDataSetChanged();
    }

    public void setItems(List<MarqueeItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setDividerMargins(int[] dividerMargins) {
        this.dividerMargins = dividerMargins;
        notifyDataSetChanged();
    }

    public void setDividerBackgroundColor(int dividerBackgroundColor) {
        this.dividerBackgroundColor = dividerBackgroundColor;
        notifyDataSetChanged();
    }

    public void setDividerWidth(float dividerWidth) {
        this.dividerWidth = dividerWidth;
        notifyDataSetChanged();
    }

    public void load() {
        onItemLoaded.onItemLoaded();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marquee_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MarqueeItem item = items.get(position);
        holder.text.setText(item.getText());
        setUpDivider(holder.divider);
        setUpText(holder.text);
        if (item.getIconUrl() != null && !item.getIconUrl().trim().isEmpty()) {
            Picasso.get().load(item.getIconUrl()).into(holder.icon);
        } else if (item.getIconResource() != null) {
            holder.icon.setImageResource(item.getIconResource());
        }
    }

    private void setUpText(TextView text) {
        if (textSize != null)
            text.setTextSize(textSize);

        if (textColor == null)
            textColor = ContextCompat.getColor(context, R.color.black);
        text.setTextColor(textColor);

        if (textStyle != null)
            text.setTypeface(text.getTypeface(), textStyle);

        if (textFontFamily != null) {
//            Typeface face = ResourcesCompat.getFont(context, textFontFamily);
            text.setTypeface(textFontFamily);
        }

        text.setPadding(textPaddings[0], textPaddings[1], textPaddings[2], textPaddings[3]);

        ConstraintLayout.LayoutParams tlp = (ConstraintLayout.LayoutParams) text.getLayoutParams();
        tlp.setMargins(textMargins[0], textMargins[1], textMargins[2], textMargins[3]);
        if (dividerWidth != null)
            tlp.width = dividerWidth.intValue();
        text.setLayoutParams(tlp);

    }

    private void setUpDivider(View divider) {
        if (showDivider) {
            divider.setVisibility(VISIBLE);
        } else {
            divider.setVisibility(GONE);
        }

        if (dividerBackgroundColor == null)
            dividerBackgroundColor = ContextCompat.getColor(context, R.color.purple);

        divider.setBackgroundColor(dividerBackgroundColor);

        ConstraintLayout.LayoutParams dlp = (ConstraintLayout.LayoutParams) divider.getLayoutParams();
        dlp.setMargins(dividerMargins[0], dividerMargins[1], dividerMargins[2], dividerMargins[3]);
        if (dividerWidth != null)
            dlp.width = dividerWidth.intValue();
        divider.setLayoutParams(dlp);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView icon;
        final TextView text;
        final RelativeLayout divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
