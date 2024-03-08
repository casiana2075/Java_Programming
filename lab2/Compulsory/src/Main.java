import java.time.LocalTime;

public class Main {
    public static void main(String args[]) {
        //client
        Client c1 = new Client();
        
        c1.setName("Client 1");
        c1.setMinTime(LocalTime.of(8, 0));
        c1.setMaxTime(LocalTime.of(12, 30));
        c1.setType(ClientType.REGULAR);
        
        System.out.println(c1);
        
        Client c3 = new Client("Client 3", LocalTime.NOON, LocalTime.MIDNIGHT);
        System.out.println(c3.getClientTime());

        /*vehicle*/
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle("Dacia ");
        
        System.out.println(v2);
        
        Depot d1 = new Depot("Depozit gara");
        // Depot d2 = new Depot("Depozit periferie");

        v1.setName("Ford Focus");
        d1.setVehicles(v1,v2);

        System.out.println(d1);
        
    }
}

