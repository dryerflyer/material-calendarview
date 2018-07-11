package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.prolificinteractive.materialcalendarview.*;

import com.prolificinteractive.materialcalendarview.sample.decorators.RangeDayDecorator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * An activity that demonstrate the multiple selection mode that the calendar offers.
 * The mode can be set through xml or in java.
 *
 * @see MaterialCalendarView.SelectionMode
 */
public class SelectionModesActivity extends AppCompatActivity
    implements OnDateSelectedListener, OnRangeSelectedListener {

  private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

  @BindView(R.id.calendar_view_single) MaterialCalendarView single;
  @BindView(R.id.calendar_view_multi) MaterialCalendarView multi;
  @BindView(R.id.calendar_view_range) MaterialCalendarView range;
  @BindView(R.id.calendar_view_none) MaterialCalendarView none;


  private RangeDayDecorator decorator;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection_modes);
    ButterKnife.bind(this);

    decorator = new RangeDayDecorator(this);

    single.setOnDateChangedListener(this);
    multi.setOnDateChangedListener(this);
    range.setOnDateChangedListener(this);
    range.setOnRangeSelectedListener(this);
    range.addDecorator(decorator);
    none.setOnDateChangedListener(this);
  }

  @Override public void onDateSelected(
      @NonNull final MaterialCalendarView widget,
      @NonNull final CalendarDay date,
      final boolean selected) {
    final String text = selected ? FORMATTER.format(date.getDate()) : "No Selection";
    Toast.makeText(SelectionModesActivity.this, text, Toast.LENGTH_SHORT).show();
  }

  @Override public void onRangeSelected(
      @NonNull final MaterialCalendarView widget,
      @NonNull final List<CalendarDay> dates) {
    if (dates.size() > 0) {
      decorator.addFirstAndLast(dates.get(0), dates.get(dates.size() - 1));
      range.invalidateDecorators();
    }
  }

  @OnCheckedChanged(R.id.calendar_mode)
  void onCalendarModeChanged(boolean checked) {
    final CalendarMode mode = checked ? CalendarMode.WEEKS : CalendarMode.MONTHS;
    single.state().edit().setCalendarDisplayMode(mode).commit();
    multi.state().edit().setCalendarDisplayMode(mode).commit();
    range.state().edit().setCalendarDisplayMode(mode).commit();
    none.state().edit().setCalendarDisplayMode(mode).commit();
  }

}
