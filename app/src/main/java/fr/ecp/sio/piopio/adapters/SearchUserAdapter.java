package fr.ecp.sio.piopio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.ecp.sio.piopio.R;
import fr.ecp.sio.piopio.model.User;

/**
 * Created by Diana on 22/01/2015.
 */
public class SearchUserAdapter extends ArrayAdapter<User> {


    public SearchUserAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }
        // Lookup view for data population
        TextView uName = (TextView) convertView.findViewById(R.id.handle);
        ImageView uPic = (ImageView) convertView.findViewById(R.id.profile_picture_search);
        // Populate the data into the template view using the data object
        uName.setText(user.getHandle());
        //tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }
}
