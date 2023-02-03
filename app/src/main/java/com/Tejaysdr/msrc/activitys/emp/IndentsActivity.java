package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.WindowManager;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.Fragments.IndentlistFragment;
import com.Tejaysdr.msrc.activitys.Fragments.NewIndentsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IndentsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    Toolbar toolbar_complaint_cat;
    private String objectempcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indents);
        toolbar_complaint_cat=findViewById(R.id.toolbar_complaint_cat);
        setSupportActionBar(toolbar_complaint_cat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_complaint_cat.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Indents");
//        getComplaintCount();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            //startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new OpenFragment(), "Open Complaint");
//        adapter.addFragment(new InProgressFragment(), "In Progress");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Completed");
        adapter.addFragment(new IndentlistFragment(), "Indentlist");
        adapter.addFragment(new NewIndentsFragment(), "New Indents");
//        adapter.addFragment(new InTransitFragment(), "In Transit");
//        adapter.addFragment(new OnsiteFragment(), "OnSite");
//        adapter.addFragment(new WorkInProgressFragment(), "Work in progress");
////        adapter.addFragment(new ResolvedFragment(), "Resolved");
////        adapter.addFragment(new CompletedFragment(), "Closed");
//        adapter.addFragment(new PendingforsparesFragment(), "Pending For Spares");
//        adapter.addFragment(new PendingforcustomerdependencyFragment(), "Pending for Customer Dependency");
//        adapter.addFragment(new PendingforothersFragment(), "Pending For Others");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Closed");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title ) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);


        }

    }
    }


