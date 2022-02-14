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

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeButtonsFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private onChangeClick mCallBack;
    private BigDecimal change;

    private Button fiftybtn;
    private Button twentybtn;
    private Button tenbtn;
    private Button fivebtn;
    private Button onebtn;
    private Button fiftyCentbtn;
    private Button twentyfiveCentbtn;
    private Button tenCentbtn;
    private Button fiveCentbtn;
    private Button oneCentbtn;

    public ChangeButtonsFragment()
    {
        // Required empty public constructor
    }

    public interface onChangeClick
    {
        void updateChange(BigDecimal change);
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mCallBack = (onChangeClick) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement onChangeClick");
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeButtonsFragment newInstance(String param1, String param2)
    {
        ChangeButtonsFragment fragment = new ChangeButtonsFragment();
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
        return root = inflater.inflate(R.layout.fragment_change_buttons, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        fiftybtn = root.findViewById(R.id.fifty);
        twentybtn = root.findViewById(R.id.twenty);
        tenbtn = root.findViewById(R.id.ten);
        fivebtn = root.findViewById(R.id.five);
        onebtn = root.findViewById(R.id.one);
        fiftyCentbtn = root.findViewById(R.id.fiftyCent);
        twentyfiveCentbtn = root.findViewById(R.id.twentyFiveCent);
        tenCentbtn = root.findViewById(R.id.tenCent);
        fiveCentbtn = root.findViewById(R.id.fiveCent);
        oneCentbtn = root.findViewById(R.id.oneCent);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.fifty:
                        change = new BigDecimal(50);
                        break;
                    case R.id.twenty:
                        change = new BigDecimal(20);
                        break;
                    case R.id.ten:
                        change = new BigDecimal(10);
                        break;
                    case R.id.five:
                        change = new BigDecimal(5);
                        break;
                    case R.id.one:
                        change = new BigDecimal(1);
                        break;
                    case R.id.fiftyCent:
                        change = new BigDecimal(.50);
                        break;
                    case R.id.twentyFiveCent:
                        change = new BigDecimal(.25);
                        break;
                    case R.id.tenCent:
                        change = new BigDecimal(.10);
                        break;
                    case R.id.fiveCent:
                        change = new BigDecimal(.05);
                        break;
                    case R.id.oneCent:
                        change = new BigDecimal(.01);
                        break;
                    default:
                        change = new BigDecimal(0);
                        break;
                }
                mCallBack.updateChange(change);
            }
        };

        fiftybtn.setOnClickListener(listener);
        twentybtn.setOnClickListener(listener);
        tenbtn.setOnClickListener(listener);
        fivebtn.setOnClickListener(listener);
        onebtn.setOnClickListener(listener);
        fiftyCentbtn.setOnClickListener(listener);
        twentyfiveCentbtn.setOnClickListener(listener);
        tenCentbtn.setOnClickListener(listener);
        fiveCentbtn.setOnClickListener(listener);
        oneCentbtn.setOnClickListener(listener);

    }
}