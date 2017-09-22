package com.bubna.utils.json;

import com.bubna.utils.Handler;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializerHandler implements Handler<String, Date> {

    private static final Logger logger = Logger.getLogger(DateDeserializerHandler.class);

    private final SimpleDateFormat format;
    private Handler<String, Date> next;

    private Date result;

    public DateDeserializerHandler(SimpleDateFormat format) {
        this.format = format;
    }

    @Override
    public void setNext(Handler<String, Date> next) {
        this.next = next;
    }

    @Override
    public void handle(String o) {
        try {
            result = format.parse(o);
        } catch (ParseException e) {
            if (next != null) {
                next.handle(o);
                result = next.getResult();
                return;
            }
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date getResult() {
        return result;
    }
}
