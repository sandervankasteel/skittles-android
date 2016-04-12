package sandervankasteel.nl.home_automation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sander on 5/22/15.
 */
public class Light {

    private String name;
    private Boolean state;
    private int icon;

    public Light(String name, Boolean state)
    {
        this.name = name;
        this.state = state;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setState(Boolean state, final Light light, Context context)
    {
        this.state = state;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.13.37.213:8000/api/light/update/";
        StringRequest updateLight = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("Home-automation", "Response van server is " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Home-automation", "Update failed because " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lightname" ,light.getName());
                params.put("status", light.getState().toString());

                Log.d("Home-automation", "Params are : " + params.toString());
                return params;
            }
        };

        queue.add(updateLight);
    }

    public Boolean getState()
    {
        return this.state;
    }

    public int getIcon() {
        return this.icon;
    }

    @Override
    public String toString() {
//        String str = this.name;
//        return str.substring(0,1).toUpperCase() + str.substring(1);
        return this.name;
    }
}
