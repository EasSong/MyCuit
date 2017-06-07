package cn.cuit.model;

/**
 * Created by Esong on 2017/5/11.
 */
public class Question {
    private int q_Id;
    private String q_Name;
    private int q_Type;
    private String q_Options;

    public int getQ_Id() {
        return q_Id;
    }

    public void setQ_Id(int q_Id) {
        this.q_Id = q_Id;
    }

    public String getQ_Name() {
        return q_Name;
    }

    public void setQ_Name(String q_Name) {
        this.q_Name = q_Name;
    }

    public int getQ_Type() {
        return q_Type;
    }

    public void setQ_Type(int q_Type) {
        this.q_Type = q_Type;
    }

    public String getQ_Options() {
        return q_Options;
    }

    public void setQ_Options(String q_Options) {
        this.q_Options = q_Options;
    }
}
