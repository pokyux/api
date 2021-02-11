package top.shiftregister.api;

public class Status {

    int status;
    String information;

    public Status() {

    }

    public Status(int code, String info) {
        status = code;
        information = info;
    }

    public int getStatus() {
        return status;
    }

    public String getInformation() {
        return information;
    }
}
