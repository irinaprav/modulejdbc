package com.module.alevel;

import com.module.alevel.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;


public class ClassRepositorySchool {

    private final Supplier<Connection> connectionSupplier;

    public ClassRepositorySchool(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public void save(SchoolClass schoolClass) throws RepositoryException {
        String className = schoolClass.getClassName();
        Long amount = schoolClass.getAmount();
        String classTeacher = schoolClass.getClassTeacher();
        String sql = "INSERT INTO classTable (classname, amount, classTeacher) VALUES (?, ?, ?) ";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.setString(1, className);
            statement.setLong(2, amount);
            statement.setString(3, classTeacher);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    public void save(List<SchoolClass> schoolClasses) throws RepositoryException {
        String sql = "INSERT INTO classTable (classname, amount, classTeacher) VALUES (?, ?, ?) ";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            for (SchoolClass schoolClass : schoolClasses) {
                statement.setString(1, schoolClass.getClassName());
                statement.setLong(2, schoolClass.getAmount());
                statement.setString(3, schoolClass.getClassTeacher());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }


    public List<SchoolClass> list() throws RepositoryException {
        String sql = "SELECT * FROM classTable";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<SchoolClass> schoolClasses = null;
            while (resultSet.next()) {
                schoolClasses.add(new SchoolClass(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("amount"),
                        resultSet.getString("classTeacher")
                ));
            }
            return schoolClasses;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }


    public SchoolClass get(String name) throws RepositoryException {
        String sql = "SELECT * FROM classTable where classname = ?";
        SchoolClass schoolClass = null;
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return schoolClass;
            }
            resultSet.first();
            schoolClass = new SchoolClass(
                    resultSet.getLong("id"),
                    resultSet.getString("classname"),
                    resultSet.getLong("amount"),
                    resultSet.getString("classTeacher")
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return schoolClass;
    }


    public void delete(SchoolClass schoolClass) throws RepositoryException {
        String sql = "DELETE FROM classTable WHERE id = ?";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.setLong(1, schoolClass.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public void deleteALL() throws RepositoryException {
        String sql = "DELETE FROM classTable ";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }


}
