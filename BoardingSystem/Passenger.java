package BoardingSystem;

import java.time.LocalDate;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class


public class Passenger {
    public String firstName;
    public String lastName;

    public String dobStr;
    public LocalDate DOB;

    public String boardingCategory;

    public String arrivalTimeStr;
    public LocalDateTime arrivalTime;

    public Integer priority; // (lower value indicates higher priority)


    public void str2date() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.DOB = LocalDate.parse(dobStr, formatter);
    }

    public void str2time() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        this.arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);
    }

}
