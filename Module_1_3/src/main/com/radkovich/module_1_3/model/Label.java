package main.com.radkovich.module_1_3.model;

import java.util.Objects;

public class Label {
    private long id;
    private String name;
    private Status status;

    public Label() {
    }

    public Label(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getPostStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id && Objects.equals(name, label.name) && status == label.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postStatus=" + status +
                '}';
    }
}
