package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Problems extends AppCompatActivity {
    MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problems);

     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Problems.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mTabLayout.setupWithViewPager(viewPager);






//        for(int i = 0; i < mTabLayout.getTabCount(); i++){
//            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }

    }


    public void menu(){
        item = (MenuItem) findViewById(R.id.Log_out);



        }






    @Override
    public void onResume() { super.onResume();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.Log_out){
            Intent postIntent = new Intent(Problems.this,Auth_login.class);

            startActivity(postIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Pollution", "Encroachment", "RRR"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new Pollution();
                case 1:
                    return new Encroachment();
                case 2:
                    return new RRR();
            }

            return null;
        }


    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    public View getTabView(int position){
        View tab = LayoutInflater.from(Problems.this).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }
}
}