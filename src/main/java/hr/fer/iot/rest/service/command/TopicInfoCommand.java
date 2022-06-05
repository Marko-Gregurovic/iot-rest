package hr.fer.iot.rest.service.command;

public class TopicInfoCommand {
    private Integer room;

    private Double threshold;

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

}
