package BoardingSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Boarding {

    public PriorityQueue passengerQ = new PriorityQueue();
    public LocalDateTime currDateTime = LocalDateTime.now();
    public Integer totalPassengers = 0;
    private boolean onBoarding = false;
    public onBoardPassengers manifest = new onBoardPassengers();

    private JFrame frame;
    private JPanel panel;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField DOB;
    private JTextField bookingClass;
    private JTextField priority;
    private JTextField searchBox;

    public void readFromFile() {
        /*Populates the all passengers from the csv file*/
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
                this.passengerQ.insert(info);
            }
            System.out.println("--------------------------------------------");
        }
        catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public void boardPassenger() {
        /* This function is used to take the top of the  queue and add it to the manfiest*/
        if (!this.passengerQ.heap.isEmpty() && this.totalPassengers < 15) {
            PriorityQueue.Node current_passenger = this.passengerQ.extractMin();
            System.out.println("Now boarding: " + current_passenger.traveller.firstName + " priority: " + current_passenger.traveller.priority);
            // Add the passenger details to the manifest, update count and set the onboarding flag to true
            this.manifest.addPassenger(current_passenger.traveller);
            this.totalPassengers = this.totalPassengers + 1;
            this.onBoarding = true;
        }
    }

    private void inputPassenger() {
        /* This function is used to dynamically add the passenger details to the queue */
        Passenger info =new Passenger();
        info.firstName = firstName.getText();
        info.lastName = lastName.getText();
        info.dobStr = DOB.getText();
        info.boardingCategory = bookingClass.getText();
        info.arrivalTimeStr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        info.priority= Integer.parseInt(priority.getText());
        info.str2date();
        info.str2time();
        this.passengerQ.insert(info);
    }

    public void findPassenger() {
        String searchParam = searchBox.getText();
        this.manifest.searchByName(searchParam);
    }

    private JLabel getNewLabel(int x, int y, int width, int height, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField getTxtFeild(int x, int y, int width, int height, int length) {
        JTextField txtFeild = new JTextField(length);
        txtFeild.setBounds(x, y, width, height);
        return txtFeild;
    }
    
    public Boarding() {
        /*GUI to add travellers to wait list and search for passengers from the manifest*/
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Add passenger details");

        frame.add(panel);
        panel.setLayout(null);
        // Add Travellers
        panel.add(this.getNewLabel(10, 20, 80, 25, "First Name"));
        firstName = this.getTxtFeild(100, 20, 165, 25, 20);
        panel.add(firstName);

        panel.add(this.getNewLabel(10, 50, 80, 25, "Last Name"));
        lastName = this.getTxtFeild(100, 50, 165, 25, 20);
        panel.add(lastName);

        panel.add(this.getNewLabel(10, 80, 80, 25, "Date of Birth"));
        DOB = this.getTxtFeild(100, 80, 165, 25, 10);
        panel.add(DOB);

        panel.add(this.getNewLabel(10, 110, 80, 25, "Booking Class"));
        bookingClass = this.getTxtFeild(100, 110, 165, 25, 20);
        panel.add(bookingClass);

        panel.add(this.getNewLabel(10, 140, 80, 25, "Priority"));
        priority = this.getTxtFeild(100, 140, 165, 25, 2);
        panel.add(priority);

        JButton button = new JButton("Add");
        button.setBounds(100, 190, 80, 25);
        button.addActionListener(new ActionListener() {
            // Add the new passenger to the wait list
            public void actionPerformed(ActionEvent e) {                
                inputPassenger();
            }
            
        });
        panel.add(button);
        
        // Search bar for passengers
        searchBox = this.getTxtFeild(10, 250, 205, 25, 40);
        panel.add(searchBox);

        JButton search = new JButton("search");
        search.setBounds(225, 250, 80, 25);
        search.addActionListener(new ActionListener() {
            // Call the hastable to search functionality
            public void actionPerformed(ActionEvent e) {                
                findPassenger();
            }
            
        });
        panel.add(search);

        panel.add(this.getNewLabel(10, 280, 350, 25, "Search results are displayed in the terminal.."));
        frame.setVisible(true);
    }

    public static void main(String args[]) throws InterruptedException {
        Boarding rs = new Boarding();
        rs.readFromFile();
        while (Duration.between(rs.currDateTime, LocalDateTime.now()).toMinutes() <= 10) {
            // Run the code for either 10 minutes or until the pasenger count hits 15...
            if (rs.totalPassengers >= 15) {
                System.out.println("Plane is at full capacity...we have boarded 15 passengers");
                break;
            } else if (rs.onBoarding){
                System.out.println("The next passenger will be helped in 20 secs");
                Thread.sleep(20*1000);
                rs.onBoarding = false;
            }
            rs.boardPassenger();
        }
        if (Duration.between(rs.currDateTime, LocalDateTime.now()).toMinutes() > 10) {
            System.out.println("Its been 10 minutes since boarding started...The gates are closing!!");
        }
    }

}
