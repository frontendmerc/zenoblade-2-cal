package com.example.user.xenoblade3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageListViewAdapter extends BaseAdapter {

    Context context;
    int[] image;
    String[] element;
    LayoutInflater layoutInflater;

    public ImageListViewAdapter(Context context, int[] image, String[] element){

        this.context = context;
        this.image = image;
        this.element = element;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return element.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Element e = new Element();

        convertView = layoutInflater.inflate(R.layout.image_list_view, null);

        ImageView imageView1 = (ImageView) convertView.findViewById(R.id.firstElement);
        ImageView imageView3 = (ImageView) convertView.findViewById(R.id.thirdElement);
        ImageView imageView2 = (ImageView) convertView.findViewById(R.id.secondElement);

        imageView1.setImageResource(e.getElementImage(element[position].split(" -> ")[0]));
        imageView2.setImageResource(e.getElementImage(element[position].split(" -> ")[1]));
        imageView3.setImageResource(e.getElementImage(element[position].split(" -> ")[2]));

        return convertView;
    }

}
