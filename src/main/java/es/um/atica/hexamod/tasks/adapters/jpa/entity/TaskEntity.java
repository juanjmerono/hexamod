package es.um.atica.hexamod.tasks.adapters.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.um.atica.hexamod.tasks.domain.Task;

@Entity
@Table(name="TASKS",schema="HEXAMOD")
public class TaskEntity {
    private String id;
    private String user;
    private String desc;
    private int duration;

    private TaskEntity() {}
    private TaskEntity(String id, String user, String desc,int duration) {
        this.id = id; this.user = user; this.desc = desc; this.duration = duration;
    }

    public static TaskEntity of (Task task) {
        return new TaskEntity(
            task.getId(),
            task.getUser(),
            task.getDescription(),
            task.getDuration());
    }

    public Task toModel() {
        return Task.of(this.id, this.user, this.desc, this.duration);
    }

    @Id
    @Column(name = "identificador")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Column(name = "usuario")
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    @Column(name = "descripcion")
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    @Column(name = "duracion")
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

}
