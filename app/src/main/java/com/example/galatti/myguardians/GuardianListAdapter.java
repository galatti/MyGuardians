package com.example.galatti.myguardians;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rodrigo Galatti on 05/03/2015.
 */
public class GuardianListAdapter extends BaseAdapter {
    private ArrayList<Guardian> guardians;
    private LayoutInflater layoutInflater;

    public GuardianListAdapter(Context aContext, ArrayList<Guardian> guardians) {
        this.guardians = guardians;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return guardians.size();
    }

    @Override
    public Object getItem(int position) {
        return guardians.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void upDateEntries(ArrayList<Guardian> entries) {
        guardians = entries;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.guardians_layout, null);
            holder = new ViewHolder();
            holder.viewGuardianClass = (TextView) convertView.findViewById(R.id.guardianClass);
            holder.viewLevel = (TextView) convertView.findViewById(R.id.level);
            holder.viewTimePlayed = (TextView) convertView.findViewById(R.id.timePlayed);
            holder.viewStats = (TextView) convertView.findViewById(R.id.stats);
            holder.viewEmblemPath = (ImageView) convertView.findViewById(R.id.emblemPath);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.viewGuardianClass.setText(guardians.get(position).getGuardianClass());
        holder.viewLevel.setText(guardians.get(position).getLevel());
        holder.viewTimePlayed.setText(guardians.get(position).getTimePlayed());
        holder.viewStats.setText(guardians.get(position).getStats());
        if (holder.viewEmblemPath != null) {
            String url = guardians.get(position).getEmblemPath();
            new ImageDownloaderTask(holder.viewEmblemPath).execute(url);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView viewGuardianClass;
        TextView viewLevel;
        TextView viewTimePlayed;
        TextView viewStats;
        ImageView viewEmblemPath;
    }
}