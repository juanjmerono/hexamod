package es.um.atica.hexamod.tasks.domain;

public class TaskDescription {

    private String value;

    private TaskDescription(String desc) {
        if (desc==null || desc.isEmpty()) throw new IllegalArgumentException("Descripción de tarea no válida.");
        this.value = desc;
    }

    public static TaskDescription of (String desc) { return new TaskDescription(desc); }

    public String getValue() { return this.value; }

}
