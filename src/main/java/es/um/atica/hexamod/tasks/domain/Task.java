package es.um.atica.hexamod.tasks.domain;

import java.util.UUID;

public class Task {
    
    private UUID id;
    private UUID user;
    private TaskDescription description;
    private TaskDuration duration;

    private Task(UUID id, UUID usr, TaskDescription desc, TaskDuration dur) {
        this.id = id; this.user = usr; this.description = desc; this.duration = dur; 
    }

    public static Task of (String id, String usr, String desc, int dur) {
        return new Task(UUID.fromString(id), UUID.fromString(usr), TaskDescription.of(desc), TaskDuration.of(dur));
    }

    public String getId() { return id.toString(); }
    public String getUser() { return user.toString(); }
    public String getDescription() { return description.getValue(); }
    public int getDuration() { return duration.getValue(); }
}
