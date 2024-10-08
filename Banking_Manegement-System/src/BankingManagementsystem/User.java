package BankingManagementsystem;

import java.sql.*;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner sc;
    public User(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc=sc;
    }

    public void register(){
        sc.nextLine();
        System.out.println("Full Name : ");
        String full_name = sc.nextLine();

        System.out.println("Email : ");
        String email = sc.nextLine();

        System.out.println("password: ");
        String password = sc.nextLine();

        if(user_exist(email)){
            System.out.println("User Already Exists for this email adress");
            return;
        }
        String register_query = "INSERT INTO user(full_name,email,password) values(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            int rowsaffected = preparedStatement.executeUpdate();
            if(rowsaffected>0){
                System.out.println("Registration sucessfull...");
            }else{
                System.out.println("Registration failed....");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String login(){
        sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Password: ");
        String password = sc.nextLine();

        String login_query = "SELECT * FROM user where email=? AND password=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return email;

            }else{
                return null;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean user_exist(String email) {
        String query = "SELECT * FROM USER WHERE EMAIL = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            } else{
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
