package edu.weber.w01311060.cs3270a5;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaxChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaxChangeFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private Button saveBtn;
    private onSave mCallBack;
    private EditText maxChangeAmount;

    public interface onSave
    {
        void saveUserAmount(BigDecimal userAmount);
    }

    public MaxChangeFragment()
    {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaxChangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaxChangeFragment newInstance(String param1, String param2)
    {
        MaxChangeFragment fragment = new MaxChangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallBack = (onSave) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement ChangeResultsFragment.saveUserAmount");
        }
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
        return root = inflater.inflate(R.layout.fragment_max_change, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        saveBtn = root.findViewById(R.id.saveBtn);
        maxChangeAmount = root.findViewById(R.id.maxChangeAmount);

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCallBack.saveUserAmount(BigDecimal.valueOf(Double.parseDouble(maxChangeAmount.getText().toString())));
            }
        });
    }
}