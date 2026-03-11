import java.time.LocalDate;

public class Task {

    private String description;
    private boolean done;
    private LocalDate createdAt;

    public Task(String description) {
        this.description = description;
        this.done = false;
        this.createdAt = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void markDone() {
        done = true;
    }

    @Override
    public String toString() {
        return (done ? "[✔] " : "[ ] ") + description + " (" + createdAt + ")";
    }
}