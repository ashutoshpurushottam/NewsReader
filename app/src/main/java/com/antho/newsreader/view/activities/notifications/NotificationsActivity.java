package com.antho.newsreader.view.activities.notifications;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import androidx.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.antho.newsreader.R;
import com.antho.newsreader.base.BaseActivity;
import com.antho.newsreader.view.activities.notifications.handlers.NotificationJobService;
import com.antho.newsreader.viewmodel.SearchViewModel;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

/**
 * Handles notifications system
 **/
public class NotificationsActivity extends BaseActivity {
    @BindView(R.id.artsCheckBox)
    CheckBox artsCheckBox;
    @BindView(R.id.businessCheckBox)
    CheckBox businessCheckBox;
    @BindView(R.id.entrepreneursCheckBox)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.politicsCheckBox)
    CheckBox politicsCheckBox;
    @BindView(R.id.sportsCheckBox)
    CheckBox sportsCheckBox;
    @BindView(R.id.travelCheckBox)
    CheckBox travelCheckBox;
    @BindView(R.id.searchTerm)
    EditText searchTermText;
    @BindView(R.id.sortSwitch)
    Switch sortSwitch;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ArrayList<CheckBox> notificationsSubjectCheckBoxes;
    public static final int NOTIFICATION_ID = 888;
    private String categoriesText;
    private SearchViewModel viewModel;
    int dailyNews;

    // Add all checkboxes and create and handles job service
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationsSubjectCheckBoxes = new ArrayList<>();
        notificationsSubjectCheckBoxes.add(artsCheckBox);
        notificationsSubjectCheckBoxes.add(businessCheckBox);
        notificationsSubjectCheckBoxes.add(entrepreneursCheckBox);
        notificationsSubjectCheckBoxes.add(politicsCheckBox);
        notificationsSubjectCheckBoxes.add(sportsCheckBox);
        notificationsSubjectCheckBoxes.add(travelCheckBox);
        sortSwitch.setOnCheckedChangeListener((v, enabled) ->
        {
            if (enableNotifications()) {
                JobScheduler scheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
                ComponentName componentName = new ComponentName(getApplicationContext(), NotificationJobService.class);
                if (enabled) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("news_desk:(");
                    for (CheckBox checkBox : notificationsSubjectCheckBoxes) {
                        if (checkBox.isChecked()) {
                            sb.append("\"").append(checkBox.getText().toString()).append("\"");
                        }
                    }
                    sb.append(")");
                    categoriesText = sb.toString();
                    String searchTerm = searchTermText.getText().toString();
                    String endDate = String.format(Locale.getDefault(), "%d%02d%02d", ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth());
                    String beginDate = String.format(Locale.getDefault(), "%d%02d%02d", ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth() - 1);
                    viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
                    viewModel.setQueryParameters(searchTerm, categoriesText, beginDate, endDate);
                    observeViewModel();
                    PersistableBundle bundle = new PersistableBundle();
                    bundle.putInt("dailynews", dailyNews);
                    bundle.putString("categories", categoriesText);
                    bundle.putString("query", searchTerm);
                    bundle.putString("end", endDate);
                    bundle.putString("begin", beginDate);
                    JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                            .setPeriodic(900 * 1000) // 86400 * 1000
                            .setExtras(bundle)
                            .build();

                    scheduler.schedule(jobInfo);
                } else {
                    scheduler.cancelAll();
                }
            }
        });
    }

    // Checks if the notifications can be enabled with current query settings
    private boolean enableNotifications() {
        String searchTerm = searchTermText.getText().toString();
        for (CheckBox checkBox : notificationsSubjectCheckBoxes) {
            if (checkBox.isChecked() && !searchTerm.isEmpty()) {
                return true;
            }
        }
        sortSwitch.setChecked(false);
        showToast("Please type a query and choose at least one category to allow notifications");
        return false;
    }

    // Observe viewmodel to retrieve the total of daily news to show through notifications
    private void observeViewModel() {
        viewModel.getSearchNews().observe(this, searchNews ->
        {
            if (searchNews != null)
                dailyNews = searchNews.size();
        });
    }

    // Return activity layout
    @Override
    protected int layoutRes() {
        return R.layout.activity_notifications;
    }
}


