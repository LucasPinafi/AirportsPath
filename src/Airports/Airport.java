package Airports;

import java.util.Vector;

public class Airport {
    String iata, name, state_name;
    double latitude, longitude;
    Vector<String> routes;

    public Airport(
            String iata,
            String name,
            String state_name,
            double latitude,
            double longitude,
            Vector<String> routes
    ) {
        this.iata = iata;
        this.name = name;
        this.state_name = state_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routes = routes;
    }

    public String get_name() {return name;}
    public String get_iata() {return iata;}
    public String get_state_name() {return state_name;}
    public double get_latitude() {return latitude;}
    public double get_longitude() {return longitude;}
    public Vector<String> get_routes() {return routes;}

    @Override
    public String toString() {
        return "Name: " + name;
    }
}
