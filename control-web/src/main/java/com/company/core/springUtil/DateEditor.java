package com.company.core.springUtil;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {

    private String dataFormat;

    public DateEditor(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || "".equals(text.trim())) {
            setValue(null);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
            try {
                setValue(simpleDateFormat.parse(text));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
        return (value != null ? simpleDateFormat.format(value) : "");
    }
}
