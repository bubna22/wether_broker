package com.bubna.model.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
@JsonIgnoreProperties
public class JsonQuery implements Serializable {
    private Integer count;
    private Date created;
    private String lang;
    private JsonResults results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public JsonResults getJsonResults() {
        return results;
    }

    public void setJsonResults(JsonResults results) {
        this.results = results;
    }

}
