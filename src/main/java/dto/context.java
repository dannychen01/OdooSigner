package dto;

import java.util.Map;

public class context {
    public context(String lang, String tz, long uid, long search_default_today, Map<String, Long> params) {
        this.lang = lang;
        this.tz = tz;
        this.uid = uid;
        this.search_default_today = search_default_today;
        this.params = params;
    }

    private String lang;
    private String tz;
    private long uid;
    private long search_default_today;
    private Map<String,Long> params;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getSearch_default_today() {
        return search_default_today;
    }

    public void setSearch_default_today(long search_default_today) {
        this.search_default_today = search_default_today;
    }

    public Map<String, Long> getParams() {
        return params;
    }

    public void setParams(Map<String, Long> params) {
        this.params = params;
    }
}
