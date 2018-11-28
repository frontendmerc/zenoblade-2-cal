package com.example.user.xenoblade3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListViewAdapter2 extends BaseAdapter {

    private Context context;
    private int[] images;
    private String[] elements;
    private LayoutInflater layoutInflater;

    public ImageListViewAdapter2(Context context, int[] images, String[] element){

        this.context = context;
        this.images = images;
        this.elements = element;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return elements.length;
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

        convertView = layoutInflater.inflate(R.layout.image_list_view_2, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.comboElementImage);
        TextView textView = (TextView) convertView.findViewById(R.id.comboElementText);

        imageView.setImageResource(e.getElementImage(elements[position]));
        textView.setText(elements[position]);
        return convertView;
    }
}
