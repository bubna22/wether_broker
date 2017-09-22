package com.bubna.utils.json;

import com.bubna.utils.Handler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    private static final Logger logger = Logger.getLogger(CustomJsonDateDeserializer.class);
    private final Handler<String, Date> dateDeserializer;

    public CustomJsonDateDeserializer() {
        dateDeserializer = new DateDeserializerHandler(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        DateDeserializerHandler next = new DateDeserializerHandler(new SimpleDateFormat("EEE, dd MMM yyyy h:mm a Z", Locale.US));
        dateDeserializer.setNext(next);
        DateDeserializerHandler next1 = new DateDeserializerHandler(new SimpleDateFormat("h:m a", Locale.US));
        next.setNext(next1);
    }

    @Override
    public Date deserialize(JsonParser jsonparser,
                            DeserializationContext deserializationcontext) {
        try {
            String date = jsonparser.getText();
            dateDeserializer.handle(date);
            return dateDeserializer.getResult();
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

}
