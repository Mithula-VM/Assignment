import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.util.regex.*;

class Employee 
{
    	private int empId;
    	private String name;
	private int age;
    	private double salary;
    	private String designation;

    	public Employee(String designation) 
	{
        	this.empId = EmpId.readId();
        	this.name = Name.readName();
        	this.age = Age.readAge(21,60);
        	this.salary = Salary.readSalary();
      		if(designation.equals("Others"))
			this.designation = Designation.readDesignation();
		else
			this.designation = designation;
    	}

    	public int getEmpId() 
	{
        	return empId;
    	}

    	public void setEmpId(int empId) 
	{
        	this.empId = empId;
    	}

    	public String getName() 
	{
        	return name;
    	}

    	public void setName(String name) 
	{
        	this.name = name;
    	}

    	public int getAge() 
	{
        	return age;
    	}

    	public void setAge(int age) 
	{
        	this.age = age;
    	}

    	public double getSalary() 
	{
        	return salary;
    	}

    	public void setSalary(double salary) 
	{
        	this.salary = salary;
    	}

    	public String getDesignation() 
	{
        	return designation;
    	}

   	public void setDesignation(String designation) 
	{
        	this.designation = designation;
    	}
}

final class Clerk extends Employee
{
	Clerk()
	{
		super("Clerk");
	}
	public static Employee getClerk()
	{
		return new Clerk();
	}
}

final class Programmer extends Employee
{
	Programmer()
	{
		super("Programmer");
	}
	public static Employee getProgrammer()
	{
		return new Programmer();
	}
}

final class Manager extends Employee
{
	Manager()
	{
		super("Manager");
	}
	public static Employee getManager()
	{
		return new Manager();
	}
}

final class OtherEmp extends Employee
{
	OtherEmp()
	{
		super("Others");
	}
	public static Employee getOtherEmp()
	{
		return new OtherEmp();
	}
}

abstract class EmployeeFactory
{
	public static Employee getEmployee(String designation)
	{
		Employee e = null;
		switch(designation)
		{
			case "Clerk":
				e =  Clerk.getClerk();
				break;
			case "Programmer":
				e = Programmer.getProgrammer();	
				break;
			case "Manager":
				e = Manager.getManager();
				break;
			case "Others":
				e = OtherEmp.getOtherEmp();
				break;
		}
		return e;
	}
}

interface EmpDAO
{
	public void save(Employee emp);
	public void display(int choice);
	public void search(int choice);
	public void appraise(int id, double amount);
	public void remove(int id);
}

class DBConnection
{
	private static Connection con = null;
	static 
	{
		try
		{
            		Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb", "postgres", "tiger");
        	} 
		catch (SQLException | ClassNotFoundException e) 
		{
            		//System.out.println(e.getMessage());
			e.printStackTrace();
        	}
	}
	public static Connection getConnection()
	{
		return con;
	}
	public static void closeConnection() 
	{
    		try 
		{
        		if (con != null && !con.isClosed()) 
			{
            			con.close();
        		}
    		} 
		catch (SQLException e) 
		{
        		System.out.println(e.getMessage());
    		}
	}
}

class EmpDAOimpl
{
       	
    	void save(Employee emp) 
	{
        	try 
		{
            		Connection con = DBConnection.getConnection();
			String query = "INSERT INTO employee3 (empId, name, age, salary, designation) VALUES (?, ?, ?, ?, ?)";
            		PreparedStatement pstmt = con.prepareStatement(query);
            		pstmt.setInt(1, emp.getEmpId());
            		pstmt.setString(2, emp.getName());
            		pstmt.setInt(3, emp.getAge());
            		pstmt.setDouble(4, emp.getSalary());
            		pstmt.setString(5, emp.getDesignation());
            
            		pstmt.executeUpdate();
            		pstmt.close();
            
            		System.out.println("Employee created successfully!");
        	} 
		catch (Exception e) 
		{
            		System.out.println(e.getMessage());
        	}
    	}

    	void display(int choice) 
	{
        	try 
		{
            		Connection con = DBConnection.getConnection();
			String query = "";
			switch(choice)
			{
				case 1:
                             		query = "SELECT * FROM employee3 ORDER BY empid";
					break;
				case 2:
                             		query = "SELECT * FROM employee3 ORDER BY name";
					break;
				case 3:
                             		query = "SELECT * FROM employee3 ORDER BY age";
					break;
				case 4:
                             		query = "SELECT * FROM employee3 ORDER BY salary";
					break;
				case 5:
                             		query = "SELECT * FROM employee3 ORDER BY designation";
					break;
            		}
	

            		Statement stmt = con.createStatement();
            		ResultSet rs = stmt.executeQuery(query);

           	 	while (rs.next()) 
			{
                		System.out.println("Employee ID: " + rs.getInt("empId"));
                		System.out.println("Name: " + rs.getString("name"));
                		System.out.println("Age: " + rs.getInt("age"));
                		System.out.println("Salary: " + rs.getDouble("salary"));
                		System.out.println("Designation: " + rs.getString("designation"));
                		System.out.println("-------------------");
            		}
            
            		rs.close();
            		stmt.close();
        	} 
		catch (SQLException e) 
		{
            		System.out.println(e.getMessage());
        	}
    	}

   	void appraise(int id, double amount) 
	{
        	try 
		{
			Connection con = DBConnection.getConnection();
            		String query = "UPDATE employee3 SET salary = salary+? WHERE empId = ?";
            		PreparedStatement pstmt = con.prepareStatement(query);
            		pstmt.setDouble(1, amount);
            		pstmt.setInt(2, id);
            
            		int rowsUpdated = pstmt.executeUpdate();
            		if (rowsUpdated > 0) 
			{
                		System.out.println("Employee appraisal successful!");
            		} 
			else 
			{
                		System.out.println("Employee not found!");
            		}
            		pstmt.close();
        	} 
		catch (Exception e) 
		{
            		System.out.println(e.getMessage());
        	}
    	}

    	void remove(int id) 
	{
        	try 
		{
            		Connection con = DBConnection.getConnection();   
            		String query = "DELETE FROM employee3 WHERE empId = ?";
            		PreparedStatement pstmt = con.prepareStatement(query);
            		pstmt.setInt(1, id);
            
            		int rowsDeleted = pstmt.executeUpdate();
            		if (rowsDeleted > 0) 
			{
                		System.out.println("Employee removed successfully!");
            		} 
			else 
			{
                		System.out.println("Employee not found!");
            		}
            
            		pstmt.close();
        	} 
		catch (SQLException e) 
		{
            		System.out.println(e.getMessage());
        	}
    	}

    	void search(int choice) 
	{
        	try 
		{
            		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Connection con = DBConnection.getConnection(); 
            		int empId=0;
	    		String name="";
	    		String designation="";
            		String searchQuery = "";
            		
			switch(choice)
			{
				case 1:
                			System.out.print("Enter Employee ID: ");
                			empId = Integer.parseInt(reader.readLine());
                			searchQuery = "SELECT * FROM employee3 WHERE empId = ?";
					break;
				case 2:
                			System.out.print("Enter Employee Name: ");
                			name = reader.readLine();
                			searchQuery = "SELECT * FROM employee3 WHERE name = ?";
					break;
				case 3: 
                			System.out.print("Enter Designation: ");
                			designation = reader.readLine();
                			searchQuery = "SELECT * FROM employee3 WHERE designation = ?";
					break;
            		}
			PreparedStatement pstmt = con.prepareStatement(searchQuery);
                       	
			switch(choice)
			{
				case 1:
					pstmt.setInt(1, empId);
					break;
				case 2:
					pstmt.setString(1, name);
					break;
				case 3:
					pstmt.setString(1, designation);
					break;
			}
            		ResultSet rs = pstmt.executeQuery();
            		while (rs.next()) 
			{
               			System.out.println("\nEmployee ID: " + rs.getInt("empId"));
                		System.out.println("Name: " + rs.getString("name"));
                		System.out.println("Age: " + rs.getInt("age"));
                		System.out.println("Salary: " + rs.getDouble("salary"));
                		System.out.println("Designation: " + rs.getString("designation"));
                		System.out.println("-------------------");
            		}
            
            		rs.close();
            		pstmt.close();
        	} 
		catch (SQLException | IOException e) 
		{
            		System.out.println("Error searching for employee: " + e.getMessage());
        	}
    	}
}


public class EmployeeManagementApp10 
{
    	public static void main(String[] args) 
	{
        	int choice1 = 0, choice2 = 0, choice3 = 0, choice4 = 0;
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		EmpDAOimpl empdao = new EmpDAOimpl();
		Employee e = null;
        	do 
		{
            		try
			{
				System.out.println("-------------------");
            			System.out.println("1. Create" + "\n" + "2. Display" + "\n" + "3. Appraisal" + "\n" + "4. Remove" + "\n" + "5. Search" + "\n" + "6. Exit");
            			System.out.println("-------------------");
            			choice1 = Menu.readChoice(6);
            
            			switch (choice1) 
				{
                			case 1:
                    				do 
						{
                        				System.out.println("-------------------");
                        				System.out.println("1. Clerk\n" + "2. Programmer" + "\n" + "3. Manager" + "\n" + "4. Others" + "\n" + "5. Exit");
                        				System.out.println("-------------------");
                        				choice2 = Menu.readChoice(5);
                       
                      		  			switch (choice2) 
							{
                            					case 1:
                                					e = EmployeeFactory.getEmployee("Clerk");
									empdao.save(e);
                                					break;
                            					case 2:
                                					e = EmployeeFactory.getEmployee("Programmer");
									empdao.save(e);
                                					break;
                            					case 3:
                                					e = EmployeeFactory.getEmployee("Manager");
									empdao.save(e);
                                					break;
 	                           				case 4:
        	                        				e = EmployeeFactory.getEmployee("Others");
									empdao.save(e);
                	                				break;
                        	    				case 5:
                                					break;
                        				}
                    				} 
						while (choice2 != 5);
        	            			break;
                	
               	 			case 2:
                    				do
						{
							System.out.println("-------------------");
							System.out.println("1. Based on ID\n"+"2. Based on Name"+"\n"+"3. Based on Age"+"\n"+"4. Based on Salary"+"\n"+"5. Based on Designation" + "\n" + "6. Exit");
							System.out.println("-------------------");
							choice3 = Menu.readChoice(6);
							empdao.display(choice3);
						}
						while (choice3 != 6);
                    				break;
	
	                		case 3:
						System.out.print("Enter Employee ID to appraise: ");
            					int id = Integer.parseInt(reader.readLine());
            					System.out.print("Enter amount to increment/decrement: ");
            					double amount = Double.parseDouble(reader.readLine());
        	            			empdao.appraise(id, amount);
                	    			break;
		
	                		case 4:
        	            			System.out.print("Enter Employee ID to remove: ");
            					int empId = Integer.parseInt(reader.readLine());
						empdao.remove(empId);
                	    			break;
	
	                		case 5:
						do
						{
							System.out.println("-------------------");
							System.out.println("1.Search by ID \n2. Search by Name \n3. Search by Designation"+ "\n" + "4. Exit");
							System.out.println("-------------------");
							choice4 = Menu.readChoice(4);
	                    				empdao.search(choice4);
						}
						while (choice4 != 4);
        	            			break;
	
	                		case 6:
						DBConnection.closeConnection();
        	            			break;
            			}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
        	} 
		while (choice1 != 6);
    	}
}

class Menu
{
	private static int maxChoice;
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static int readChoice(int max)
	{
		maxChoice =  max;
		while(true)
		{
			System.out.print("Enter Choice :");
			try
			{
				int choice = Integer.parseInt(reader.readLine());
				if(choice<1 || choice>maxChoice)
					throw new InvalidChoiceException();
				return choice;
			}
			catch(InputMismatchException e)
			{
				System.out.println("\nPlease enter number only\n");
			}
			catch(InvalidChoiceException e)
			{
				e.displayMessage(maxChoice);
			}
			catch (IOException e)
        		{
            			System.out.println("Error opening file: " + e.getMessage());
       			}
		}
	}
}
class InvalidChoiceException extends RuntimeException
{
	public InvalidChoiceException()
	{
	}
	public InvalidChoiceException(String msg)
	{
		super(msg);
	}
	public void displayMessage(int maxChoice)
	{
		System.out.println("\nEnter choice between 1 and "+maxChoice+"\n");
	}
}

class Name
{
	public static String readName()
	{
		while(true)
		{
			try
			{
				System.out.print("Enter full name: ");
				String name = new Scanner(System.in).next();
				Pattern p = Pattern.compile("[A-Z][a-z]+");
				Matcher m = p.matcher(name);
				if(!m.matches()) 
					throw new InvalidNameException();
				return name;
			}
			catch(InvalidNameException e)
			{
				e.displayMessage();
			}
		}
	}
}
class InvalidNameException extends RuntimeException
{
	public InvalidNameException()
	{
		super();
	}
	public InvalidNameException(String msg)
	{
		super(msg);
	}
	public void displayMessage()
	{
		System.out.println("\nName is not in valid format\n");
	}
}

class Age
{
	private static int minAge, maxAge;
	public static int readAge(int min, int max)
	{
		minAge = min;
		maxAge = max;
		while(true)
		{
			try
			{
				System.out.print("Enter age: ");
				int age=new Scanner(System.in).nextInt();
				if(age<minAge || age>maxAge)
				{
					throw new InvalidAgeException();
				}
				return age;
			}
			catch(InputMismatchException e)
			{
				System.out.println("\nPlease enter number only\n");
			}
			catch(InvalidAgeException e)
			{
				e.displayMessage(min, max);
			}
		}
	}
}
class InvalidAgeException extends RuntimeException
{
	public InvalidAgeException()
	{
		super();
	}
	public InvalidAgeException(String msg)
	{
		super(msg);
	}
	public void displayMessage(int minAge, int maxAge)
	{
		System.out.println("\nAge not between "+minAge+" and "+ maxAge);
	}
}

class Salary
{
	public static int readSalary()
	{
		while(true)
		{
			try
			{
				System.out.print("Enter salary: ");
				int salary=new Scanner(System.in).nextInt();
				if(salary<0)
				{
					throw new InvalidSalaryException();
				}
				return salary;
			}
			catch(InputMismatchException e)
			{
				System.out.println("\nPlease enter number only\n");
			}
			catch(InvalidSalaryException e)
			{
				e.displayMessage();
			}
		}
	}
}
class InvalidSalaryException extends RuntimeException
{
	public InvalidSalaryException()
	{
		super();
	}
	public InvalidSalaryException(String msg)
	{
		super(msg);
	}
	public void displayMessage()
	{
		System.out.println("\nSalary can not be less than 0");
	}
}


class EmpId
{
	public static int readId()
	{
    		while(true)
    		{
        		try
        		{
            			Connection con = DBConnection.getConnection();
				System.out.print("Enter employee ID: ");
            			int empID = new Scanner(System.in).nextInt();
				String query = "SELECT * FROM employee3 WHERE empId = ?";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt(1, empID);
				ResultSet rs = pstmt.executeQuery();
            			if(rs.next())  
            			{
                			throw new InvalidIDException(); 
            			}
				rs.close();
            				pstmt.close();
            			return empID;  
       	 		}
			catch (SQLException e) 
			{
            			System.out.println(e.getMessage());
        		}
        		catch(InvalidIDException e)
        		{
            			e.displayMessage();  
        		}
   		}
	}
}
class InvalidIDException extends RuntimeException
{
	public InvalidIDException()
	{
		super();
	}
	public InvalidIDException(String msg)
	{
		super(msg);
	}
	public void displayMessage()
	{
		System.out.println("\nEmployee with this ID already exists!\n");
	} 
}
class Designation
{
	public static String readDesignation()
	{
		while(true)
		{
			try
			{
				System.out.print("Enter designation: ");
				String desig = new Scanner(System.in).next();
				return desig;
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		}
	}
}
