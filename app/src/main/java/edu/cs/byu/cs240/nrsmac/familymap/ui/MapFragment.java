package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cs.byu.cs240.nrsmac.familymap.R;
import edu.cs.byu.cs240.nrsmac.familymap.model.DataCache;
import edu.cs.byu.cs240.nrsmac.familymap.model.Event;
import edu.cs.byu.cs240.nrsmac.familymap.model.Person;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MAIN_PERSON_ID = "param1";

    // TODO: Rename and change types of parameters
    private String mUserPersonId;

    private MapView mMapView;
    private ImageView mGenderImage;
    private TextView mNameText;
    private TextView mEventPlaceText;
    private TextView mEventYearText;
    private TextView mEventTypeText;


    private DataCache dataCache;
    private String mPersonId;
    private Person mPerson;
    private Event mCurrentEvent;

    private GoogleMap mMap;


    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String personId) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_PERSON_ID, personId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserPersonId = getArguments().getString(MAIN_PERSON_ID);
            //TODO populate members
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        Iconify.with(new FontAwesomeModule());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mNameText = view.findViewById(R.id.infoPanelName);
        mEventPlaceText = view.findViewById(R.id.infoPanelEventPlace);
        mEventTypeText = view.findViewById(R.id.infoPanelEventType);
        mEventYearText = view.findViewById(R.id.infoPanelEventYear);

        mGenderImage = view.findViewById(R.id.genderImage);

        return view;
    }

    private void updateView(){
        mNameText.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
        mEventPlaceText.setText(mCurrentEvent.getCity()+ ", " + mCurrentEvent.getCountry());
        mEventTypeText.setText(mCurrentEvent.getEventType().toUpperCase());
        mEventYearText.setText(String.valueOf(mCurrentEvent.getYear()));

        Drawable maleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                colorRes(R.color.male_icon).sizeDp(40);
        Drawable femaleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                colorRes(R.color.female_icon).sizeDp(40);

        if(mPerson.getGender().equals("m")){
            //TODO update image with male icon
            mGenderImage.setImageDrawable(maleIcon);
        } else if(mPerson.getGender().equals("f")){
            //TODO update image with female icon
            mGenderImage.setImageDrawable(femaleIcon);

        }

        double latitude = mCurrentEvent.getLatitude();
        double longitude = mCurrentEvent.getLongitude();

        // Add a marker in Sydney and move the camera
        LatLng eventPlace = new LatLng(latitude, longitude);
        MarkerOptions currentMarker = new MarkerOptions().position(eventPlace).title(mPerson.getFirstName() + " " + mPerson.getLastName() + ":" + mCurrentEvent.getEventType());
        mMap.addMarker(currentMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventPlace));


    }

    public void updateInfo(Person person, Event event){
        mPerson = person;
        mCurrentEvent = event;


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateView();
    }


}