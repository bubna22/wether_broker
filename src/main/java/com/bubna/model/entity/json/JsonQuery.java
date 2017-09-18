package com.bubna.model.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName(value = "query")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonQuery implements Serializable {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("created")
    private String created;
    @JsonProperty("lang")
    private String lang;
//    @JsonProperty("results")
//    private JsonResults results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

//    public JsonResults getJsonResults() {
//        return results;
//    }
//
//    public void setJsonResults(JsonResults results) {
//        this.results = results;
//    }

    @Override
    public String toString() {
        return "count: " + count + "; created: " + created + "; lang: " + lang;
    }

}
