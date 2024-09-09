import UI.MainMenu;
import UI.UserMenu;

import java.sql.SQLException;
import java.util.Scanner;

import static UI.MainMenu.menu;
import static UI.UserMenu.menuuser;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws Exception {
        System.out.println("Welcome Sir to Ecomove");
        while (true) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|                             |");
            System.out.println("|  1 : Admin Hub              |");
            System.out.println("|  2 : Users Hub              |");
            System.out.println("|  3 : return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                {

                    System.out.println("Please give me the admin email :D");
                    String email = scanner.nextLine();
                    boolean checkedemail = false;
                    while(!checkedemail){

                        if(!email.equals("admin@gmail.com")){
                            System.out.println("The email is wrong please enter another one");
                            email = scanner.nextLine();
                        }
                        else{
                            checkedemail = true;
                        }
                    }

                    System.out.println("Now can you please give me the password");
                    String password = scanner.nextLine();

                    boolean checkedpassword = false;
                    while(!checkedpassword){

                        if(!password.equals("admin1234")){
                            System.out.println("The password is wrong please enter another one");
                            password = scanner.nextLine();
                        }
                        else{
                            checkedpassword = true;
                        }
                    }


                    if(checkedemail && checkedpassword) {
                        System.out.println("Good luck MR");
                        menu();
                    }


                    break;
                }
                case 2:
                    menuuser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}