package edu.weber.w01311060.cs3270a5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements MaxChangeFragment.onSave,
        ChangeActionsFragment.onNewAmount, ChangeButtonsFragment.onChangeClick,
        OverChangeDialog.onClickOk, TimesUpDialog.onClickOk, WinnerDialog.onClickOk,
        ChangeResultsFragment.onCorrect
{
    private ChangeResultsFragment cr;
    private ChangeActionsFragment ca;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.ChangeResultsFragmentContainer, new ChangeResultsFragment(), "changeResultFrag")
                .replace(R.id.ChangeButtonFragmentContainer, new ChangeButtonsFragment(), "changeButtonsFrag")
                .replace(R.id.ChangeActionFragmentContainer, new ChangeActionsFragment(), "changeActionFrag")
                .commit();
    }

    @Override
    public void saveUserAmount(BigDecimal userAmount)
    {
        fm.popBackStack();
        if(cr == null)
        {

            cr = (ChangeResultsFragment) fm.findFragmentByTag("changeResultFrag");
        }
        if(cr != null)
        {
            cr.updateUserAmount(userAmount);
        }
    }

    @Override
    public void newAmountClick()
    {
        if(cr == null)
        {
            cr = (ChangeResultsFragment) fm.findFragmentById(R.id.ChangeResultsFragmentContainer);
        }
        if(cr != null)
        {
            cr.newAmount();
        }
    }

    @Override
    public void startOver()
    {
        if(cr == null)
        {
            cr = (ChangeResultsFragment) fm.findFragmentById(R.id.ChangeResultsFragmentContainer);
        }
        if(cr != null)
        {
            cr.startOver();
        }
    }

    @Override
    public void updateChange(BigDecimal change)
    {
        if(cr == null)
        {
            cr = (ChangeResultsFragment) fm.findFragmentById(R.id.ChangeResultsFragmentContainer);
        }
        if(cr != null)
        {
            cr.getChange(change);
        }
    }

    @Override
    public void correct()
    {
        if(ca == null)
        {
            ca = (ChangeActionsFragment) fm.findFragmentById(R.id.ChangeActionFragmentContainer);
        }
        if(ca != null)
        {
            ca.incrementCount();
        }
    }

    @Override
    public void onReset()
    {
        if(ca == null)
        {
            ca = (ChangeActionsFragment) fm.findFragmentById(R.id.ChangeActionFragmentContainer);
        }
        if(ca != null)
        {
            ca.resetCount();
        }
    }
}