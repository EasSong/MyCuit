package cn.cuit.model;

/**
 * Created by Esong on 2017/6/9.
 */
public class TaskState {
    private int id;
    private int tkId;
    private String uNumber;
    private int tsState;

    public TaskState(){}
    public TaskState(int tkId,String uNumber, int tsState){
        this.tkId = tkId;
        this.uNumber = uNumber;
        this.tsState = tsState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTkId() {
        return tkId;
    }

    public void setTkId(int tkId) {
        this.tkId = tkId;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public int getTsState() {
        return tsState;
    }

    public void setTsState(int tsState) {
        this.tsState = tsState;
    }
}
