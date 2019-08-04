package com.itpzy.crowdfunding.bean;

public class CertForm {

    private int id;
    private int memid;
    private String piid;
    private String code;
    private String lastproc;
    private String formstatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemid() {
        return memid;
    }

    public void setMemid(int memid) {
        this.memid = memid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastproc() {
        return lastproc;
    }

    public void setLastproc(String lastproc) {
        this.lastproc = lastproc;
    }

    public String getFormstatus() {
        return formstatus;
    }

    public void setFormstatus(String formstatus) {
        this.formstatus = formstatus;
    }
}
