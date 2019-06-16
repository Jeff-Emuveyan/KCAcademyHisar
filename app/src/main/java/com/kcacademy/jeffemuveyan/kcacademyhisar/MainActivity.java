package com.kcacademy.jeffemuveyan.kcacademyhisar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kcacademy.jeffemuveyan.kcacademyhisar.Fragments.AboutUsFragment;
import com.kcacademy.jeffemuveyan.kcacademyhisar.Fragments.AllLecturesFragment;
import com.kcacademy.jeffemuveyan.kcacademyhisar.Fragments.CategoriesFragment;
import com.kcacademy.jeffemuveyan.kcacademyhisar.Fragments.DashboardFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView navigation;

    public static FragmentStatePagerAdapter adapterViewPager;
    public static ViewPager vpPager;

    static android.support.v4.app.FragmentManager fm;
    public static ArrayList<Fragment> fragments;

    public static Boolean viewPagerIsVisible;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all:
                    popBackStack();
                    showViewPager();
                    //Now we reveal the fragment by saying:
                    slideToFragment(0);
                    return true;
                case R.id.navigation_categories:
                    popBackStack();
                    showViewPager();
                    //Now we reveal the fragment by saying:
                    slideToFragment(1);
                    return true;
                case R.id.navigation_dashboard:
                    popBackStack();
                    showViewPager();
                    //Now we reveal the fragment by saying:
                    slideToFragment(2);
                    return true;

                case R.id.navigation_about:
                    popBackStack();
                    showViewPager();
                    //Now we reveal the fragment by saying:
                    slideToFragment(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4896fe")));

        fm = getSupportFragmentManager();

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setOffscreenPageLimit(4);//this determines how many pages get loaded simultaneously
        adapterViewPager = new MainActivity.MyPagerAdapter(fm);

        viewPagerIsVisible = true;

        //add the new Fragments we need
        Fragment allLecturesFragment = new AllLecturesFragment();
        Fragment categoriesFragment = new CategoriesFragment();
        Fragment dashboardFragment = new DashboardFragment();
        Fragment aboutUsFragment = new AboutUsFragment();

        fragments = new ArrayList<>();
        fragments.add(allLecturesFragment);
        fragments.add(categoriesFragment);
        fragments.add(dashboardFragment);
        fragments.add(aboutUsFragment);

        //adapterViewPager.notifyDataSetChanged();

        vpPager.setAdapter(adapterViewPager);

        vpPager.setCurrentItem(0);//The time fragment should be at position 0 at his point

        //Now we want to do something so that when the user swipes through with his fingers, the bottom navigation drawer menu position gets updated
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //so when the user swipes with his fingers, we also move the position of the selected item on the bottom menu:
                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_all);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_categories);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.navigation_about);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public static void popBackStack() {//this means removing any fragment on top of another fragment

        if (fm.getBackStackEntryCount() > 0) {
            //first we must close the drawer
            fm.popBackStack();
        }
    }


    public static void showViewPager() {

        if (!viewPagerIsVisible) {
            //reveal it
            vpPager.setVisibility(View.VISIBLE);
            viewPagerIsVisible = true;
        }
    }


    private void slideToFragment(final int position) {//This method is necessary to fix a stupid bug
        //the method 'onNavigationItemSelected' from BottomNavigationView runs when the app starts.
        //inside it, in our switch block we called stuffs like 'vpPager.setCurrentItem(0);'
        //this causes a bug crash Exception which says that the 'FragmentManager is still executing transations.
        //To solve this, we create this method and inside it we say:
        adapterViewPager = new MainActivity.MyPagerAdapter(fm);

        //and we do it like this:
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                vpPager.setCurrentItem(position);//slide to the fragment
            }
        });


    }




    //inner class
    public static class MyPagerAdapter extends FragmentStatePagerAdapter {


        public MyPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return fragments.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }


        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }



    }//END INNER CLASS

}
