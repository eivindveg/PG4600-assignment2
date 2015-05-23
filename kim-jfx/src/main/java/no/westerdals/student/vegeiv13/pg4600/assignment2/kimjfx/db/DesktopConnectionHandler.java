package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.spring.DaoFactory;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.table.TableUtils;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.utils.OSHandler;

import java.io.File;
import java.sql.SQLException;

public class DesktopConnectionHandler extends ConnectionHandler {

    private final JdbcConnectionSource connectionSource;

    public DesktopConnectionHandler() {
        try {
            Class.forName("org.sqlite.JDBC");
            File file = OSHandler.getWorkingDirectory();
            String dbConnectionString = "jdbc:sqlite:/" + file.getAbsolutePath();
            connectionSource = new JdbcConnectionSource(dbConnectionString);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T, ID> Dao<T, ID> getDao(Class<T> clazz) {
        try {
            Dao<T, ID> dao = DaoFactory.createDao(connectionSource, clazz);
            System.setProperty(TableCreator.AUTO_CREATE_TABLES, "true");
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            return dao;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
