package com.example.user.xenoblade3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

    Context context;
    int[] image;
    String[] type;
    LayoutInflater layoutInflater;

    public ImageAdapter(Context context, int[] image, String[] type){

        this.context = context;
        this.image = image;
        this.type = type;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return image.length;
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

        convertView = layoutInflater.inflate(R.layout.image_spinner, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.spinnerImage);
        TextView textView = (TextView) convertView.findViewById(R.id.spinnerText);
        imageView.setImageResource(image[position]);
        textView.setText(type[position]);
        return convertView;
    }
}
