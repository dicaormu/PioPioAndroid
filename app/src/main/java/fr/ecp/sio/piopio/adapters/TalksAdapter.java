package fr.ecp.sio.piopio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.ecp.sio.piopio.R;
import fr.ecp.sio.piopio.model.Talk;

/**
 * Created by Diana on 05/12/2014.
 */

//optimization of visualization of talks (pios)

public class TalksAdapter extends BaseAdapter {

    private List<Talk> mData;

    @Override
    public int getCount() {
        if(mData==null)
            return 0;
        return mData.size();
    }

    @Override
    public Talk getItem(int position) {
        if(mData==null)
            return null;
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(getItem(position)!= null)
            if(getItem(position).getId()!=null)
                return getItem(position).getId().hashCode();
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){// we re-uses view
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        }
        Talk talk =getItem(position);
        TextView handleView=(TextView)convertView.findViewById(R.id.handle);
        handleView.setText(talk.getContent());
        TextView statusView=(TextView)convertView.findViewById(R.id.status);
        statusView.setText(talk.getDate());
        return convertView;
    }

    public List<Talk> getmData() {
        return mData;
    }

    public void setmData(List<Talk> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}
