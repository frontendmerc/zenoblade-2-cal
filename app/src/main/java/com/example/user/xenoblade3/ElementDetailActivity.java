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

public class ElementDetailActivity extends AppCompatActivity {

    private String getElement;
    private ImageView iv;
    private TextView tv;
    private ListView lv;
    private MainActivity ma = new MainActivity();
    private List<ComboRoute> comboRouteAvailable = new ArrayList<ComboRoute>();
    private Element element = new Element();
    private int elementId = 0;
    private boolean check;
    private String comboRouteArray[];
    private int[] elementImages = {R.drawable.element_unknown, R.drawable.element_fire, R.drawable.element_water, R.drawable.element_ice, R.drawable.element_earth, R.drawable.element_thunder, R.drawable.element_wind,
            R.drawable.element_light, R.drawable.element_dark};
    private Element e = new Element();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_detail);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //enable and set close button on top-left toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //get intent string from ComboListFragment or BestComboFragment
        Bundle bundle = getIntent().getExtras();
        getElement = bundle.getString("element");

        iv = (ImageView) findViewById(R.id.elementImage);
        lv = (ListView) findViewById(R.id.comboAvailable);
        tv = (TextView) findViewById(R.id.element);

        iv.setImageResource(e.getElementImage(getElement));
        tv.setText(getElement);

        elementId = element.getElementId(getElement);

        for(int i =0; i<ma.comboRoutes.length; i++){
            check = true;
            while(check){

                if(ma.comboRoutes[i].getStage1() == elementId){
                    comboRouteAvailable.add(new ComboRoute(ma.comboRoutes[i].getStage1(), ma.comboRoutes[i].getStage2(), ma.comboRoutes[i].getStage3()));

                    break;
                }

                if(ma.comboRoutes[i].getStage2() == elementId){
                    comboRouteAvailable.add(new ComboRoute(ma.comboRoutes[i].getStage1(), ma.comboRoutes[i].getStage2(), ma.comboRoutes[i].getStage3()));

                    break;
                }

                if(ma.comboRoutes[i].getStage3() == elementId){
                    comboRouteAvailable.add(new ComboRoute(ma.comboRoutes[i].getStage1(), ma.comboRoutes[i].getStage2(), ma.comboRoutes[i].getStage3()));

                    break;
                }

                check = false;

            }
        }

        comboRouteArray = new String[comboRouteAvailable.size()];

        for(int i=0; i< comboRouteArray.length; i++){

            comboRouteArray[i] = element.getTypeElement(comboRouteAvailable.get(i).getStage1()) + " -> " + element.getTypeElement(comboRouteAvailable.get(i).getStage2()) + " -> " + element.getTypeElement(comboRouteAvailable.get(i).getStage3());
        }

        ImageListViewAdapter aa = new ImageListViewAdapter(this, elementImages, comboRouteArray);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ElementDetailActivity.this, ComboDetailActivity.class);
                i.putExtra("comboRoute", comboRouteArray[position]);
                startActivity(i);
            }
        });

    }

    //create toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_item, menu);

        return true;
    }

    //set toolbar function
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
