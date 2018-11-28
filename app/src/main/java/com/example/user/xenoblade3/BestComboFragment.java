package com.example.user.xenoblade3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BestComboFragment extends Fragment {

    private final Bundle bundle = new Bundle();
    private String[] arrays;
    private ListView lv;
    private int[] elementImages = {R.drawable.element_unknown, R.drawable.element_fire, R.drawable.element_water, R.drawable.element_ice, R.drawable.element_earth, R.drawable.element_thunder, R.drawable.element_wind,
            R.drawable.element_light, R.drawable.element_dark};

    public BestComboFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrays = getArguments().getStringArray("bestComboArray");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_best_combo_fragment, container, false);
        ListView lv = (ListView) v.findViewById(R.id.listviewsss);

        ImageListViewAdapter aa = new ImageListViewAdapter(getContext(), elementImages, arrays);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getContext(), ComboDetailActivity.class);
                i.putExtra("comboRoute", arrays[position]);
                startActivity(i);
            }
        });

        return v;
    }
}
