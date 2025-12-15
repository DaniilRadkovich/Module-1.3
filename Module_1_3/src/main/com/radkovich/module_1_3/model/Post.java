package main.com.radkovich.module_1_3.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels;
    private Status status;

    public Post(long id, String content, LocalDateTime created, LocalDateTime updated, List<Label> labels, Status status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.status = status;
    }
    public Post() {
    }

    public Post(String content, LocalDateTime creationDate, List<Label> labels, Status status) {
        this.content = content;
        this.created = creationDate;
        this.labels = labels;
        this.status = status;
    }

    public Post(String content, LocalDateTime modificationDate, Status status) {
        this.content = content;
        this.updated = modificationDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addLabel(Label label){
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(label);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", labels=" + labels +
                ", status=" + status +
                '}';
    }
}
