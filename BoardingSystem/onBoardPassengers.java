package BoardingSystem;

import java.util.Enumeration;
import java.util.Hashtable;

public class onBoardPassengers {
    
    Hashtable<String, Passenger> obpList = new Hashtable<>();

    public void addPassenger(Passenger traveller) {
        // Adding key/value pairs
        this.obpList.put(traveller.firstName + " " + traveller.lastName, traveller);
    }

    public void searchByName(String passengerName) {
        // Searching for names that match
        if (this.obpList.containsKey(passengerName)) {
            Enumeration<String> keys = this.obpList.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                Passenger value = this.obpList.get(key);

                if (passengerName.equals(key)) {
                    System.out.println("Name: " + passengerName + ", DOB: " + value.dobStr + ", prioprity: " + value.priority.toString());
                }
            }
        } else {
            System.out.println("No matching entry found!!");
        }
    }
}