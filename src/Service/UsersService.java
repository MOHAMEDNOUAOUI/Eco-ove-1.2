package Service;

import Model.Users;
import Repository.*;
import Service.Interface.UsersServiceInterface;

import javax.swing.*;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Enum.*;

public class UsersService implements UsersServiceInterface {


    public Scanner scanner = new Scanner(System.in);
    public ContratsRepository contratsRepository = new ContratsRepository();
    public OffresRepository offresRepository = new OffresRepository();
    public ContratsService contratsService = new ContratsService();
    public OffresService offresService = new OffresService();
    public TrajetRepository trajetRepository = new TrajetRepository();
    public BilletsRepository billetsRepository = new BilletsRepository();
    public UserRepository userRepository = new UserRepository();


    @Override
    public void addUser() throws SQLException, InterruptedException {
        System.out.println("Welcome to user creation system");
        System.out.println();


        System.out.println("Please give me your first name");
        String nom = scanner.nextLine();
        if (!nom.matches("^[a-zA-Z ]+$")) {
            System.out.println("Invalid first name. Please use only alphabetic characters.");
            return;
        }


        System.out.println("Last name");
        String prenom = scanner.nextLine();
        if (!prenom.matches("^[a-zA-Z ]+$")) {
            System.out.println("Invalid last name. Please use only alphabetic characters.");
            return;
        }

        // Email validation
        System.out.println("Now give me your email");
        String email = scanner.nextLine();
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("Invalid email format.");
            return;
        }
        System.out.println("First I should check the email if it exists or not, can you hold on ...");
        Thread.sleep(3000);

        Users user = userRepository.getUserByEmail(email);

        if (user == null) {
            System.out.println("Give me the phone number");
            String numero_de_telephon = scanner.nextLine();
            if (!numero_de_telephon.matches("^[0-9]{10}$")) {
                System.out.println("Invalid phone number. Please enter a 10-digit number.");
                return;
            }

            System.out.println("Statut of this user (" + Arrays.toString(StatutUser.values()) + ") ");
            String statut_user = scanner.nextLine();
            boolean isValidStatus = Arrays.stream(StatutUser.values())
                    .anyMatch(status -> status.name().equals(statut_user));
            if (!isValidStatus) {
                System.out.println("Invalid status. Please enter one of the following: " + Arrays.toString(StatutUser.values()));
                return;
            }

            Users userR = new Users();
            userR.setNom(nom);
            userR.setPrenom(prenom);
            userR.setEmail(email);
            userR.setNumero_de_telephon(numero_de_telephon);
            userR.setStatut_user(StatutUser.valueOf(statut_user));
            userRepository.addToDatabase(userR);

        } else {
            System.out.println("This user already exists.");
            return;
        }
    }


    @Override
    public void getUser() throws SQLException {
        System.out.println("Please can you give me the user email");
        String email = scanner.nextLine();

        Users user = userRepository.getUserByEmail(email);
        if(user != null) {
            System.out.println("+------------------------------------------+");
            System.out.println("|              User Details                |");
            System.out.println("+------------------------------------------+");
            System.out.printf("| %-15s : %-25s |\n", "First Name", user.getNom());
            System.out.printf("| %-15s : %-25s |\n", "Last Name", user.getPrenom());
            System.out.printf("| %-15s : %-25s |\n", "Email", user.getEmail());
            System.out.printf("| %-15s : %-25s |\n", "Phone Number", user.getNumero_de_telephon());
            System.out.printf("| %-15s : %-25s |\n", "Status", user.getStatut_user());
            System.out.println("+------------------------------------------+");
        }
        else {
            System.out.println("I couldnt find this user sorry");
        }
    }

    @Override
    public void getAllUsers() throws SQLException, InterruptedException {
        List<Users> usersLists = userRepository.getAllUsers();

        if (usersLists.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        // Print table header
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.println("| ID           | First Name       | Last Name        | Email                  | Phone Number  | Status |");
        System.out.println("+---------------------------------------------------------------------------------------------------+");

        // Print each user in a row
        for (Users user : usersLists) {
            System.out.printf("| %-12s | %-15s | %-15s | %-22s | %-13s | %-6s |\n",
                    user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero_de_telephon(), user.getStatut_user());
        }

        // Print table footer
        System.out.println("+---------------------------------------------------------------------------------------------------+");
    }
    @Override
    public void updateUser() throws InterruptedException, SQLException {

    }

    @Override
    public void deleteUser() throws SQLException {
            System.out.println("Please give me the user email");
            String email = scanner.nextLine();

            Users user = userRepository.getUserByEmail(email);

            if(user != null) {
                System.out.println("Are you sure you want to delete this user (yes/no)");
                String choicce = scanner.nextLine();

                if(choicce.equals("yes")){
                    userRepository.deleteUser(user);
                }else{
                    return;
                }
            }
    }
}
