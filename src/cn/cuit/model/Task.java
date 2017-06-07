package cn.cuit.model;

import java.sql.Date;

/**
 * Created by Esong on 2017/5/11.
 */
public class Task {
    private int tk_Id;
    private String tk_Name;
    private Date tk_Begin;
    private Date tk_End;
    private int ql_Id;
    private int ttc_id;

    public int getTk_Id() {
        return tk_Id;
    }

    public void setTk_Id(int tk_Id) {
        this.tk_Id = tk_Id;
    }

    public String getTk_Name() {
        return tk_Name;
    }

    public void setTk_Name(String tk_Name) {
        this.tk_Name = tk_Name;
    }

    public Date getTk_Begin() {
        return tk_Begin;
    }

    public void setTk_Begin(Date tk_Begin) {
        this.tk_Begin = tk_Begin;
    }

    public Date getTk_End() {
        return tk_End;
    }

    public void setTk_End(Date tk_End) {
        this.tk_End = tk_End;
    }

    public int getQl_Id() {
        return ql_Id;
    }

    public void setQl_Id(int ql_Id) {
        this.ql_Id = ql_Id;
    }

    public int getTtc_id() {
        return ttc_id;
    }

    public void setTtc_id(int ttc_id) {
        this.ttc_id = ttc_id;
    }
}
