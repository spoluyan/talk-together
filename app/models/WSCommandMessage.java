package models;

import java.io.Serializable;

public class WSCommandMessage implements Serializable {
    private String op;
    private String data;

    public WSCommandMessage() {
    }

    public WSCommandMessage(String op, String data) {
        this.op = op;
        this.data = data;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
