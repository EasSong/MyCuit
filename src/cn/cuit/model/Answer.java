package cn.cuit.model;

import java.io.Serializable;

/**
 * Created by Esong on 2017/5/11.
 */
public class Answer implements Serializable {
    private String ans_Context;
    private int qtq_Id;
    private int ts_Id;

    public String getAns_Context() {
        return ans_Context;
    }

    public void setAns_Context(String ans_Context) {
        this.ans_Context = ans_Context;
    }

    public int getQtq_Id() {
        return qtq_Id;
    }

    public void setQtq_Id(int qtq_Id) {
        this.qtq_Id = qtq_Id;
    }

    public int getTs_Id() {
        return ts_Id;
    }

    public void setTs_Id(int ts_Id) {
        this.ts_Id = ts_Id;
    }
}