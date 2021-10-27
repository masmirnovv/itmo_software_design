package ru.masmirnov.sd.mvc.model;

public class TodoRemove {

    private String id;

    public TodoRemove() { }

    public TodoRemove(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
