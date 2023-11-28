package BoardingSystem;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Duration;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Boarding {

    public PriorityQueue passengerQ = new PriorityQueue();
    public LocalDateTime currDateTime = LocalDateTime.now();
    public Integer totalPassengers = 0;

    public void readFromFile() {
        // Populates the all tasks list
        String csvFile = System.getProperty("user.dir") + "/BoardingSystem/passengerData.csv";
        System.out.println("Reading Data from: " + csvFile);


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            Integer n =0;
            while ((line = br.readLine()) != null) {
                if (n == 0) {
                    n = 1;
                    continue; // Skip 1st iteration...since the first line does not have task info
                }
                String[] values = line.split(",");
                System.out.println(line);
                Passenger info =new Passenger();
                info.firstName = values[0];
                info.lastName = values[1];
                info.dobStr = values[2];
                info.boardingCategory = values[3];
                info.arrivalTimeStr = values[4];
                info.priority= Integer.parseInt(values[5]);
                info.str2date();
                info.str2time();
                passengerQ.insert(info);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void boardPassenger() {
        if (!this.passengerQ.heap.isEmpty() && this.totalPassengers < 15) {
            PriorityQueue.Node current_passenger = this.passengerQ.extractMin();
            /* TODO Add the current_passenger to heaplist*/
            this.totalPassengers = this.totalPassengers + 1;
            Thread.sleep(20*1000);
        }
    }
    public static void main(String args[]) {
        Boarding rs = new Boarding();
        rs.readFromFile();
        while (Duration.between(rs.currDateTime, LocalDateTime.now()).toMinutes() <= 10) {
            rs.boardPassenger();
            if (rs.totalPassengers >= 15) {
                System.out.println("Plane is at full capacity...we have boarded 15 passengers");
                break;
            }
        }
        if (Duration.between(rs.currDateTime, LocalDateTime.now()).toMinutes() > 10) {
            System.out.println("Its been 10 minutes since boarding started...The gates are closing!!");
        }
        // while (!rs.passengerQ.heap.isEmpty()) {
        //     PriorityQueue.Node min = rs.passengerQ.extractMin();
        //     System.out.println(min.traveller.priority);
        //     System.out.println(min.traveller.firstName);
        // }
    }

}
