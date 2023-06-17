package es.um.atica.hexamod.tasks.domain;

public class TaskDuration {

    private int value;

    private TaskDuration(int duration) {
        if (duration <=0) throw new IllegalArgumentException("Duración de tarea no permitida");
        this.value = duration;
    }

    public static TaskDuration of (int duration) { return new TaskDuration(duration); }

    public int getValue() { return this.value; }

}
