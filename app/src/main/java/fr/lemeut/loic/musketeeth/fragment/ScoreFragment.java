package fr.lemeut.loic.musketeeth.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.activity.MainActivity;
import fr.lemeut.loic.musketeeth.classes.Badge;
import fr.lemeut.loic.musketeeth.classes.GestionScore;
import fr.lemeut.loic.musketeeth.classes.RecyclerViewAdapter;
import fr.lemeut.loic.musketeeth.classes.ScoreRowItem;
import fr.lemeut.loic.musketeeth.sqlbadges.Badges;
import fr.lemeut.loic.musketeeth.sqlbadges.BadgesDataSource;
import fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavage;
import fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavageDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private  View view;

    private ScoreLavageDataSource datasource;
    String score;
    String Ts_dateScore;

    RecyclerView recyclerView;
    ArrayList<ScoreRowItem> itemsList = new ArrayList<>();
    RecyclerViewAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);
        super.onCreate(savedInstanceState);

        // Affichage de la BDD dans un listView sur la home de l'application
        datasource = new ScoreLavageDataSource(getActivity());
        datasource.open();
        // Recupere la liste de tous les scores
        List<ScoreLavage> values = datasource.getAllComments();

        // Fabrique des texView pour afficher les scores
        //LinearLayout layout = new LinearLayout(getActivity());


        // Boucle sur tous les scores
        for (int i = 0; i < values.size(); i++) {

            score = values.get(i).getscore();
            Ts_dateScore = values.get(i).getTs_dateScore();

            ScoreRowItem item = new ScoreRowItem();
            item.setTitle(score + " points");
            item.setDate(Ts_dateScore);

            itemsList.add(item);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), itemsList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
