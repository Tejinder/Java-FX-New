package sample.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sample.model.Employee;
 

public class JdbcDerbyConnection {
 
	private static  String dbURL = "jdbc:derby://localhost:1527/employeedb;create=true";
    private static String tableName = "employee";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

	
    
    private static void createConnection()
    {
        try
        {
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
    
    public static void insertEmployee(String name, String department, String position)
    {
    	
    	if(conn==null)
    	{
    		createConnection();
    	}
        try
        {
            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + "(name, department, position) values ('" +
            		name + "','" + department + "','" + position +"')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        
    }
    
    public static List<Employee> getAllEmployees()
    {
    	
    	List<Employee> employeeList = new ArrayList<Employee>();
    	if(conn==null)
    	{
    		createConnection();
    	}
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from employee");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                int id = results.getInt(1);
                String name = results.getString(2);
                String department = results.getString(3);
                String position = results.getString(4);

                System.out.println(id + "\t\t" + name + "\t\t" + department + "\t\t" + position);
                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setDepartment(department);
                employee.setPosition(position);
                employeeList.add(employee);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	return employeeList;
        
    }
    
    
    public static void updateEmployeeName(String name, Integer employeeid)
    {
    
    	
    	if(conn==null)
    	{
    		createConnection();
    	}
    	try
        {
            stmt = conn.createStatement();
            int record = stmt.executeUpdate("update employee set name = '"+name + "' where id = "+employeeid);
            
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	
        
    }
    
    public static void updateEmployeeDepartment(String department, Integer employeeid)
    {
    	
    	
    	if(conn==null)
    	{
    		createConnection();
    	}
    	try
        {
            stmt = conn.createStatement();
            int record = stmt.executeUpdate("update employee set department = '"+department + "' where id = "+employeeid);
            
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	
        
    }
    
    public static void updateEmployeePosition(String position, Integer employeeid)
    {
    	
    	if(conn==null)
    	{
    		createConnection();
    	}
    	try
        {
            stmt = conn.createStatement();
            int record = stmt.executeUpdate("update employee set position = '"+position + "' where id = "+employeeid);
            
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	
        
    }
}