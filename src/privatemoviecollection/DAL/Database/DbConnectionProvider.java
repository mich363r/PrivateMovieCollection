/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL.Database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
/**
 *
 * @author kokus
 */
public class DbConnectionProvider
{
    private static final String PROP_FILE = "Database_Settings.txt";
    private final SQLServerDataSource ds;

    /*
    establishes a connection to the database based on the Database_Settings file
    */
    public DbConnectionProvider() throws FileNotFoundException, IOException
    {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        ds = new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("Server"));
        ds.setDatabaseName(databaseProperties.getProperty("Database"));
        ds.setUser(databaseProperties.getProperty("User"));
        ds.setPassword(databaseProperties.getProperty("Password"));
    }

    
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
}
