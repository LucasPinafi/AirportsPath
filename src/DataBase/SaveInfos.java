package DataBase;

import Airports.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SaveInfos {
    public static void save_infos(Vector<Airport> path, int num_max_path) {
        Connection connection = GetDataBaseConnection.get_database_connection();
        try {
            StringBuilder query_builder = new StringBuilder("INSERT INTO save_searches(iata_1, iata_2, iata_3, iata_4, iata_5, iata_6) VALUES(");
            String[] iatas = new String[num_max_path];
            for(int i = 0; i < num_max_path; i++) {
                if(i < path.size()) {
                    if (i == num_max_path - 1){
                        iatas[i] = "\"" + path.get(i).get_iata() + "\"";
                    }
                    else {
                        iatas[i] = "\"" + path.get(i).get_iata() + "\",";
                    }
                } else {
                    if(i == num_max_path - 1){
                        iatas[i] = "\"XXX\"";
                    } else {
                        iatas[i] = "\"XXX\",";
                    }
                }
            }
            for(int i = 0; i < num_max_path; i++) {
                query_builder.append(iatas[i]);
            }
            query_builder.append(")");
            String query = query_builder.toString();
            System.out.println(query);
            assert connection != null;
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
