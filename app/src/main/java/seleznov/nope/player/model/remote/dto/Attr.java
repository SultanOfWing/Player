package seleznov.nope.player.model.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 21.06.2018.
 */

public class Attr {
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("perPage")
    @Expose
    private String perPage;
    @SerializedName("totalPages")
    @Expose
    private String totalPages;
    @SerializedName("total")
    @Expose
    private String total;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
