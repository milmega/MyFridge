package com.example.myfridge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyFridgeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_fridge, container, false);

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPager);
        VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new CategoryFragment("All"), "All");
        vpAdapter.addFragment(new CategoryFragment("Dairy"), "Dairy");
        vpAdapter.addFragment(new CategoryFragment("Meat"), "Meat");
        vpAdapter.addFragment(new CategoryFragment("Fruits"), "Fruits");
        vpAdapter.addFragment(new CategoryFragment("Vegetables"), "Vegetables");
        vpAdapter.addFragment(new CategoryFragment("Others"), "Others");
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}