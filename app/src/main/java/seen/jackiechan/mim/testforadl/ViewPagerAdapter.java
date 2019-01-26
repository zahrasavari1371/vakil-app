package seen.jackiechan.mim.testforadl;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<ViewPagerModel> values;

    public ViewPagerAdapter(ArrayList<ViewPagerModel> values) {
        this.values = values;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.fragment_third_about, container, false);
        container.addView(view);

        TextView aboutTitle = view.findViewById(R.id.aboutTitle);
        aboutTitle.setText(values.get(position).getTitle());

        TextView aboutText = view.findViewById(R.id.aboutText);
        aboutText.setText(values.get(position).getText());

        TextView aboutCity = view.findViewById(R.id.aboutCity);
        aboutCity.setText(values.get(position).getCity());

        TextView laws = view.findViewById(R.id.laws);
        laws.setText(values.get(position).getLaws());

        ImageView aboutLogo = view.findViewById(R.id.aboutLogo);
        aboutLogo.setImageResource(values.get(position).getImage());

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}