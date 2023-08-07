package com.sindercube.iconic.splash;

import net.minecraft.text.Text;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashText {

    public Text text;
    public int weight = 1;
    public Date date;
    public String mod;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");

    public SplashText(Text text, @Nullable Integer weight, @Nullable String date, @Nullable String mod) throws ParseException {
        this.text = text;
        if (weight != null) this.weight = weight;
        if (date != null) this.date = DATE_FORMAT.parse(date + " " + Calendar.getInstance().get(Calendar.YEAR));
        if (mod != null) this.mod = mod;
    }

}
