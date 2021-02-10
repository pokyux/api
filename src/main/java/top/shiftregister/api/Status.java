package top.shiftregister.api;

public class Status {

    private final int status;
    private final String information;

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
