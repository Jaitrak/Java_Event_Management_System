import java.util.*;
import java.io.*;
import java.sql.*;
// import java.sql.Date;

public class Event_planner {
    public static void main(String[] args) throws Exception {
        EventPlanner();
    }

    public static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_management", "root", "");
        return con;
    }

    public static void EventPlanner() throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = getConnection();
        HashMap<Integer, User> userMap = new HashMap<>();
        new User().setUserHM(con, userMap);
        HashMap<Integer, Admin> adminMap = new HashMap<>();
        new Admin().setAdminHM(con, adminMap);
        HashMap<Integer, Hotel> hotelMap = new HashMap<>();
        new Hotel().setHotelHM(con, hotelMap);
        HashMap<Integer, PartyPlot> partyPlotMap = new HashMap<>();
        new PartyPlot().setPlotHM(con, partyPlotMap);
        BST eventBST = new BST();
        eventBST.setEventBST(con, eventBST);

        while (true) {
            System.out.print("1.User\n2.Admin\n3.Exit\n\nEnter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    while (true) {
                        System.out.print("\n1.Register\n2.Login\n\n2024/Enter your choice: ");
                        int ch1 = sc.nextInt();
                        sc.nextLine();
                        switch (ch1) {

                            case 1:
                                System.out.print("Username: ");
                                String username = sc.nextLine();
                                String password, confirmPassword;
                                while (true) {
                                    System.out.print("Password: ");
                                    password = sc.nextLine();
                                    System.out.print("Confirm Password: ");
                                    confirmPassword = sc.nextLine();

                                    if (password.equals(confirmPassword)) {
                                        break;
                                    }
                                    System.out.print("Invalid....Try Again...!!");

                                }
                                PreparedStatement pst = con
                                        .prepareStatement("INSERT INTO user(username,password,role) VALUES(?,?,?)");
                                pst.setString(1, username);
                                pst.setString(2, password);
                                pst.setString(3, "User");
                                int r = pst.executeUpdate();
                                System.out.println(r > 0 ? "Added Successful" : "Failed");
                                User user = new User(username, password);
                                userMap.put(userMap.size() + 1, user);
                                while (true) {
                                    System.out.print(
                                            "\n1.Add Event\n2.Show Venue\n3.change password\n4.Log Out\n\nEnter your choice: ");
                                    int ch2 = sc.nextInt();
                                    sc.nextLine();
                                    switch (ch2) {
                                        case 1:
                                            System.out.print("Enter your Event name: ");
                                            String eventName = sc.nextLine();

                                            String date;
                                            while (true) {
                                                System.out.print("Enter Event Date(YYYY/MM/DD): ");
                                                date = sc.next();
                                                if (isValidDate(date)) {
                                                    break;
                                                }
                                            }

                                            double totalCost = 0;
                                            int i = 0;
                                            int partyPlotChoice = 0;
                                            int hotelChoice = 0;
                                            int i1 = 0;
                                            int days = 0, plates = 0;
                                            boolean again = true;
                                           
                                        

                                            while (true) {
                                                
                                                

                                                String choice = sc.nextLine();
                                                if (choice.equalsIgnoreCase("H")) {
                                                    while (true) {
                                                        try {
                                                            System.out.println("How many days? ");
                                                            days = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    for (i = 1; i <= hotelMap.size(); i++) {
                                                        System.out.println((i) + "." + hotelMap.get(i).getName() + " - "
                                                                + hotelMap.get(i).getRentPerNight() + " per night");
                                                    }
                                                    System.out.println(
                                                            "Which hotel would you like to choose (Please select a number): ");
                                                    while (true) {
                                                        try {
                                                            hotelChoice = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    totalCost = hotelMap.get(hotelChoice).getRentPerNight() * days;
                                                    System.out
                                                            .println("Total cost for " + eventName + " event in "
                                                                    + hotelMap.get(hotelChoice).getName()
                                                                    + " hotel for " + days + " days is: " + totalCost
                                                                    + " Rs");
                                                    PreparedStatement pst1 = con.prepareStatement(
                                                            "INSERT INTO event(name,date,cost) VALUES(?,?,?)");
                                                    pst1.setString(1, eventName);
                                                    pst1.setString(2, date);
                                                    pst1.setDouble(3, totalCost);
                                                    int r1 = pst1.executeUpdate();
                                                    System.out.println(r1 > 0 ? "Added Successful" : "Failed");
                                                    eventBST.insert(new Event(eventName, date, totalCost));

                                                    if (again) {
                                                        try (BufferedWriter writer = new BufferedWriter(
                                                                new FileWriter("Hotel.txt", true))) {
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.write("Event Name:" + eventName);
                                                            writer.newLine();
                                                            writer.write("Date:" + date);
                                                            writer.newLine();

                                                            writer.write(
                                                                    "Venue:" + hotelMap.get(hotelChoice).getName());
                                                            writer.newLine();

                                                            writer.write("Total Cost: " + totalCost + " Rs");
                                                            writer.newLine();
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.flush();
                                                            writer.close();
                                                        } catch (IOException e) {
                                                            System.out.println(
                                                                    "An error occurred while saving event details: "
                                                                            + e.getMessage());
                                                        }
                                                    }

                                                    break;

                                                } else if (choice.equalsIgnoreCase("P")) {
                                                    while (true) {
                                                        try {
                                                            System.out.println("How many plates for catering? ");
                                                            plates = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    System.out.println(
                                                            "Which party plot would you like to choose (Please select a number between Number): ");
                                                    for (i1 = 1; i1 <= partyPlotMap.size(); i1++) {
                                                        System.out.println(
                                                                (i1) + "." + partyPlotMap.get(i1).getName() + " - "
                                                                        + partyPlotMap.get(i1).getPerPlateCharge()
                                                                        + " per plate");
                                                    }
                                                    while (true) {
                                                        try {
                                                            partyPlotChoice = sc.nextInt();
                                                            break;

                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    totalCost = partyPlotMap.get(partyPlotChoice).getPerPlateCharge()
                                                            * plates;

                                                    System.out.println(
                                                            "Total cost for " + eventName + " event in "
                                                                    + partyPlotMap.get(partyPlotChoice).getName()
                                                                    + " for " + plates + " plates is: " + totalCost
                                                                    + " Rs");

                                                    PreparedStatement pst2 = con.prepareStatement(
                                                            "INSERT INTO event(name,date,cost) VALUES(?,?,?)");
                                                    pst2.setString(1, eventName);
                                                    pst2.setString(2, date);
                                                    pst2.setDouble(3, totalCost);
                                                    int r2 = pst2.executeUpdate();
                                                    System.out.println(r2 > 0 ? "Added Successful" : "Failed");
                                                    eventBST.insert(new Event(eventName, date, totalCost));

                                                    if (again) {
                                                        try (BufferedWriter writer = new BufferedWriter(
                                                                new FileWriter("PartyPlot.txt", true))) {
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.write("Event Name:" + eventName);
                                                            writer.newLine();
                                                            writer.write("Date:" + date);
                                                            writer.newLine();

                                                            writer.write("Venue:"
                                                                    + partyPlotMap.get(partyPlotChoice).getName());
                                                            writer.newLine();
                                                            writer.write("Total Cost: " + totalCost + " Rs");
                                                            writer.newLine();
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.flush();
                                                            writer.close();
                                                        } catch (IOException e) {
                                                            System.out.println(
                                                                    "An error occurred while saving event details: "
                                                                            + e.getMessage());
                                                        }
                                                    }
                                                    break;
                                                }
                                            }

                                            break;
                                        case 2:
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");
                                            System.out.println("Avalaible Hotel:\n");
                                            System.out.println(
                                                    "---------------------------------------------------------------------------");

                                            for (int j = 1; j <= hotelMap.size(); j++) {
                                                System.out.println((j) + "." + hotelMap.get(j).getName() + " - "
                                                        + hotelMap.get(j).getRentPerNight() + " per night");
                                            }
                                            System.out.println(
                                                    "---------------------------------------------------------------------------");
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");

                                            System.out.println("\nAvalaible Party Plot:\n");
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");

                                            for (int j1 = 1; j1 <= partyPlotMap.size(); j1++) {
                                                System.out.println(
                                                        (j1) + "." + partyPlotMap.get(j1).getName() + " - "
                                                                + partyPlotMap.get(j1).getPerPlateCharge()
                                                                + " per plate");
                                            }
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");

                                            break;
                                        case 3:

                                            String update = "call updateUserPassword(?,?)";
                                            CallableStatement cs = con.prepareCall(update);
                                            System.out.println("Enter username for change");
                                            String user_name = sc.nextLine();
                                            System.out.println("Enter new password:");
                                            // System.out.print("Password: ");
                                            String password11 = sc.nextLine();
                                            System.out.print("Confirm Password: ");
                                            String confirmPassword11 = sc.nextLine();

                                            if (!(password11.equals(confirmPassword11))) {
                                                System.out.println("Invalid....Try Again...!!");
                                                break;
                                            } else {
                                                cs.setString(1, user_name);
                                                cs.setString(2, password11);
                                                int r1 = cs.executeUpdate();
                                                System.out.println((r1 > 0) ? "Updation done" : "Updation fail");
                                            }
                                        case 4:
                                            EventPlanner();
                                            break;
                                        default:
                                            System.out.println("Invalid Choice..!!");
                                    }
                                }

                            case 2:
                                while (true) {
                                    System.out.println("Username: ");
                                    String username1 = sc.nextLine();
                                    System.out.println("Password: ");
                                    String password1 = sc.nextLine();
                                    PreparedStatement pst1 = con
                                            .prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
                                    pst1.setString(1, username1);
                                    pst1.setString(2, password1);
                                    ResultSet rs = pst1.executeQuery();
                                    if (rs.next()) {
                                        break;
                                    }
                                    System.out.println("Invalid Password..!");
                                }

                                while (true) {
                                    System.out.print(
                                            "\n1.Add Event\n2.Show Venue\n3.change password\n4.Log Out\n\nEnter your choice: ");
                                    int ch2 = sc.nextInt();
                                    sc.nextLine();
                                    switch (ch2) {
                                        case 1:

                                            System.out.print("Enter your Event name: ");
                                            String eventName = sc.nextLine();

                                            String date;
                                            while (true) {
                                                System.out.print("Enter Birth_Date (YYYY/MM/DD): ");
                                                date = sc.next();
                                                if (isValidDate(date)) {
                                                    break;
                                                }
                                            }

                                            double totalCost = 0;
                                            int i = 0;
                                            int partyPlotChoice = 0;
                                            int hotelChoice = 0;
                                            int i1 = 0;
                                            int days = 0, plates = 0;
                                            boolean again = true;
                                            // boolean f1 = false, again = false;
                                            System.out.println(
                                                        "Would you like to choose hotel or party plot? (Enter H for hotel, P for party plot)");

                                            while (true) {
                                                
                                                String choice = sc.nextLine();
                                                if (choice.equalsIgnoreCase("H")) {
                                                    while (true) {
                                                        try {
                                                            System.out.println("How many days? ");
                                                            days = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    for (i = 1; i <= hotelMap.size(); i++) {
                                                        System.out.println((i) + "." + hotelMap.get(i).getName() + " - "
                                                                + hotelMap.get(i).getRentPerNight() + " per night");
                                                    }
                                                    System.out.println(
                                                            "Which hotel would you like to choose (Please select a number): ");
                                                    while (true) {
                                                        try {
                                                            hotelChoice = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    totalCost = hotelMap.get(hotelChoice).getRentPerNight() * days;
                                                    System.out
                                                            .println("Total cost for " + eventName + " event in "
                                                                    + hotelMap.get(hotelChoice).getName()
                                                                    + " hotel for " + days + " days is: " + totalCost
                                                                    + " Rs");
                                                    PreparedStatement pst1 = con.prepareStatement(
                                                            "INSERT INTO event(name,date,cost) VALUES(?,?,?)");
                                                    pst1.setString(1, eventName);
                                                    pst1.setString(2, date);
                                                    pst1.setDouble(3, totalCost);
                                                    int r1 = pst1.executeUpdate();
                                                    System.out.println(r1 > 0 ? "Added Successful" : "Failed");
                                                    eventBST.insert(new Event(eventName, date, totalCost));

                                                    if (again) {
                                                        try (BufferedWriter writer = new BufferedWriter(
                                                                new FileWriter("Hotel.txt", true))) {
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.write("Event Name:" + eventName);
                                                            writer.newLine();
                                                            writer.write("Date:" + date);
                                                            writer.newLine();

                                                            writer.write(
                                                                    "Venue:" + hotelMap.get(hotelChoice).getName());
                                                            writer.newLine();

                                                            writer.write("Total Cost: " + totalCost + " Rs");
                                                            writer.newLine();
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.flush();
                                                            writer.close();
                                                        } catch (IOException e) {
                                                            System.out.println(
                                                                    "An error occurred while saving event details: "
                                                                            + e.getMessage());
                                                        }
                                                    }
                                                    break;

                                                } else if (choice.equalsIgnoreCase("P")) {
                                                    while (true) {
                                                        try {
                                                            System.out.println("How many plates for catering? ");
                                                            plates = sc.nextInt();
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    System.out.println(
                                                            "Which party plot would you like to choose (Please select a number between Number): ");
                                                    for (i1 = 1; i1 <= partyPlotMap.size(); i1++) {
                                                        System.out.println(
                                                                (i1) + "." + partyPlotMap.get(i1).getName() + " - "
                                                                        + partyPlotMap.get(i1).getPerPlateCharge()
                                                                        + " per plate");
                                                    }
                                                    while (true) {
                                                        try {
                                                            partyPlotChoice = sc.nextInt();
                                                            break;

                                                        } catch (Exception e) {
                                                            System.out.println("Plz Enter Valid Number");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                    totalCost = partyPlotMap.get(partyPlotChoice).getPerPlateCharge()
                                                            * plates;
                                                    System.out.println(
                                                            "Total cost for " + eventName + " event in "
                                                                    + partyPlotMap.get(partyPlotChoice).getName()
                                                                    + " for " + plates + " plates is: " + totalCost
                                                                    + " Rs");

                                                    PreparedStatement pst2 = con.prepareStatement(
                                                            "INSERT INTO event(name,date,cost) VALUES(?,?,?)");
                                                    pst2.setString(1, eventName);
                                                    pst2.setString(2, date);
                                                    pst2.setDouble(3, totalCost);
                                                    int r2 = pst2.executeUpdate();
                                                    System.out.println(r2 > 0 ? "Added Successful" : "Failed");
                                                    eventBST.insert(new Event(eventName, date, totalCost));

                                                    if (again) {
                                                        try (BufferedWriter writer = new BufferedWriter(
                                                                new FileWriter("PartyPlot.txt", true))) {
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.write("Event Name:" + eventName);
                                                            writer.newLine();
                                                            writer.write("Date:" + date);
                                                            writer.newLine();

                                                            writer.write("Venue:"
                                                                    + partyPlotMap.get(partyPlotChoice).getName());
                                                            writer.newLine();
                                                            writer.write("Total Cost: " + totalCost + " Rs");
                                                            writer.newLine();
                                                            writer.write("-----------------------------------------");
                                                            writer.newLine();
                                                            writer.flush();
                                                            writer.close();
                                                        } catch (IOException e) {
                                                            System.out.println(
                                                                    "An error occurred while saving event details: "
                                                                            + e.getMessage());
                                                        }

                                                    }

                                                    break;
                                                }
                                            }

                                            break;
                                        case 2:
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");
                                            System.out.println("Avalaible Hotel:");
                                            System.out.println(
                                                    "---------------------------------------------------------------------------");

                                            for (int j = 1; j <= hotelMap.size(); j++) {
                                                System.out.println((j) + "." + hotelMap.get(j).getName() + " - "
                                                        + hotelMap.get(j).getRentPerNight() + " per night");
                                            }
                                            System.out.println(
                                                    "---------------------------------------------------------------------------");
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");

                                            System.out.println("Avalaible Party Plot:");
                                            System.out.println(
                                                    "---------------------------------------------------------------------------");

                                            for (int j1 = 1; j1 <= partyPlotMap.size(); j1++) {
                                                System.out.println(
                                                        (j1) + "." + partyPlotMap.get(j1).getName() + " - "
                                                                + partyPlotMap.get(j1).getPerPlateCharge()
                                                                + " per plate");
                                            }
                                            System.out.println(
                                                    "\n---------------------------------------------------------------------------");

                                            break;
                                        case 3:
                                            System.out.println("Enter username for change password");
                                            String user_change = sc.nextLine();
                                            String update = "call updateUserPassword(?,?)";
                                            CallableStatement cs = con.prepareCall(update);
                                            System.out.println("Enter new password:");
                                            // System.out.print("Password: ");
                                            String password11 = sc.nextLine();
                                            System.out.print("Confirm Password: ");
                                            String confirmPassword11 = sc.nextLine();

                                            if (!(password11.equals(confirmPassword11))) {
                                                System.out.print("Invalid....Try Again...!!");

                                            } else {
                                                cs.setString(1, user_change);
                                                cs.setString(2, password11);
                                                int r1 = cs.executeUpdate();
                                                System.out.println((r1 > 0) ? "Updation done" : "Updation fail");
                                            }
                                        case 4:
                                            EventPlanner();
                                            break;
                                        default:
                                            System.out.println("Invalid Choice..!!");
                                    }
                                }

                            case 3:
                                sc.close();
                                System.exit(0);
                            default:
                                System.out.println("Invalid Choice..!!");
                                break;
                        }
                    }
                case 2:
                    while (true) {
                        System.out.println("Username: ");
                        String username = sc.nextLine();
                        System.out.println("Password: ");
                        String password = sc.nextLine();
                        PreparedStatement pst = con
                                .prepareStatement("SELECT * FROM admin WHERE username=? AND password=? AND role=?");
                        pst.setString(1, username);
                        pst.setString(2, password);
                        pst.setString(3, "admin");
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            break;
                        }
                        System.out.println("Invalid Password..!");
                    }

                    while (true) {
                        System.out
                                .print("\n1.Add Admin\n2.Add Hotel\n3.Add Paty Plot\n4.Show Bookings\n5.change password\n6.Log Out\n\nEnter your choice: ");
                        int ch2 = sc.nextInt();
                        sc.nextLine();
                        switch (ch2) {
                            case 1:
                                System.out.print("Username: ");
                                String username = sc.nextLine();
                                String password, confirmPassword;
                                while (true) {
                                    System.out.print("Password: ");
                                    password = sc.nextLine();
                                    System.out.print("Confirm Password: ");
                                    confirmPassword = sc.nextLine();

                                    if (password.equals(confirmPassword)) {
                                        break;
                                    }
                                    System.out.print("Invalid....Try Again...!!");

                                }
                                PreparedStatement pst = con
                                        .prepareStatement("INSERT INTO user(username,password,role) VALUES(?,?,?)");
                                pst.setString(1, username);
                                pst.setString(2, password);
                                pst.setString(3, "Admin");
                                int r = pst.executeUpdate();
                                System.out.println(r > 0 ? "Added Successful" : "Failed");
                                Admin admin = new Admin(username, password);
                                adminMap.put(userMap.size() + 1, admin);
                                break;
                            case 2:
                                System.out.println("Enter hotel details:");

                                // Read hotel details from the user
                                System.out.print("Hotel Name: ");
                                String hotelName = sc.next();

                                System.out.print("Rating(out of 10): ");
                                double rating = sc.nextDouble();

                                System.out.print("Price: ");
                                int price = sc.nextInt();
                                sc.nextLine(); // Consume the newline character

                                System.out.print("Address: ");
                                String address = sc.nextLine();

                                // Generate a unique key for the hotel (you can use a counter for this)
                                int hotelId = hotelMap.size() + 1;

                                PreparedStatement pst1 = con
                                        .prepareStatement(
                                                "INSERT INTO venue(name,type,ratings,cost,location) VALUES(?,?,?,?,?)");
                                pst1.setString(1, hotelName);
                                pst1.setString(2, "Hotel");
                                pst1.setDouble(3, rating);
                                pst1.setInt(4, price);
                                pst1.setString(5, address);
                                int r1 = pst1.executeUpdate();
                                System.out.println(r1 > 0 ? "Hotel added successfully!" : "Failed");
                                Hotel hotel = new Hotel(hotelName, rating, price, address);

                                // Create a new Hotel object and add it to the map
                                hotelMap.put(hotelId, hotel);
                                break;
                            case 3:
                                System.out.print("PartyPlot Name: ");
                                String PlotName = sc.next();

                                System.out.print("Rating(out of 5): ");
                                double rating1 = sc.nextDouble();

                                System.out.print("Price: ");
                                int price1 = sc.nextInt();

                                sc.nextLine(); // Consume the newline character

                                System.out.print("Address: ");
                                String address1 = sc.nextLine();

                                // Generate a unique key for the hotel (you can use a counter for this)
                                int plotId = partyPlotMap.size() + 1;

                                PreparedStatement pst2 = con
                                        .prepareStatement(
                                                "INSERT INTO venue(name,type,ratings,cost,location) VALUES(?,?,?,?,?)");
                                pst2.setString(1, PlotName);
                                pst2.setString(2, "Party Plot");
                                pst2.setDouble(3, rating1);
                                pst2.setInt(4, price1);
                                pst2.setString(5, address1);
                                int r2 = pst2.executeUpdate();

                                System.out.println(r2 > 0 ? "Hotel added successfully!" : "Failed");
                                PartyPlot partyPlot = new PartyPlot(PlotName, rating1, price1, address1);

                                // Create a new Hotel object and add it to the map
                                partyPlotMap.put(plotId, partyPlot);
                                break;

                            case 4:
                                eventBST.inOrderRec();
                                // FileReader fr=new FileReader("Hotel.txt");
                                // FileReader fr1=new FileReader("PartyPlot.txt");
                                // System.out.println(fr);
                                // System.out.println(fr1);
                                break;
                            case 5:
                                String update = "call updateUserPassword(?,?)";
                                CallableStatement cs = con.prepareCall(update);
                                System.out.println("Enter admin name for change");
                                String admin_name = sc.nextLine();
                                System.out.println("Enter new password:");
                                // System.out.print("Password: ");
                                String password11 = sc.nextLine();
                                System.out.print("Confirm Password: ");
                                String confirmPassword11 = sc.nextLine();
                                if (!(password11.equals(confirmPassword11))) {
                                    System.out.println("Invalid....Try Again...!!");

                                    break;
                                } else {
                                    cs.setString(1, admin_name);
                                    cs.setString(2, password11);
                                    int a = cs.executeUpdate();
                                    System.out.println((a > 0) ? "Updation done" : "Updation fail");
                                }
                                break;

                            case 6:
                                EventPlanner();
                                break;
                            default:
                                System.out.println("Invalid Choice..!!");
                        }
                    }
                default:
                    System.out.println("Invalid Choice..!!");
                    break;
            }
        }
    }

    static boolean isValidDate(String inputDate) {
        String[] dateParts = inputDate.split("/");

        // Check if day and date are of length 2
        if (dateParts.length != 3 || dateParts[0].length() != 4 || dateParts[1].length() != 2
                || dateParts[2].length() != 2) {
            System.out.println("Invalid date format. It should be in the format of yyyy/mm/dd");
            return false;
        }

        int day = Integer.parseInt(dateParts[2]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[0]);

        // Check if year is between 2023 and 9999
        if (year < 2024 || year > 2026) {
            System.out.println("Year should be between 2024 and 2026");
            return false;
        }
        // Check if day is valid according to month and year
        switch (month) {
            case 1: // January
            case 3: // March
            case 5: // May
            case 7: // July
            case 8: // August
            case 10: // October
            case 12: // December
                if (day <= 31) {
                    return true;
                } else {
                    System.out.println("There are only 31 days in this month");
                    return false;
                }

            case 4: // April
            case 6: // June
            case 9: // September
            case 11: // November
                if (day <= 30) {
                    return true;
                } else {
                    System.out.println("There are only 30 days in this month");
                    return false;
                }
            case 2: // February
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (day <= 29) {
                        return true;
                    } else {
                        System.out.println("February has only 29 days in a leap year");
                        return false;
                    }
                } else {
                    if (day <= 28) {
                        return true;
                    } else {
                        System.out.println("February has only 28 days in a non-leap year");
                        return false;
                    }
                }
            default:
                System.out.println("Invalid month entered");
                return false;
        }
    }
}

class Hotel {
    String name;
    double ratings;
    int rentPerNight;
    String location;

    Hotel() {

    }

    public Hotel(String name, double ratings, int rentPerNight, String location) {
        this.name = name;
        this.ratings = ratings;
        this.rentPerNight = rentPerNight;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public int getRentPerNight() {
        return rentPerNight;
    }

    public void setRentPerNight(int rentPerNight) {
        this.rentPerNight = rentPerNight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    void setHotelHM(Connection con, HashMap<Integer, Hotel> hotelMap) throws Exception {
        int i = 1;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM venue WHERE type=?");
        pst.setString(1, "Hotel");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            hotelMap.put(i++, new Hotel(rs.getString(2), rs.getDouble(4), rs.getInt(5), rs.getString(6)));
        }
    }
}

class PartyPlot {
    String name;
    double ratings;
    int perPlateCharge;
    String location;

    PartyPlot() {

    }

    public PartyPlot(String name, double ratings, int perPlateCharge, String location) {
        this.name = name;
        this.ratings = ratings;
        this.perPlateCharge = perPlateCharge;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public int getPerPlateCharge() {
        return perPlateCharge;
    }

    public void setPerPlateCharge(int perPlateCharge) {
        this.perPlateCharge = perPlateCharge;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    void setPlotHM(Connection con, HashMap<Integer, PartyPlot> partyPlotMap) throws Exception {
        int i = 1;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM venue WHERE type=?");
        pst.setString(1, "Party Plot");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            partyPlotMap.put(i++, new PartyPlot(rs.getString(2), rs.getDouble(4), rs.getInt(5), rs.getString(6)));
        }
    }

}

class User {
    String userName, password, role;

    User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.role = "User";
    }

    void setUserHM(Connection con, HashMap<Integer, User> userMap) throws Exception {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM user WHERE role=?");
        pst.setString(1, "User");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            userMap.put(rs.getInt(1), new User(rs.getString(2), rs.getString(3)));
        }
    }
}

class Admin {
    String userName, password, role;

    Admin() {

    }

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.role = "Admin";
    }

    void setAdminHM(Connection con, HashMap<Integer, Admin> adminMap) throws Exception {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM user WHERE role=?");
        pst.setString(1, "Admin");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            adminMap.put(rs.getInt(1), new Admin(rs.getString(2), rs.getString(3)));
        }
    }
}

class Event {

    String name;
    String date;
    double cost;

    Event() {

    }

    public Event(String name, String date, double cost) {
        this.name = name;
        this.date = date;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Event: " + name + ", Date: " + date + ", Cost: RS." + cost;
    }
}

class BST {
    class Node {
        Event event;
        Node left;
        Node right;

        Node(Event event) {
            this.event = event;
            left = right = null;
        }
    }

    Node root = null;

    void setEventBST(Connection con, BST eventBST) throws Exception {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM event");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            eventBST.insert(new Event(rs.getString(2), rs.getString(3), rs.getDouble(4)));
        }
    }

    public void insert(Event event) {
        root = insertRec(root, event);
    }

    Node insertRec(Node root, Event event) {
        if (root == null) {
            root = new Node(event);
            return root;
        }
        if (event.getDate().compareTo(root.event.getDate()) < 0) {
            root.left = insertRec(root.left, event);
            return root;
        } else {
            root.right = insertRec(root.right, event);
            return root;
        }
    }

    void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.println(root.event);
            inOrder(root.right);
        }
    }

    void inOrderRec() {
        inOrder(root);
        System.out.println();
    }
}
