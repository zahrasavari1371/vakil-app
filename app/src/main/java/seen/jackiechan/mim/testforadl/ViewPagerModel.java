package seen.jackiechan.mim.testforadl;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerModel {

    private String text;
    private String title;
    private String city;
    private String laws;
    private int image;

    public ViewPagerModel(String title, String name, int image, String laws,String city) {
        this.title = title;
        this.text = name;
        this.image = image;
        this.laws = laws;
        this.city=city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLaws() {
        return laws;
    }

    public void setLaws(String laws) {
        this.laws = laws;
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
