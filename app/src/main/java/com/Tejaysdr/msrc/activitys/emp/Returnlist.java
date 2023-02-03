package com.Tejaysdr.msrc.activitys.emp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.Fragments.IndentlistFragment;
import com.Tejaysdr.msrc.activitys.Fragments.NewIndentsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Returnlist extends AppCompatActivity {
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Toolbar toolbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returnlist);

       toolbar1=findViewById(R.id.toolbar_complaint_cat1r);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar1.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Return To Warehouse");
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

        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
     ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new OpenFragment(), "Open Complaint");
//        adapter.addFragment(new InProgressFragment(), "In Progress");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Completed");
        adapter.addFragment(new IndentlistFragment(), "New Return");
        adapter.addFragment(new NewIndentsFragment(), "Return list");
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
        Fragment f1;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
             f1 =new Fragment();
             switch (position){
                 case 0:
                     f1=new NewIndentsFragment();
                     break;

                 case 1:
                     f1=new IndentlistFragment();
                     break;
             }
             return f1;
           // return mFragmentList.get(position);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}




