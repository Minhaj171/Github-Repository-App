package com.example.githubrepoapp.base;

import java.text.ParseException;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public interface IUserSelectedDate {
    void getUpdatedDate(String date, String tag) throws ParseException;
}
