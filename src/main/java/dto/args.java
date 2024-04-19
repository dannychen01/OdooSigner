package dto;

public class args {

    public args(long employee_id, String name, String action, boolean action_desc) {
        this.employee_id = employee_id;
        this.name = name;
        this.action = action;
        this.action_desc = action_desc;
    }

    private long employee_id;
    private String name;
    private String action;
    private boolean action_desc;

    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isAction_desc() {
        return action_desc;
    }

    public void setAction_desc(boolean action_desc) {
        this.action_desc = action_desc;
    }
}
