package Logic;

import Airports.Airport;
import DataBase.GetDataBaseInfos;

import java.util.Collections;
import java.util.Map;
import java.util.Vector;

public class Brain {
    static final Map<Integer, Airport> airportsMap = GetDataBaseInfos.get_database_infos();
    private int[][] get_adjacency_matrix() {

        int[][] adjacencyMatrix = new int[airportsMap.size()][airportsMap.size()];
        for(int i = 0; i < airportsMap.size(); i++) {
            for(int j = 0; j < airportsMap.size(); j++) {
                if(i == j) adjacencyMatrix[i][j] = 0;
                else {
                    if(airportsMap.get(i).get_routes().contains(airportsMap.get(j).get_iata())) {
                        Double[] loc1 = {airportsMap.get(i).get_latitude(), airportsMap.get(i).get_longitude()};
                        Double[] loc2 = {airportsMap.get(j).get_latitude(), airportsMap.get(j).get_longitude()};
                        Integer distance = Distance.calculate_distance(loc1, loc2);
                        adjacencyMatrix[i][j] = distance;
                    } else {
                        adjacencyMatrix[i][j] = 0;
                    }

                }
            }
        }
        return adjacencyMatrix;
    }

    private int get_index_from_iata(String airport_iata) {
        for(int i = 0; i < airportsMap.size(); i++) {
            if(airportsMap.get(i).get_iata().equals(airport_iata)) {
                return i;
            }
        }
        // If we don't find the correct airport we will return the standard airport.
        return 0;
    }

    public String get_iata_from_complete_name(String airport_complete_name) {
        char[] sequence_name = airport_complete_name.toCharArray();
        int len = sequence_name.length; // ... (XXX)
        StringBuilder airport_iata = new StringBuilder();
        for(int i = 4; i > 1; i--) {
            airport_iata.append(sequence_name[len - i]);
        }
        return airport_iata.toString();
    }

    public Vector<Airport> get_path(String start_airport_complete_name, String goal_airport_complete_name) {
        String start_airport_iata = get_iata_from_complete_name(start_airport_complete_name);
        String goal_airport_iata = get_iata_from_complete_name(goal_airport_complete_name);
        int startVertex = get_index_from_iata(start_airport_iata);
        int goal = get_index_from_iata(goal_airport_iata);
        Vector<Integer> paths = new Dijkstra(get_adjacency_matrix()).dijkstra(startVertex, goal);
        Vector<Airport> airports_in_path = new Vector<>();
        for(Integer path : paths) {
            Airport airport = new Airport(
                    airportsMap.get(path).get_iata(),
                    airportsMap.get(path).get_name(),
                    airportsMap.get(path).get_state_name(),
                    airportsMap.get(path).get_latitude(),
                    airportsMap.get(path).get_latitude(),
                    airportsMap.get(path).get_routes()
            );
            airports_in_path.add(airport);
        }
        return airports_in_path;
    }

    public Vector<String> get_airports_name(String state_name) {
        Vector<String> airports_name = new Vector<>();
        for(int i = 0; i < airportsMap.size(); i++) {
            if(airportsMap.get(i).get_state_name().equals(state_name)) {
                airports_name.add(airportsMap.get(i).get_name() + " (" + airportsMap.get(i).get_iata() + ")");
            }
        }
        return airports_name;
    }

    public Vector<String> get_states_name() {
        Vector<String> states_name = new Vector<>();
        for(int i = 0; i < airportsMap.size(); i++) {
            if(!states_name.contains(airportsMap.get(i).get_state_name())) {
                states_name.add(airportsMap.get(i).get_state_name());
            }
        }
        Collections.sort(states_name);
        return states_name;
    }
}

