/* ************************************************************************************************************************
#  *                                                                                                                      *
#  * @License Starts                                                                                                      *
#  *                                                                                                                      *
#  * Copyright © 2015 - present. MongoExpUser.  All Rights Reserved.                                                      *
#  *                                                                                                                      *
#  * License: MIT - hhttps://github.com/MongoExpUser/euro-soccer-league-full-text-search-with-postgres/blob/main/LICENSE  *
#  *                                                                                                                      *
#  * @License Ends                                                                                                        *
#  ************************************************************************************************************************
# *  1) The class implements method for:                                                                                  *
# *     a) Connecting to and disconnecting from PostgreSQL or PostgreSQL-Compatible DB                                    *
# *     b) Running SQL queries on  PostgreSQL or PostgreSQL-Compatible DBs                                                *
# *  2) Implementation is done with Java version 17 and Java JDBC API                                                     *
# *                                                                                                                       *
# ************************************************************************************************************************/



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class PostgreSQLApp
{
    public void connectAndRunQueries(String host, String dbName, String schemaName, String url, String user, String password, String sslrootcertPath) throws SQLException, ClassNotFoundException
    {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "verify-ca");  // "verify-full" || "verify-ca" || disable	|| "allow" || prefer	|| "require"	
        props.setProperty("sslrootcert", sslrootcertPath);
        Integer statementTimeout = 60000;
        String options = String.format("-c search_path=%s,public -c statement_timeout=%s", schemaName, statementTimeout);
        props.setProperty("options", options);
        
        try
        {
            // conn = DriverManager.getConnection(url, user, password);
            Connection conn = DriverManager.getConnection(url, props);
            
            if(conn instanceof Connection)
            {
              System.out.println("Successfully connected to PostgreSQL server.");
              
              // then add queries to run
              String query0 = "SELECT datname, usename, ssl, client_addr FROM pg_stat_ssl JOIN pg_stat_activity ON pg_stat_ssl.pid = pg_stat_activity.pid;";
              String query1 = "SELECT VERSION()"; 
              String query2 = "SELECT * FROM pg_stat_ssl ORDER BY ssl;";   
              String query3 = "SELECT * FROM league.soccer LIMIT 5;";  

                  
              Statement st = conn.createStatement();
              String [] queryList = new String[4];
              queryList[0] = query0;
              queryList[1] = query1;
              queryList[2] = query2;
              queryList[3] = query3;
              // add other queries from the repo and expand the list as defined in the queryList above
          
              for(int index = 0; index < queryList.length; index++)
              {
                  // execute query
                  ResultSet rs = st.executeQuery(queryList[index]);
                
                  // fetch and show query result
                  while(rs.next())
                  {
                      if(index == 1)
                      {
                        System.out.println("==================================");
                        System.out.println("Version: " + rs.getString(1));
                        System.out.println("==================================");
                      }
                  }
                  
                  rs.close();
              }
                
              st.close();
              conn.close();
              
              System.out.println("Connection closed....");
            }
        }
        catch (SQLException error)
        {
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        String host = "host";
        String dbName = "euro";
        String schemaName = "league";
        String engineName = "postgresql";
        String url = String.format("jdbc:%s://%s/%s", engineName, host, dbName);
        String user = "user";
        String password = "password";
        String sslrootcertPath = "/path/to/root-cert.pem";
        PostgreSQLApp app = new PostgreSQLApp();
        app.connectAndRunQueries(host, dbName, schemaName, url, user, password, sslrootcertPath);
    }
}
