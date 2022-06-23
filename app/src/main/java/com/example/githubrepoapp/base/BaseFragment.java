package com.example.githubrepoapp.base;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.githubrepoapp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public abstract class BaseFragment extends Fragment {
    private KProgressHUD progressHUD;
    public static boolean isPositive = true;
    public static boolean isFirstTime = true;
    public static boolean isErrorOccurred = true;

    public abstract void showErrorLayout(String message, boolean isError);

    public abstract void fetchDataFromViewModel();

    @Override
    public void onResume() {
        super.onResume();
        progressHUD = KProgressHUD.create(requireContext());
    }

    protected void showDialog() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    protected void showSnackBar(String text, boolean positive) {
        Snackbar snackbar = Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG);
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        View snackBarLayout = snackbar.getView();
        TextView textView = snackBarLayout.findViewById(R.id.snackbar_text);
        if (positive) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success, 0, 0, 0);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
        }
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        snackbar.show();
    }

    public void showCalendar(IUserSelectedDate IUserSelectedDate, String tag) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = year1 + "-" + month1 + "-" +  day1;
            try {
                IUserSelectedDate.getUpdatedDate(date, tag);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }



}
