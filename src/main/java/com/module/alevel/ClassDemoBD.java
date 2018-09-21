package com.module.alevel;

import com.module.alevel.exceptions.MyConnectionPool;
import com.module.alevel.exceptions.RepositoryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Supplier;

public class ClassDemoBD {

    public static void main(String[] args) throws IOException {

        Properties connectionProps = new Properties();

        try (InputStream props = ClassDemoBD.class.getResourceAsStream("/datasourse.properties")) {
            connectionProps.load(props);
        } catch (IOException e) {
            panic(e);
        }
        String url = connectionProps.getProperty("url");
        try (Connection connection = DriverManager.getConnection(url, connectionProps)) {
            Supplier<Connection> connectionSupplier = new MyConnectionPool(connection);
            ClassRepositorySchool repository = new ClassRepositorySchool(connectionSupplier);
            repository.deleteALL();
        } catch (SQLException | RepositoryException e){
            panic(e);
        }
        BufferedReader reader = new BufferedReader(new FileReader("/home/irina/IdeaProjects/module_csv/src/main/java/com/module/alevel/files/test.csv"));
        List<SchoolClass> repositorySchool = new ArrayList<SchoolClass>();
        String line = null;
        Scanner scanner = null;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            SchoolClass sclass = new SchoolClass();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                sclass.setId(null);
                if (index == 0)
                    sclass.setClassName(data);
                else if (index == 1)
                    sclass.setAmount(new Long(data));
                else if (index == 2)
                    sclass.setClassTeacher(data);
                else
                    System.out.println("wrong data:" + data);
                index++;
            }
            index = 0;
            repositorySchool.add(sclass);
        }

        reader.close();

        try (Connection connection = DriverManager.getConnection(url, connectionProps)) {
            Supplier<Connection> connectionSupplier = new MyConnectionPool(connection);
            ClassRepositorySchool repository = new ClassRepositorySchool(connectionSupplier);
            repository.save(repositorySchool);
        } catch (SQLException | RepositoryException e){
            panic(e);
        }

        String name = "";
        scanner = new Scanner(System.in);
        while (!(name.equals("quit"))) {
            System.out.println("Enter please name of class");
            name = scanner.next();
            try (Connection connection = DriverManager.getConnection(url, connectionProps)) {
                Supplier<Connection> connectionSupplier = new MyConnectionPool(connection);
                ClassRepositorySchool repository = new ClassRepositorySchool(connectionSupplier);
                if (repository.get(name) == null) {
                    System.out.println("No such elements");
                } else {
                    System.out.println(repository.get(name).toString());
                }
            } catch (SQLException | RepositoryException e){
                panic(e);
            }
        }
    }


    private static void panic(Throwable e) {
        e.printStackTrace();
        System.exit(1);
    }
}
