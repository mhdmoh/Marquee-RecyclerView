package mhdmoh.marquee_recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MarqueeItem {
    @Nullable
    private Integer iconResource;
    private String text;
    private @Nullable
    String iconUrl;

    public MarqueeItem(String text) {
        this.text = text;
    }

    public MarqueeItem(String text, int iconResource) {
        this.text = text;
        this.iconResource = iconResource;
    }

    public MarqueeItem(String text, @NonNull String icon) {
        this.text = text;
        this.iconUrl = icon;
    }

    public Integer getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
