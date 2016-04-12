package sandervankasteel.nl.home_automation;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by sander on 5/21/15.
 */
public class Lights {

    private List<Light> lightList;
    private Context context;

    // Constructor
    public Lights(Context context)
    {
        this.context = context;
    }

    public void fetchData(final VolleyCallBack callBack)
    {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://10.13.37.213:8000/api/lights/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBack.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Home-automation", "Volley HTTP get lights request failed!");
            }
        });

        queue.add(stringRequest);

    }

    public void add(String name, Boolean status){
        this.lightList.add(new Light(name, status));
    }

    public interface VolleyCallBack{
        void onSuccess(String result);
    }
}
