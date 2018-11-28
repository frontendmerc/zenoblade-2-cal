package com.example.user.xenoblade3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ComboDetailActivity extends AppCompatActivity {

    private String comboRoute;
    private TextView showComboRoute;
    private ListView lv;
    private List<String> stagesList = new ArrayList<String>();
    private boolean check;
    private String[] tmpStages, stages;
    private int[] elementImages = {R.drawable.element_unknown, R.drawable.element_fire, R.drawable.element_water, R.drawable.element_ice, R.drawable.element_earth, R.drawable.element_thunder, R.drawable.element_wind,
            R.drawable.element_light, R.drawable.element_dark};
    private Element e = new Element();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_detail);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //enable and set close button on top-left toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //get intent string from ComboListFragment or BestComboFragment
        Bundle bundle = getIntent().getExtras();
        comboRoute = bundle.getString("comboRoute");

        ImageView imageView1 = (ImageView) findViewById(R.id.combo_detail_image1);
        ImageView imageView2 = (ImageView) findViewById(R.id.combo_detail_image2);
        ImageView imageView3 = (ImageView) findViewById(R.id.combo_detail_image3);

        //eg.. "Fire -> Water -> Thunder"
        imageView1.setImageResource(e.getElementImage(comboRoute.split(" -> ")[0]));
        imageView2.setImageResource(e.getElementImage(comboRoute.split(" -> ")[1]));
        imageView3.setImageResource(e.getElementImage(comboRoute.split(" -> ")[2]));

        tmpStages = comboRoute.split(" -> ");
        stagesList.add(tmpStages[0]);

        for (int i = 0; i < tmpStages.length; i++) {
            check = true;
            while (check) {

                if (!tmpStages[i].equals(stagesList.get(0))) {

                    stagesList.add(tmpStages[i]);
                }
                check = false;
            }

        }

        stages = new String[stagesList.size()];
        stages = stagesList.toArray(stages);

        ImageListViewAdapter2 aa = new ImageListViewAdapter2(this, elementImages, stages);
        lv = (ListView) findViewById(R.id.elements);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ComboDetailActivity.this, ElementDetailActivity.class);
                i.putExtra("element", stages[position]);
                startActivity(i);
            }
        });

    }

    //Create Action Bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_item, menu);

        return true;
    }

    //toolbar item select
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = null;

        if(id == R.id.picture_btn){

            i = new Intent(this, ComboChartActivity.class);
        }else if(id == R.id.about_btn){

            i = new Intent(this, ComboChartActivity.class);
        }else{
            i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //    clear all stack activities except main
        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
