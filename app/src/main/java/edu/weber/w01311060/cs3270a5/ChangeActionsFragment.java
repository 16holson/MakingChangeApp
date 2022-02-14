package edu.weber.w01311060.cs3270a5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeActionsFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private Button newAmountBtn;
    private Button startOverBtn;
    private TextView correctCount;
    private int count;
    private onNewAmount mCallBack;

    public interface onNewAmount
    {
        void newAmountClick();
        void startOver();
    }

    public ChangeActionsFragment()
    {
        count = 0;
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallBack = (onNewAmount) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement onNewActivity");
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeActionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeActionsFragment newInstance(String param1, String param2)
    {
        ChangeActionsFragment fragment = new ChangeActionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_change_actions, container, false);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putInt("count", count);
        prefEdit.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        newAmountBtn = root.findViewById(R.id.newAmount);
        startOverBtn = root.findViewById(R.id.startOver);
        correctCount = root.findViewById(R.id.correctCount);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        count = prefs.getInt("count", 0);
        displayCount();
        newAmountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Change", "Clicked newAmount");
                mCallBack.newAmountClick();
            }
        });
        startOverBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Change", "Clicked StartOver");
                mCallBack.startOver();
            }
        });
    }
    public void incrementCount()
    {
        count++;
        displayCount();
    }
    public void displayCount()
    {
        correctCount.setText("" + count);
    }
    public void resetCount()
    {
        count = 0;
        displayCount();
    }
}