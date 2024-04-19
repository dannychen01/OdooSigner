package dto;

public class kwargs {
    public kwargs(dto.context context) {
        this.context = context;
    }

    private context context;

    public dto.context getContext() {
        return context;
    }

    public void setContext(dto.context context) {
        this.context = context;
    }
}
