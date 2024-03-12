import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class Client{

    private String name;
    private ClientType type;
    private LocalTime minTime;
    private LocalTime maxTime;
    private int index;

    public Client(String name, LocalTime minTime, LocalTime maxTime, ClientType type){
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.type = type;
    }

    public Client(int index) {
        this.index = index;
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ClientType getType() {
        return type;
    }
    public void setType(ClientType type) {
        this.type = type;
    }
    public LocalTime getMinTime() {
        return minTime;
    }
    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }
    public LocalTime getMaxTime() {
        return maxTime;
    }
    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Client other)) {
            return false;
        }
        return name.equals(other.name) &&
                type.equals(other.type) &&
                minTime.equals(other.minTime) &&
                maxTime.equals(other.maxTime);
    }
}