package seen.jackiechan.mim.testforadl;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import pub.devrel.easypermissions.EasyPermissions;

public class SupportActivity extends AppCompatActivity {

    LinearLayout what_problem_layer;
    LinearLayout problem_main;
    LinearLayout consultant_layer;
    LinearLayout money_layer;
    LinearLayout app_layer;
    LinearLayout other_problem_layer;
    LinearLayout explain_layer;
    LinearLayout send_tick_layer;

    TextView problem_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        what_problem_layer = findViewById(R.id.what_problem_layer);
        problem_main = findViewById(R.id.Problem_main);
        consultant_layer = findViewById(R.id.consultant_layer);
        money_layer = findViewById(R.id.money_layer);
        app_layer = findViewById(R.id.app_layer);
        other_problem_layer = findViewById(R.id.other_problem_layer);
        explain_layer = findViewById(R.id.explain_layer);
        send_tick_layer = findViewById(R.id.send_tick_layer);

        problem_title = findViewById(R.id.problem_title);

        listeners();
    }

    public void listeners() {

        final TextView dontSendConsultantRequest = findViewById(R.id.dontSendConsultantRequest);
        final TextView problemWithFactor = findViewById(R.id.problemWithFactor);
        final TextView appCrash = findViewById(R.id.appCrash);
        final TextView other = findViewById(R.id.other);
        final TextView discountCodeDisable = findViewById(R.id.discountCodeDisable);
        final TextView noMoneyIncrease = findViewById(R.id.noMoneyIncrease);
        final TextView moneyDeductionMore = findViewById(R.id.moneyDeductionMore);
        final TextView other_consultant = findViewById(R.id.other_consultant);
        final TextView disrespect = findViewById(R.id.disrespect);
        final TextView immorality = findViewById(R.id.immorality);
        final TextView badConsultant = findViewById(R.id.badConsultant);
        final TextView bargaining = findViewById(R.id.bargaining);
        final TextView explain_title = findViewById(R.id.explain_title);


        View.OnClickListener problemDetails = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title;
                String explain;
                what_problem_layer.setVisibility(View.GONE);
                problem_main.setVisibility(View.GONE);
                consultant_layer.setVisibility(View.GONE);
                money_layer.setVisibility(View.GONE);
                app_layer.setVisibility(View.GONE);
                other_problem_layer.setVisibility(View.GONE);
                explain_layer.setVisibility(View.VISIBLE);


                switch (view.getId()) {

                    case R.id.dontSendConsultantRequest:
                        title = dontSendConsultantRequest.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainDontSendConsultantRequest);
                        explain_title.setText(explain);
                        break;

                    case R.id.problemWithFactor:
                        title = problemWithFactor.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainProblemWithFactor);
                        explain_title.setText(explain);
                        break;

                    case R.id.appCrash:
                        title = appCrash.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainAppCrash);
                        explain_title.setText(explain);
                        break;

                    case R.id.other:
                        title = other.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainOther);
                        explain_title.setText(explain);
                        break;

                    case R.id.discountCodeDisable:
                        title = discountCodeDisable.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainDiscountCodeDisable);
                        explain_title.setText(explain);
                        break;

                    case R.id.noMoneyIncrease:
                        title = noMoneyIncrease.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainNoMoneyIncrease);
                        explain_title.setText(explain);
                        break;

                    case R.id.moneyDeductionMore:
                        title = moneyDeductionMore.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainMoneyDeductionMore);
                        explain_title.setText(explain);
                        break;

                    case R.id.other_consultant:
                        title = other_consultant.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainOtherConsultant);
                        explain_title.setText(explain);
                        break;

                    case R.id.disrespect:
                        title = disrespect.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainDisrespect);
                        explain_title.setText(explain);
                        break;

                    case R.id.immorality:
                        title = immorality.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainImmorality);
                        explain_title.setText(explain);
                        break;

                    case R.id.badConsultant:
                        title = badConsultant.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainBadConsultant);
                        explain_title.setText(explain);
                        break;

                    case R.id.bargaining:
                        title = bargaining.getText().toString();
                        problem_title.setText(title);
                        explain=getResources().getString(R.string.explainBargaining);
                        explain_title.setText(explain);
                        break;

                }
            }
        };

        dontSendConsultantRequest.setOnClickListener(problemDetails);
        problemWithFactor.setOnClickListener(problemDetails);
        appCrash.setOnClickListener(problemDetails);
        other.setOnClickListener(problemDetails);
        discountCodeDisable.setOnClickListener(problemDetails);
        noMoneyIncrease.setOnClickListener(problemDetails);
        moneyDeductionMore.setOnClickListener(problemDetails);
        other_consultant.setOnClickListener(problemDetails);
        disrespect.setOnClickListener(problemDetails);
        immorality.setOnClickListener(problemDetails);
        badConsultant.setOnClickListener(problemDetails);
        bargaining.setOnClickListener(problemDetails);


        TextView problemConsultant = findViewById(R.id.problemConsultant);
        problemConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_main.setVisibility(View.GONE);
                consultant_layer.setVisibility(View.VISIBLE);
            }
        });

        TextView problemMoney = findViewById(R.id.problemMoney);
        problemMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_main.setVisibility(View.GONE);
                money_layer.setVisibility(View.VISIBLE);

            }
        });

        TextView problemApp = findViewById(R.id.problemApp);
        problemApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_main.setVisibility(View.GONE);
                app_layer.setVisibility(View.VISIBLE);
            }
        });

        TextView callWithSupport = findViewById(R.id.callWithSupport);
        callWithSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        TextView sendTick = findViewById(R.id.sendTick);
        sendTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what_problem_layer.setVisibility(View.GONE);
                problem_main.setVisibility(View.GONE);
                consultant_layer.setVisibility(View.GONE);
                money_layer.setVisibility(View.GONE);
                app_layer.setVisibility(View.GONE);
                other_problem_layer.setVisibility(View.GONE);
                explain_layer.setVisibility(View.GONE);
                send_tick_layer.setVisibility(View.VISIBLE);
            }
        });
    }

    public void call() {
        String[] galleryPermissions = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getBaseContext(), galleryPermissions)) {
            Uri uri = Uri.parse("tel:09135340374");
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage", 101, galleryPermissions);
        }
    }

    @Override
    public void onBackPressed() {

        if (explain_layer.getVisibility() == View.VISIBLE && (problem_title.getText().equals("چانه زنی") || problem_title.getText().equals("مشاوره نامناسب") ||
                problem_title.getText().equals("بدصحبتی و بداخلاقی") || problem_title.getText().equals("عدم احترام") || problem_title.getText().equals("موارد دیگر"))) {
            explain_layer.setVisibility(View.GONE);
            consultant_layer.setVisibility(View.VISIBLE);
            what_problem_layer.setVisibility(View.VISIBLE);
            other_problem_layer.setVisibility(View.VISIBLE);
        } else if (explain_layer.getVisibility() == View.VISIBLE && (problem_title.getText().equals("از اعتبار من هزینه بیشتری کم شده است") ||
                problem_title.getText().equals("بعد از پرداخت آنلاین میزان اعتبار حساب من افزایش پیدا نکرده است") ||
                problem_title.getText().equals("کد تخفیف های وارد شده اعمال نمیشود"))) {
            explain_layer.setVisibility(View.GONE);
            money_layer.setVisibility(View.VISIBLE);
            what_problem_layer.setVisibility(View.VISIBLE);
            other_problem_layer.setVisibility(View.VISIBLE);
        } else if (explain_layer.getVisibility() == View.VISIBLE && (problem_title.getText().equals("درخواست مشاوره ارسال نمیکند") ||
                problem_title.getText().equals("با نمایش رسید مشکل دارم") ||
                problem_title.getText().equals("اپلیکیشن خود به خود بسته میشود") || problem_title.getText().equals("سایر موارد"))) {
            explain_layer.setVisibility(View.GONE);
            app_layer.setVisibility(View.VISIBLE);
            what_problem_layer.setVisibility(View.VISIBLE);
            other_problem_layer.setVisibility(View.VISIBLE);
        } else if (consultant_layer.getVisibility() == View.VISIBLE || money_layer.getVisibility() == View.VISIBLE || app_layer.getVisibility() == View.VISIBLE) {
            consultant_layer.setVisibility(View.GONE);
            money_layer.setVisibility(View.GONE);
            app_layer.setVisibility(View.GONE);
            problem_main.setVisibility(View.VISIBLE);
        } else if (send_tick_layer.getVisibility() == View.VISIBLE) {
            send_tick_layer.setVisibility(View.GONE);
            problem_main.setVisibility(View.VISIBLE);
            what_problem_layer.setVisibility(View.VISIBLE);
            other_problem_layer.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }
}
