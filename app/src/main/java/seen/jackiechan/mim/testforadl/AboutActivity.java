package seen.jackiechan.mim.testforadl;

import android.support.annotation.NonNull;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        viewPager = findViewById(R.id.viewPager);
        InkPageIndicator inkPageIndicator = findViewById(R.id.indicator);

        ArrayList<ViewPagerModel> values = new ArrayList<>();
        values.add(new ViewPagerModel("سرعت و امنیت", getResources().getString(R.string.aboutTextOne), R.drawable.logo_si));
        values.add(new ViewPagerModel("صرفه جویی در وقت و هزینه", getResources().getString(R.string.aboutTextTwo), R.drawable.logo_si));
        values.add(new ViewPagerModel("عملکرد آسان", getResources().getString(R.string.aboutTextThree), R.drawable.logo_si));
        values.add(new ViewPagerModel("برای همه مردم ایران", getResources().getString(R.string.aboutTextFour), R.drawable.logo_si));

        ViewPagerAdapter adapter = new ViewPagerAdapter(values);
        viewPager.setAdapter(adapter);
        inkPageIndicator.setViewPager(viewPager);
    }
}
