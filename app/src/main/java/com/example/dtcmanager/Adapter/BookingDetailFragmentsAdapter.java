package com.example.dtcmanager.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;




import java.util.ArrayList;
import java.util.List;


public class BookingDetailFragmentsAdapter extends FragmentPagerAdapter implements ObjectAtPositionInterface {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public BookingDetailFragmentsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void addFragment(Fragment fragment, String name) {
        fragmentList.add(fragment);
        fragmentTitles.add(name);
    }

    @Override
    public Object getObjectAtPosition(int position) {
        if (position <= fragmentList.size() - 1){
            return fragmentList.get(position);
        }
        return null;

    }

}
