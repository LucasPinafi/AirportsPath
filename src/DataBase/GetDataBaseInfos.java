package DataBase;

import Airports.Airport;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GetDataBaseInfos {
    static public Map<Integer, Airport> get_database_infos() {
        Map<Integer, Airport> airportsMap = new HashMap<>();
        Connection connection = GetDataBaseConnection.get_database_connection();
        try{
            String query = "SELECT * from airports";
            assert connection != null;
            ResultSet airports = connection.createStatement().executeQuery(query);
            Integer index = 0;
            while(airports.next()) {
                String airport_iata = airports.getString("airport_iata");
                String name = airports.getString("airport_name");
                String state_name = airports.getString("state_name");
                double latitude = Double.parseDouble(airports.getString("airport_latitude"));
                double longitude = Double.parseDouble(airports.getString("airport_longitude"));
                query = "SELECT route_name from routes WHERE source_name = '" + airport_iata + "'";

                // GET AIRPORTS ROUTES.
                ResultSet airport_routes = connection.createStatement().executeQuery(query);
                Vector<String> routes = new Vector<>();
                while(airport_routes.next()) {
                    routes.add(airport_routes.getString("route_name"));

                }
                Airport airport = new Airport(
                        airport_iata,
                        name,
                        state_name,
                        latitude,
                        longitude,
                        routes
                );
                airportsMap.put(index, airport);
                index++;
            }
        } catch (SQLException e) {
            System.out.println("Problem in SQL [GetDataBaseInfos]");
            System.out.println(e.getMessage());
        }
        return airportsMap;
    }
}

