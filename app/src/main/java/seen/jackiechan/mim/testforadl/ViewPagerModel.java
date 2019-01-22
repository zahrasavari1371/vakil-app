package seen.jackiechan.mim.testforadl;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerModel {

    private String text;
    private String title;
    private int image;

    public ViewPagerModel(String title, String name, int image) {
        this.title = title;
        this.text = name;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + text + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
