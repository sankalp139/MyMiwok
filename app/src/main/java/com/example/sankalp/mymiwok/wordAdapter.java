package com.example.sankalp.mymiwok;

/**
 * Created by Sankalp on 6/29/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class wordAdapter extends ArrayAdapter<word> {
    private int  colorId;

    public wordAdapter(Activity number, ArrayList<word> words,int mcolorId) {
        super(number, 0, words);
        colorId=mcolorId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final word currentword = getItem(position);

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        if (currentword.getimg() == 0) {
            iconView.setVisibility(convertView.GONE);

        } else
            iconView.setImageResource(currentword.getimg());
        // Get the {@link AndroidFlavor} object located at this position in the list

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView english = (TextView) listItemView.findViewById(R.id.text1);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        english.setText(currentword.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.text2);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentword.getDefaultTranslation());
// Set the theme color for the list item
        View textcontainer=listItemView.findViewById(R.id.first);
        int col=ContextCompat.getColor(getContext(),colorId);
        textcontainer.setBackgroundColor(col);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;

        // return getView(position, convertView, parent);
    }
}


