package User;

/**
 * Created by tushar on 24/5/16.
 */
public class Requirements {
    private String name;
    private String status;
    private int viewId;
    private final String REQUIRE="notrequired";

    public Requirements(String name){
        this.name=name;
        this.status=REQUIRE;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
