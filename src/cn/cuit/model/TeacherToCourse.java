package cn.cuit.model;

/**
 * Created by Esong on 2017/5/11.
 */
public class TeacherToCourse {
    private int ttc_Id;
    private String u_number;
    private int cou_Id;
    private String ttc_type;
    private int ttc_time;
    private float ttc_credit;
    private int cls_id;


    public int getTtc_Id() {
        return ttc_Id;
    }

    public void setTtc_Id(int ttc_Id) {
        this.ttc_Id = ttc_Id;
    }

    public String getU_number() {
        return u_number;
    }

    public void setU_number(String u_number) {
        this.u_number = u_number;
    }

    public int getCou_Id() {
        return cou_Id;
    }

    public void setCou_Id(int cou_Id) {
        this.cou_Id = cou_Id;
    }

    public String getTtc_type() {
        return ttc_type;
    }

    public void setTtc_type(String ttc_type) {
        this.ttc_type = ttc_type;
    }

    public int getTtc_time() {
        return ttc_time;
    }

    public void setTtc_time(int ttc_time) {
        this.ttc_time = ttc_time;
    }

    public float getTtc_credit() {
        return ttc_credit;
    }

    public void setTtc_credit(float ttc_credit) {
        this.ttc_credit = ttc_credit;
    }

    public int getCls_id() {
        return cls_id;
    }

    public void setCls_id(int cls_id) {
        this.cls_id = cls_id;
    }
}
