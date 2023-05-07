package com.example.bbm384oyd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


//for database connection check
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;



@SpringBootApplication
public class Bbm384oydApplication {

    //for database connection check
    @Autowired
    private DataSource dataSource;



    public static void main(String[] args) {
        SpringApplication.run(Bbm384oydApplication.class, args);
        
    }

    @Bean
    public CommandLineRunner commandLineRunner(Environment environment) {
        return args -> {
            String port = environment.getProperty("local.server.port");

			System.out.println("\n \n \n SERVER STARTED");
            System.out.println("Running on port: " + port);
			System.out.println("note:to connect server use https://localhost:8080");

            // Check and print the database connection
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Database URL: " + connection.getMetaData().getURL());
                System.out.println("Database Username: " + connection.getMetaData().getUserName());
                System.out.println("Database connection successful!");
            } catch (SQLException e) {
                System.out.println("Error connecting to the database:");
                e.printStackTrace();
            }
            
        };
    }
}
