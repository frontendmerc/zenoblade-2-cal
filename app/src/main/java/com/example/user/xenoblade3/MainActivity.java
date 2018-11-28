package com.example.user.xenoblade3;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //initialize all elements
    private Element[] elements = new Element[]{

            new Element(0, "None"),
            new Element(1, "Fire"),
            new Element(2, "Water"),
            new Element(3, "Ice"),
            new Element(4, "Earth"),
            new Element(5, "Thunder"),
            new Element(6, "Wind"),
            new Element(7, "Light"),
            new Element(8, "Dark"),
    };

    //initialize all combo routes
    protected ComboRoute[] comboRoutes = new ComboRoute[]{

            //fire
            new ComboRoute(1, 1, 1),
            new ComboRoute(1, 2, 1),
            new ComboRoute(7, 5, 1),
            //water
            new ComboRoute(2, 2, 2),
            new ComboRoute(5, 5, 2),
            new ComboRoute(7, 7, 2),
            //ice
            new ComboRoute(1, 2, 3),
            new ComboRoute(5, 1, 3),
            new ComboRoute(6, 3, 3),
            //Earth
            new ComboRoute(4, 1, 4),
            new ComboRoute(6, 6, 4),
            new ComboRoute(3, 3, 4),
            new ComboRoute(8, 8, 4),
            //Thunder
            new ComboRoute(4, 4, 5),
            new ComboRoute(6, 6, 5),
            new ComboRoute(8, 7, 5),
            //Wind
            new ComboRoute(2, 4, 6),
            new ComboRoute(4, 1, 6),
            new ComboRoute(5, 1, 6),
            new ComboRoute(3, 2, 6),
            //Light
            new ComboRoute(1, 1, 7),
            new ComboRoute(7, 7, 7),
            //Dark
            new ComboRoute(2, 2, 8),
            new ComboRoute(3, 3, 8),
            new ComboRoute(8, 8, 8),

    };

    private Element ElementString = new Element(); //create an element class object to convert int to string [element]
    private List<Integer> userSelectItem = new ArrayList<Integer>(); //list to store user selected items
    private List<ComboRoute> customComboRoute = new ArrayList<ComboRoute>(); //list to store the possible route that item user selected
    private List<Integer> route1 = new ArrayList<Integer>(); //list to store stage 1 from ComboRoute Class
    private List<Integer> route2 = new ArrayList<Integer>(); //list to store stage 2 from ComboRoute Class
    private List<Integer> route3 = new ArrayList<Integer>(); //list to store stage 3 from ComboRoute Class
    private TextView textView, test;
    private boolean checkStage1, checkStage2, checkStage3;
    private int[][] doublearray;
    private Button calculateBtn, pictureBtn, resetBtn;
    private Integer[] userSelect;
    private String[] listViewArray, bestComboArray;
    private ListView lv;
    private final Bundle bundle = new Bundle();
    private TabLayout t1;
    private FrameLayout f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinner();
        setUserSelect();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add tablayout's tab
        f1 = (FrameLayout) findViewById(R.id.fragmentLayout);
        t1 = (TabLayout) findViewById(R.id.tabLayout);

        TabLayout.Tab bestComboTab = t1.newTab();
        bestComboTab.setText("Best Combo");
        t1.addTab(bestComboTab, true);
        TabLayout.Tab comboListTab = t1.newTab();
        comboListTab.setText("Combo List");
        t1.addTab(comboListTab);

        final TabLayout.Tab[] selectTab = {t1.getTabAt(1)};

        calculateBtn = (Button) findViewById(R.id.calculate_btn);
        doublearray = new int[comboRoutes.length][3];

        calculateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //reset everything when calculate button is click
                allPosibleRouteList.removeAll(allPosibleRouteList);
                bestComboRoute.removeAll(bestComboRoute);
                getAllPossibleRoute();
                checkBestCombo();

                //initialize value
                cloneComboRoute();

                int doubleArrayFirstIndex = checkComboList();

                listViewArray = new String[doubleArrayFirstIndex]; //initialize listViewArray
                bestComboArray = new String[bestComboRoute.size()];

                for (int i = 0; i < doublearray.length; i++) {

                    customComboRoute.add(new ComboRoute(doublearray[i][0], doublearray[i][1], doublearray[i][2])); //add all doublearray into customComboRoute list
                }

                for (int i = 0; i < doubleArrayFirstIndex; i++) {

                    listViewArray[i] = ElementString.getTypeElement(customComboRoute.get(i).getStage1()) + " -> " + ElementString.getTypeElement(customComboRoute.get(i).getStage2()) + " -> " + ElementString.getTypeElement(customComboRoute.get(i).getStage3());
                }


                //store best combo and combo list arrays in bundle
                bundle.putStringArray("comboListArray", listViewArray);
                bundle.putStringArray("bestComboArray", bestComboRoute.toArray(bestComboArray));

                t1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        Fragment fragment = null;

                        //get Fragment between BestComboFragment and ComboListFragment
                        switch (tab.getPosition()) {

                            case 0:
                                fragment = new BestComboFragment();
                                break;
                            case 1:
                                fragment = new ComboListFragment();
                                break;
                        }

                        fragment.setArguments(bundle);
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.fragmentLayout, fragment).commit();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

//               set tab position back and forth when calculate button is click
                if (selectTab[0].getPosition() == 1) {
                    selectTab[0].select();
                    selectTab[0] = t1.getTabAt(0);
                } else {
                    selectTab[0].select();
                    selectTab[0] = t1.getTabAt(1);
                }

                //remove all item in customComboRoute for the next click
                customComboRoute.removeAll(customComboRoute);

            }
        });

        //reset button
        resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] emptyArray = {};

                setSpinner();
                customComboRoute.removeAll(customComboRoute);
            }
        });

    }

    private int[] elementTypeId = new int[elements.length];

    //get user select and set the position
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        switch (arg0.getId()) {

            case R.id.spinner_1_1:
                userSelectItem.set(0, elementTypeId[position]);
                break;
            case R.id.spinner_1_2:
                userSelectItem.set(1, elementTypeId[position]);
                break;
            case R.id.spinner_1_3:
                userSelectItem.set(2, elementTypeId[position]);
                break;
            case R.id.spinner_2_1:
                userSelectItem.set(3, elementTypeId[position]);
                break;
            case R.id.spinner_2_2:
                userSelectItem.set(4, elementTypeId[position]);
                break;
            case R.id.spinner_2_3:
                userSelectItem.set(5, elementTypeId[position]);
                break;
            case R.id.spinner_3_1:
                userSelectItem.set(6, elementTypeId[position]);
                break;
            case R.id.spinner_3_2:
                userSelectItem.set(7, elementTypeId[position]);
                break;
            case R.id.spinner_3_3:
                userSelectItem.set(8, elementTypeId[position]);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String[] elementType = new String[elements.length];

    private int[] elementImages = {R.drawable.element_unknown, R.drawable.element_fire, R.drawable.element_water, R.drawable.element_ice, R.drawable.element_earth, R.drawable.element_thunder, R.drawable.element_wind,
            R.drawable.element_light, R.drawable.element_dark};

    //setting data for spinners
    private void setSpinner() {

        //initialize 9 spinners
        Spinner spinner_1_1 = (Spinner) findViewById(R.id.spinner_1_1);
        Spinner spinner_1_2 = (Spinner) findViewById(R.id.spinner_1_2);
        Spinner spinner_1_3 = (Spinner) findViewById(R.id.spinner_1_3);
        Spinner spinner_2_1 = (Spinner) findViewById(R.id.spinner_2_1);
        Spinner spinner_2_2 = (Spinner) findViewById(R.id.spinner_2_2);
        Spinner spinner_2_3 = (Spinner) findViewById(R.id.spinner_2_3);
        Spinner spinner_3_1 = (Spinner) findViewById(R.id.spinner_3_1);
        Spinner spinner_3_2 = (Spinner) findViewById(R.id.spinner_3_2);
        Spinner spinner_3_3 = (Spinner) findViewById(R.id.spinner_3_3);

        //applying listener to all spinners
        spinner_1_1.setOnItemSelectedListener(this);
        spinner_1_2.setOnItemSelectedListener(this);
        spinner_1_3.setOnItemSelectedListener(this);
        spinner_2_1.setOnItemSelectedListener(this);
        spinner_2_2.setOnItemSelectedListener(this);
        spinner_2_3.setOnItemSelectedListener(this);
        spinner_3_1.setOnItemSelectedListener(this);
        spinner_3_2.setOnItemSelectedListener(this);
        spinner_3_3.setOnItemSelectedListener(this);

        for (int i = 0; i < elements.length; i++) {

            elementType[i] = elements[i].getType();
            elementTypeId[i] = elements[i].getTypeId();
        }

        //creating adapter instance
        ImageAdapter adapter = new ImageAdapter(this, elementImages, elementType);

        //setting the adapter for the spinners
        spinner_1_1.setAdapter(adapter);
        spinner_1_2.setAdapter(adapter);
        spinner_1_3.setAdapter(adapter);
        spinner_2_1.setAdapter(adapter);
        spinner_2_2.setAdapter(adapter);
        spinner_2_3.setAdapter(adapter);
        spinner_3_1.setAdapter(adapter);
        spinner_3_2.setAdapter(adapter);
        spinner_3_3.setAdapter(adapter);

    }

    private void cloneComboRoute() {

        for (int i = 0; i < comboRoutes.length; i++) {

            route1.add(i, comboRoutes[i].getStage1());
            route2.add(i, comboRoutes[i].getStage2());
            route3.add(i, comboRoutes[i].getStage3());
        }
    }

    //set all spinner to default value
    private void setUserSelect() {

        userSelectItem.add(0, 0);
        userSelectItem.add(1, 0);
        userSelectItem.add(2, 0);
        userSelectItem.add(3, 0);
        userSelectItem.add(4, 0);
        userSelectItem.add(5, 0);
        userSelectItem.add(6, 0);
        userSelectItem.add(7, 0);
        userSelectItem.add(8, 0);

    }

    private List<ComboRoute> allPosibleRouteList = new ArrayList<ComboRoute>();
    boolean checkDupli = false; //check duplicate

    //get all combo routes calculation depends on the spinner selected
    private void getAllPossibleRoute() {

        for (int i = 0; i < userSelectItem.size(); i++) {

            if (userSelectItem.get(i) != 0) { //if index value equals 0 skip this index
                if (i < 3) { //userSelectItem[0,1,2]

                    for (int x = 3; x < userSelectItem.size(); x++) { //start from index 3
                        if (userSelectItem.get(x) != 0) { //if index value equals 0 skip this index
                            if (x < 6) {
                                for (int y = 6; y < userSelectItem.size(); y++) { //driver 3

                                    if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                        if (allPosibleRouteList.size() == 0) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        } else {
                                            for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                                //check if got duplicate
                                                if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                    checkDupli = true;
                                                    break;
                                                }
                                            }

                                            //if no duplicate then proceed to add to list
                                            if (!checkDupli) {
                                                allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                            }
                                            checkDupli = false;
                                        }
                                    }
                                }
                            } else {
                                for (int y = 3; y < userSelectItem.size() - 3; y++) { //driver 2

                                    if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                        if (allPosibleRouteList.size() == 0) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        } else {
                                            for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                                //check if got duplicate
                                                if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                    checkDupli = true;
                                                    break;
                                                }
                                            }

                                            //if no duplicate then proceed to add to list
                                            if (!checkDupli) {
                                                allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                            }
                                            checkDupli = false;
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else if (i < 6) { //userSelectItem[3,4,5]

                    for (int x = 0; x < userSelectItem.size() - 6; x++) { // driver 1
                        if (userSelectItem.get(x) != 0) { //if index value equals 0 skip this index

                            for (int y = 6; y < userSelectItem.size(); y++) { //driver 3

                                if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                    if (allPosibleRouteList.size() == 0) {
                                        allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                    } else {
                                        for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                            //check if got duplicate
                                            if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                checkDupli = true;
                                                break;
                                            }
                                        }

                                        //if no duplicate then proceed to add to list
                                        if (!checkDupli) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        }
                                        checkDupli = false;
                                    }
                                }
                            }
                        }
                    }

                    for (int x = 6; x < userSelectItem.size(); x++) { // driver 3
                        if (userSelectItem.get(x) != 0) { //if index value equals 0 skip this index

                            for (int y = 0; y < userSelectItem.size() - 6; y++) { //driver 1

                                if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                    if (allPosibleRouteList.size() == 0) {
                                        allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                    } else {
                                        for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                            //check if got duplicate
                                            if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                checkDupli = true;
                                                break;
                                            }
                                        }

                                        //if no duplicate then proceed to add to list
                                        if (!checkDupli) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        }
                                        checkDupli = false;
                                    }
                                }
                            }
                        }
                    }

                } else if (i < 9) { //userSelectItem[6,7,8]

                    for (int x = 0; x < userSelectItem.size() - 3; x++) { //start from driver [0 - 6]
                        if (userSelectItem.get(x) != 0) { //if index value equals 0 skip this index
                            if (x < 3) {
                                for (int y = 3; y < userSelectItem.size() - 3; y++) { //driver 2

                                    if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                        if (allPosibleRouteList.size() == 0) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        } else {
                                            for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                                //check if got duplicate
                                                if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                    checkDupli = true;
                                                    break;
                                                }
                                            }

                                            //if no duplicate then proceed to add to list
                                            if (!checkDupli) {
                                                allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                            }
                                            checkDupli = false;
                                        }
                                    }
                                }
                            } else {
                                for (int y = 0; y < userSelectItem.size() - 6; y++) { // driver 1

                                    if (userSelectItem.get(y) != 0) { //if index value equals 0 skip this index
                                        if (allPosibleRouteList.size() == 0) {
                                            allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                        } else {
                                            for (int q = 0; q < allPosibleRouteList.size(); q++) {
                                                //check if got duplicate
                                                if (allPosibleRouteList.get(q).getStage1() == userSelectItem.get(i) && allPosibleRouteList.get(q).getStage2() == userSelectItem.get(x) && allPosibleRouteList.get(q).getStage3() == userSelectItem.get(y)) {
                                                    checkDupli = true;
                                                    break;
                                                }
                                            }

                                            //if no duplicate then proceed to add to list
                                            if (!checkDupli) {
                                                allPosibleRouteList.add(new ComboRoute(userSelectItem.get(i), userSelectItem.get(x), userSelectItem.get(y))); //add to list
                                            }
                                            checkDupli = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    private List<String> bestComboRoute = new ArrayList<String>();

    //best combo calculation
    private void checkBestCombo() {

        for (int i = 0; i < allPosibleRouteList.size(); i++) {

            for (int j = 0; j < comboRoutes.length; j++) {

                if (allPosibleRouteList.get(i).getStage1() == comboRoutes[j].getStage1() && allPosibleRouteList.get(i).getStage2() == comboRoutes[j].getStage2() && allPosibleRouteList.get(i).getStage3() == comboRoutes[j].getStage3()) {

                    bestComboRoute.add(ElementString.getTypeElement(comboRoutes[j].getStage1()) + " -> " + ElementString.getTypeElement(comboRoutes[j].getStage2()) + " -> " + ElementString.getTypeElement(comboRoutes[j].getStage3()));
                    break;
                }

            }
        }
    }

    //combo list calculation
    private int checkComboList() {

        userSelect = new Integer[userSelectItem.size()];
        userSelect = userSelectItem.toArray(userSelect);
        int counter = 0, doubleArrayFirstIndex = 0;
        checkStage1 = checkStage2 = checkStage3 = false;

        for (int i = 0; i < comboRoutes.length; i++) { //check all 25 combo routes

            while (!checkStage1) { //check first stage of combo route

                while (counter < userSelectItem.size()) { //loop all the user select item

                    if (userSelect[counter] == route1.get(i)) { //check if user select item is equal stage 1 combo route

                        doublearray[doubleArrayFirstIndex][0] = route1.get(i); //store first stage of the route in double array
                        checkStage1 = true; // set to true to bypass checkStage1
                        checkStage3 = checkStage2 = false; // check stage 2 and 3 set to false in case checkstage 2 and 3 set to true
                        counter = 10; //bypass the counter

                    } else {

                        counter++; //increment till counter is 8
                        checkStage3 = checkStage2 = true;//skip stage 2 and 3 because userSelect don't exist in the comboroute 1
                    }
                }
                checkStage1 = true; //in case all userSelect dont exist and bypass checkStage 1
                counter = 0; //reset counter to zero for the next stage use
            }

            while (!checkStage2) {

                while (counter < userSelectItem.size()) {

                    if (userSelect[counter] == route2.get(i)) {

                        doublearray[doubleArrayFirstIndex][1] = route2.get(i);
                        checkStage2 = true;
                        checkStage3 = false; // check stage 3 set to false incase checkstage3 set to true
                        counter = 10;
                    } else {
                        checkStage3 = true;//skip stage 3 because userSelect dont exist in the comboroute 2
                        counter++;
                    }
                }
                checkStage2 = true;
                counter = 0;
            }

            while (!checkStage3) {

                while (counter < userSelectItem.size()) {

                    if (userSelect[counter] == route3.get(i)) {

                        doublearray[doubleArrayFirstIndex][2] = route3.get(i);
                        checkStage3 = true;
                        counter = 10;
                        doubleArrayFirstIndex++; // increment by 1
                    } else {

                        counter++;
                    }
                }
                checkStage3 = true;
                counter = 0;
            }

            checkStage1 = checkStage2 = checkStage3 = false; //reset all checkStage to false
        }

        return doubleArrayFirstIndex;
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

        if (id == R.id.picture_btn) {

            i = new Intent(this, ComboChartActivity.class);
        } else if (id == R.id.about_btn) {

            i = new Intent(this, ComboChartActivity.class);
        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
