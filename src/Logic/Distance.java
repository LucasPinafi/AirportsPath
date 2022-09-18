package Logic;

public class Distance {
    static public Integer calculate_distance(Double[] loc1, Double[] loc2) {
        double lat1 = loc1[0];
        double lon1 = loc1[1];

        double lat2 = loc2[0];
        double lon2 = loc2[1];

        double rad = Math.PI/180;
        double a = 0.5 - Math.cos((lat2 - lat1) * rad)/2 + Math.cos(lat1 * rad) * Math.cos(lat2 * rad) * (1-Math.cos((lon2 - lon1) * rad))/2;
        return (int) (12742 * Math.asin(Math.sqrt(a)));
    }
}
