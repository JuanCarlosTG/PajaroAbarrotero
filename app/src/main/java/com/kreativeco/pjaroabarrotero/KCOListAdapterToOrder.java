package com.kreativeco.pjaroabarrotero;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kreativeco on 06/03/15.
 */
public class KCOListAdapterToOrder extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<KCOListItems> items;

    public KCOListAdapterToOrder(Activity activity, ArrayList<KCOListItems> items)
    {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View vi=contentView;

        if(contentView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_items_to_orders, null);
        }

        KCOListItems item = items.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.imagenOrder);
        int imageResource = activity.getResources().getIdentifier(item.getRutaImagen(), null, activity.getPackageName());
        image.setImageDrawable(activity.getResources().getDrawable(imageResource));

        TextView nombre = (TextView) vi.findViewById(R.id.nombreOrder);
        nombre.setText(item.getNombre());

        //TextView tipo = (TextView) vi.findViewById(R.id.tipo);
        //tipo.setText(item.getTipo());

        return vi;
    }
}
