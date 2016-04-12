package sandervankasteel.nl.home_automation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sander on 5/26/15.
 */
public class LightsAdapter extends ArrayAdapter<Light>
{

        public LightsAdapter(Context context, List<Light> lights)
        {
            super(context, 0, lights);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Light light = getItem(position);


            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            }

            TextView lightName = (TextView) convertView.findViewById(R.id.lightName);
            ImageView lightStatus = (ImageView) convertView.findViewById(R.id.lightStatus);
//            ImageView test = (ImageView) convertView.findViewById(R.id.lightStatus);

            lightName.setText(light.getName());
            if(light.getState() == true)
            {
                lightStatus.setImageResource(R.drawable.light_on);
            } else {
                lightStatus.setImageResource(R.drawable.light_off);
            }

            return convertView;
        }

        public void dataChanged()
        {
            notifyDataSetChanged();
        }
}


