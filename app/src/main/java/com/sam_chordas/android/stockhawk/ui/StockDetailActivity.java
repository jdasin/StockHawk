package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StockDetailActivity extends AppCompatActivity implements DatePickerDialogFragment.DatePickerDialogHandler {
    private static final int START_DATE_REFERENCE = 1;
    private static final int END_DATE_REFERENCE = 2;
    TextView startDateTextView;
    TextView endDateTextView;
    private Date endDate;
    private Date startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        fillStockValues();
        startDateTextView = (TextView) findViewById(R.id.startDate);
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setReference(START_DATE_REFERENCE)
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                dpb.show();
            }
        });
        endDateTextView = (TextView) findViewById(R.id.endDate);
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setReference(END_DATE_REFERENCE)
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                dpb.show();
            }
        });
        DateFormat format = DateFormat.getDateInstance();
        Calendar c = Calendar.getInstance();
        endDate = c.getTime();
        endDateTextView.setText(getDateFormat().format(endDate));
        c.add(Calendar.MONTH, -1);
        startDate = c.getTime();
        startDateTextView.setText(getDateFormat().format(startDate));
    }
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        switch (reference)
        {
            case START_DATE_REFERENCE:
                startDate = buildDate(year, monthOfYear, dayOfMonth);
                startDateTextView.setText(getDateFormat().format(startDate));
                break;
            case END_DATE_REFERENCE:
                endDate = buildDate(year, monthOfYear, dayOfMonth);
                endDateTextView.setText(getDateFormat().format(endDate));
                break;
        }

    }
    public void fillStockValues() {
        String[] labels = {"test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2"};
        float[] values = {1f, 2f,1f, 2f,1f, 2f,1f, 2f,1f, 2f};
        LineSet dataSet = new LineSet(labels, values);
        LineChartView chartView = (LineChartView) findViewById(R.id.linechart);
        dataSet.setColor(Color.WHITE);
        chartView.addData(dataSet);
        chartView.show();
    }

    public Date buildDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }

    public Format getDateFormat() {
        Format dateFormat = DateFormat.getDateInstance();//android.text.format.DateFormat.getDateFormat(getApplicationContext());
        return dateFormat;
    }
}
