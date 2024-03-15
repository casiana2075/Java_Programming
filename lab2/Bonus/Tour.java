package org.example;
import java.util.ArrayList;
import java.util.List;

class Tour {
    private final Vehicle vehicle;
    private final List<Client> clients;
    private Depot depot;

    public Tour(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.clients = new ArrayList<>();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "\ndepot=" + depot.getName() +
                ",\nvehicle=" + vehicle.getName() +
                ",\nclients=" + clients +
                "}\n";
    }
}
