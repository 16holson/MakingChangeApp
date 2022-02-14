package edu.weber.w01311060.cs3270a5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeResultsFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private FragmentManager fm;
    private TextView timerText;
    private BigDecimal totalUserChange;
    private DecimalFormat format = new DecimalFormat("$00.00");
    private TextView userChange;
    private CountDownTimer timer;
    private TextView changeToMake;
    private BigDecimal topRange;
    private MaxChangeFragment maxChangeFragment = new MaxChangeFragment();
    private boolean reset = false;
    private BigDecimal amount;
    private BigDecimal totalChange;
    private onCorrect mCallBack;

    public interface onCorrect
    {
        void correct();
        void onReset();
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mCallBack = (onCorrect) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement onCorrect");
        }
    }

    public ChangeResultsFragment()
    {
        totalUserChange = new BigDecimal(0);
        topRange = new BigDecimal(50);
        totalChange = new BigDecimal(0);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeResultsFragment newInstance(String param1, String param2)
    {
        ChangeResultsFragment fragment = new ChangeResultsFragment();
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
        fm = getParentFragmentManager();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.changeresults, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //we can implement this function in the other fragments if they are responsible for implementing the funcitonality of the menu item
        switch(item.getItemId())
        {
            case R.id.zeroCorrectCount:
                mCallBack.onReset();
                timer.cancel();
                return true;
            case R.id.setChangeMax:
                timer.cancel();
                amount = new BigDecimal(0);
                totalChange = new BigDecimal(0);
                fm.beginTransaction()
                        .replace(R.id.ChangeResultsFragmentContainer, maxChangeFragment, "maxChangeFrag")
                        .hide(fm.findFragmentByTag("changeButtonsFrag"))
                        .hide(fm.findFragmentByTag("changeActionFrag"))
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_change_results, container, false);
        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        userChange = root.findViewById(R.id.userChange);
        changeToMake = root.findViewById(R.id.changeToMake);
        timerText = root.findViewById(R.id.timerText);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        totalChange = BigDecimal.valueOf(Double.parseDouble(prefs.getString("totalChange", String.valueOf(0))));
        amount = BigDecimal.valueOf(Double.parseDouble(prefs.getString("amount", String.valueOf(0))));
        if(amount.compareTo(new BigDecimal(0)) == 0)
        {
            updateRandomTotal();
        }
        else
        {
            changeToMake.setText(format.format(amount) + "");
            userChange.setText(format.format(totalChange));
        }
        startTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("totalChange", totalChange.toString());
        prefEdit.putString("amount", amount.toString());
        prefEdit.commit();
    }

    public void startTimer()
    {
        if(!reset)
        {
            resetTimer();
            reset = false;
        }
        timer = new CountDownTimer(30000, 1000)
        {
            @Override
            public void onTick(long l)
            {
                timerText.setText(l/1000 + "");
            }

            @Override
            public void onFinish()
            {
                TimesUpDialog dialog = new TimesUpDialog();
                dialog.show(getActivity().getSupportFragmentManager(), "TimesUpDialog");
            }
        }.start();
    }
    public void resetTimer()
    {
        if(timer != null)
        {
            timer.cancel();
            reset = true;
            startTimer();
        }
    }
    public void updateUserTotal()
    {
        userChange.setText(format.format(totalChange));

    }
    public void updateRandomTotal()
    {
        amount = BigDecimal.valueOf(Math.random() * (topRange.doubleValue()));
        changeToMake.setText(format.format(amount) + "");
    }
    public void updateUserAmount(BigDecimal userAmount)
    {
        topRange = userAmount; //for the save button look to set the change total so far to $00.00 instead of .00
        timer.cancel();
    }
    public void newAmount()
    {
        updateRandomTotal();
        resetTimer();
        totalChange = new BigDecimal(0);
        updateUserTotal();
    }
    public void startOver()
    {
        totalChange = new BigDecimal(0);
        updateUserTotal();
        resetTimer();
    }
    public void getChange(BigDecimal change)
    {
        totalChange = totalChange.add(change);
        totalChange = totalChange.setScale(2, RoundingMode.HALF_UP);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        if(totalChange.equals(amount))
        {
            timer.cancel();
            WinnerDialog dialog = new WinnerDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "WinnerDialog");
            mCallBack.correct();
        }
        else if(totalChange.compareTo(amount) == 1)
        {
            timer.cancel();
            OverChangeDialog dialog = new OverChangeDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "OverChangeDialog");
        }
        updateUserTotal();
    }
}