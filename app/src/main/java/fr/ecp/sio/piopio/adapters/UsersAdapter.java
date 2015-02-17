package fr.ecp.sio.piopio.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ecp.sio.piopio.R;
import fr.ecp.sio.piopio.model.User;

/**
 * Created by Diana on 05/12/2014.
 */

//optimization of visualization of users

public class UsersAdapter extends BaseAdapter {

    private List<User> mData;

    @Override
    public int getCount() {
        if(mData==null)
            return 0;
        return mData.size();
    }

    @Override
    public User getItem(int position) {
        if(mData==null)
            return null;
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        }
        User user =getItem(position);
        TextView handleView=(TextView)convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());
        TextView statusView=(TextView)convertView.findViewById(R.id.status);

        switch (user.getStatus()){
            case "online" : statusView.setText(R.string.online);  break;
            case "offline" : statusView.setText(R.string.offline);      break;
                default: statusView.setText("");                              break;
        }
        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getProfilePicture()).into(profilePictureView);
        return convertView;
    }

    public List<User> getmData() {
        return mData;
    }

    public void setmData(List<User> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void dataChanged(){
        notifyDataSetChanged();
    }
}
