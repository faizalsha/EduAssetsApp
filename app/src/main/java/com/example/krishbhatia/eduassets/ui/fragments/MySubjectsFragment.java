package com.example.krishbhatia.eduassets.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.pojo.Subject;
import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;


public class MySubjectsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<Subject> subjectList;


    private String mParam1;
    private String mParam2;


    public MySubjectsFragment() {
    }


    public static MySubjectsFragment newInstance(String param1, String param2) {
        MySubjectsFragment fragment = new MySubjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
//        TextView textView=container.findViewById(R.id.mycourseText);
//        textView.setText("My courses");
        View view = inflater.inflate(R.layout.fragment_my_subjects, container, false);

        recyclerView = view.findViewById(R.id.mySubjectRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList = new ArrayList<>();

        subjectList.add(new Subject("Physics", "B.tech 1st sem"));
        subjectList.add(new Subject("Maths", "B.tech 1st sem"));
        subjectList.add(new Subject("Biology", "B.tech 2nd sem"));
        subjectList.add(new Subject("English", "B.tech 1st sem"));



        //creating recyclerview adapter
        SubjectAdapter adapter = new SubjectAdapter(getContext(), subjectList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
