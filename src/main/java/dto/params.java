package dto;

import java.util.List;

public class params {

    public params(String model, String method, List<args> args, dto.kwargs kwargs) {
        this.model = model;
        this.method = method;
        this.args = args;
        this.kwargs = kwargs;
    }

    private String model;
    private String method;
    private List<args> args;
    private kwargs kwargs;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<dto.args> getArgs() {
        return args;
    }

    public void setArgs(List<dto.args> args) {
        this.args = args;
    }

    public dto.kwargs getKwargs() {
        return kwargs;
    }

    public void setKwargs(dto.kwargs kwargs) {
        this.kwargs = kwargs;
    }
}
