package sandervankasteel.nl.home_automation;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment LightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightFragment newInstance() {
        LightFragment fragment = new LightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Lights lights = new Lights(getActivity().getBaseContext());
        lights.fetchData(new Lights.VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                final List<Light> array = new ArrayList<>();

                try {
                    JSONArray lights = new JSONArray(result);

                    for (int i = 0; i < lights.length(); i++) {
                        JSONObject currentLight = lights.getJSONObject(i);
                        array.add(new Light(currentLight.getString("name"), currentLight.getBoolean("status")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView lv = (ListView) getView().findViewById(R.id.listLights);
                final LightsAdapter adapter = new LightsAdapter(getActivity(), array);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Light selectedLight = array.get(position);

                        if (selectedLight.getState()) {
                            selectedLight.setState(false, selectedLight, getActivity().getApplicationContext());
                        } else {
                            selectedLight.setState(true, selectedLight, getActivity().getApplicationContext());
                        }

                        adapter.dataChanged();

                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                "Item " + (position + 1) + ": " + parent.getCount(),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                lv.setAdapter(adapter);
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
