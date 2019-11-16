package com.example.thechat.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Switch;

import com.example.thechat.fragment.ChatFragment;
import com.example.thechat.fragment.ProfessoresFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private String[] tituloTabs = {"CHAT","PROFESSORES"};

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch ( i ) {
            case 0:
                fragment = new ChatFragment();
                break;
            case 1:
                fragment = new ProfessoresFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloTabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloTabs[position];
    }
}
