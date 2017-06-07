package cn.cuit.model;

/**
 * Created by Esong on 2017/5/11.
 */
public class Answer {
    private int ans_Id;
    private StringBuffer ans_Context;
    private int qtq_Id;

    public int getAns_Id() {
        return ans_Id;
    }

    public void setAns_Id(int ans_Id) {
        this.ans_Id = ans_Id;
    }

    public StringBuffer getAns_Context() {
        return ans_Context;
    }

    public void setAns_Context(StringBuffer ans_context) {
        this.ans_Context = ans_context;
    }

    public int getQtq_Id() {
        return qtq_Id;
    }

    public void setQtq_Id(int qtq_Id) {
        this.qtq_Id = qtq_Id;
    }
}