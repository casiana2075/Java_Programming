import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class Client{
    
    private String name;
    private ClientType type;
    private LocalTime minTime;
    private LocalTime maxTime;
    public static int count;
    
    public Client(){count++;}
    public Client(String name){
        this(name, null, null); count++;
    }
    public Client(String name, LocalTime minTime, LocalTime maxTime){
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
        count++;
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

    public Duration getClientTime(){ //time that a client has
            LocalDateTime dateTime1 = LocalDateTime.of(LocalDate.now(), minTime);
            LocalDateTime dateTime2 = LocalDateTime.of(LocalDate.now(), maxTime);
            Duration duration = Duration.between(dateTime1, dateTime2);
            return duration;
    }
    
    @Override
    public String toString(){ 
        return "Name:" + name + "\nClientType:" + type + "\nVisiting hours:" + minTime + "-" + maxTime + "\nDuration:" + getClientTime() + "\n";
    }
}

