package emp.assignment;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.lang.*;

class Employee implements Serializable
{
	private String name;
	int empId;
	private int age;
	private double salary;
	String designation;
	static int count=0;
	public Employee(int empId, String name, int age, double salary, String designation)
    	{
        	this.empId = empId;
        	this.name = name;
        	this.age = age;
        	this.salary = salary;
        	this.designation = designation;
		count++;
    	}
	Employee(double salary,String designation, List<Employee> employeeList) 
	{
		this.name=Name.readName();
		this.age=Age.readAge(21, 60);
		this.empId=EmpId.readId(employeeList);
		this.salary=salary;
		this.designation=designation;
		count++;	
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		if(name.isEmpty())
			System.out.println("Invalid name");
		else
			this.name=name;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		if(age<21 || age>60)
			System.out.println("Invalid age");
		else
			this.age=age;
	}

	public double getSalary()
	{
		return salary;
	}
	public void setSalary(double salaryToRaise)
	{
		this.salary += salaryToRaise;
	}
	public void raiseSalary(List<Employee> employeeList, int empId){}

	final public void display()
	{
		System.out.println("Name: "+ name);
		System.out.println("Emp ID: "+ empId);
		System.out.println("Age: "+ age);
		System.out.println("Salary: "+ salary);
		System.out.println("Designation: "+ designation+"\n");
	}
	public static int removeEmp(List<Employee> employeeList)
	{
		System.out.print("Enter employee ID to remove : ");
		int empID = new Scanner(System.in).nextInt();
		Iterator<Employee> iterator = employeeList.iterator();
        	while (iterator.hasNext()) 
		{
            		Employee emp = iterator.next();
            		if (emp.empId == empID) 
			{
                		iterator.remove();
                		count--;
                		System.out.println("Employee with ID " + empID + " has been removed.");
				return count;
            		}
        	}
        	System.out.println("Employee with ID " + empID + " not found.");
		return count;
	}		
}

final class Clerk extends Employee
{
	Clerk(List<Employee> employeeList) 
	{
		super(20000, "Clerk", employeeList);
	}
	public void raiseSalary(List<Employee> employeeList, int empId)
	{
		for (Employee emp : employeeList) 
		{
        		if (emp.empId == empId) 
			{
                        	emp.setSalary(2000); 
            			return;  
        		}
    		}
	}
	public static Employee getClerk(List<Employee> employeeList)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Clerk(employeeList);
	}
}

final class  Programmer extends Employee
{
	Programmer(List<Employee> employeeList) 
	{
		super(30000, "Programmer", employeeList);
	}
	public void raiseSalary(List<Employee> employeeList, int empId)
	{
		for (Employee emp : employeeList) 
		{
        		if (emp.empId == empId) 
			{
                        	emp.setSalary(5000); 
            			return;  
        		}
    		}
	}
	public static Employee getProgrammer(List<Employee> employeeList)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Programmer(employeeList);
	}
}

final class Manager extends Employee
{
	Manager(List<Employee> employeeList)
	{
		super(100000, "Manager", employeeList);
	}
	public void raiseSalary(List<Employee> employeeList, int empId)
	{
		for (Employee emp : employeeList) 
		{
        		if (emp.empId == empId) 
			{
                        	emp.setSalary(15000); 
            			return;  
        		}
    		}
	}
	public static Employee getManager(List<Employee> employeeList)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Manager(employeeList);
	}
}

final class CEO extends Employee
{
	private static CEO c1 = null;
	private CEO(List<Employee> employeeList)	
	{
		super(200000, "CEO", employeeList);
		System.out.println("CEO object created....");
	}
	public static CEO getCEO(List<Employee> employeeList)
	{
		if(c1==null)
			c1 = new CEO(employeeList);
		else
			throw new InstanceAlreadyExistsException("\nCEO can be created only once\n");
		return c1;
	}
	public void raiseSalary(List<Employee> employeeList, int empId)
	{
		for (Employee emp : employeeList) 
		{
        		if (emp.empId == empId) 
			{
                        	emp.setSalary(25000); 
            			return;  
        		}
    		}
	}
}

abstract class EmployeeFactory
{
	public static void getEmployee(String designation, List<Employee> employeeList)
	{
		Employee e = null;
		switch(designation)
		{
			case "Clerk":
				e =  Clerk.getClerk(employeeList);
				break;
			case "Programmer":
				e = Programmer.getProgrammer(employeeList);	
				break;
			case "Manager":
				e = Manager.getManager(employeeList);
				break;
			case "CEO":
				e = CEO.getCEO(employeeList);
				break;
		}
		if(e!=null)
			employeeList.add(e);
		return;
	}
	public static void displayEmployee(String basedOn, List<Employee> employeeList)
	{
		System.out.println("-------------------");
		System.out.println("Employee Details:");
		System.out.println("-------------------");
		
		switch(basedOn)
		{
			case "ID":
				Collections.sort(employeeList, new IDSorter());
				break;
			case "name":
				Collections.sort(employeeList, new NameSorter());	
				break;
			case "age":
				Collections.sort(employeeList, new AgeSorter());
				break;
			case "salary":
				Collections.sort(employeeList, new AgeSorter());
				break;
			case "designation":
				Collections.sort(employeeList, new DesignationSorter());
				break;
			case "default":
				break;
		}	
		Iterator<Employee> iterator = employeeList.iterator();
        	while (iterator.hasNext()) 
		{
            		Employee emp = iterator.next();
            		emp.display();
       		}
	}
	public static void searchEmployee(String searchBy, List<Employee> employeeList)
	{
		Scanner sc = new Scanner(System.in);
		
		switch(searchBy)
		{
			case "ID":
				System.out.print("Enter emp ID to search: ");
                    		int empIdToSearch = sc.nextInt();
                    		boolean foundById = false;
                    		for (Employee emp : employeeList) 
				{
                        		if (emp.empId == empIdToSearch) 
					{
                            			foundById = true;
                            			emp.display();
                        		}
                    		}
                    		if (!foundById) 
				{
                        		System.out.println("Employee with this ID not found!");
                    		}
                    		break;
			case "name":
				System.out.print("Enter Employee Name to search: ");
                    		String nameToSearch = sc.next();
                    		boolean nameFound = false;
                    		for (Employee emp : employeeList) 
				{
                        		if (emp.getName().equalsIgnoreCase(nameToSearch)) 
					{
                            			nameFound = true;
                            			emp.display();
                        		}
                    		}
                    		if (!nameFound) 
				{
                        		System.out.println("No employee found with name: " + nameToSearch);
                    		}
                    		break;
			case "designation":
				System.out.print("Enter Employee Designation to search: ");
                    		String designationToSearch = sc.next();
                    		boolean designationFound = false;
                    		for (Employee emp : employeeList) 
				{
                        		if (emp.designation.equalsIgnoreCase(designationToSearch)) 
					{
                            			designationFound = true;
                            			emp.display();
                        		}
                    		}
                    		if (!designationFound) 
				{
                        		System.out.println("No employee found with designation: " + designationToSearch);
                    		}
                    		break;
		}
        }	
}

class NameSorter implements Comparator<Employee> 
{
    	public int compare(Employee e1, Employee e2) 
	{
        	return e1.getName().compareTo(e2.getName());
    	}
}

class IDSorter implements Comparator<Employee> 
{
    	public int compare(Employee e1, Employee e2) 
	{
       		return Integer.compare(e1.empId, e2.empId);
    	}
}

class AgeSorter implements Comparator<Employee> 
{
	public int compare(Employee e1, Employee e2)
	{
        	return Integer.compare(e1.getAge(), e2.getAge());
    	}
}

class SalarySorter implements Comparator<Employee> 
{
    	public int compare(Employee e1, Employee e2) 
	{
        	return Double.compare(e1.getSalary(), e2.getSalary());
    	}
}

class DesignationSorter implements Comparator<Employee> 
{
    	public int compare(Employee e1, Employee e2) 
	{
        	return e1.designation.compareTo(e2.designation);
   	}
}


class EmployeeManagementApp8
{
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final String FILE_NAME = "employee.ser";
	public static void main(String args[])
	{
		int choice1=0, choice2=0, choice3=0, choice4=0;
		List<Employee> employeeList = new ArrayList<>();
		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile("employee.csv", "rw");
                        if (raf.length() != 0) 
			{
                		raf.seek(0);
                		raf.readLine(); 
                		String line;
                		while ((line = raf.readLine()) != null) 
				{
                    			String[] parts = line.split(",");
                    			int empId = Integer.parseInt(parts[0]);
                    			String name = parts[1];
                    			int age = Integer.parseInt(parts[2]);
                    			double salary = Double.parseDouble(parts[3]);
                    			String designation = parts[4];
                    			employeeList.add(new Employee(empId, name, age, salary, designation));
                		}
            		}
			do
			{
				System.out.println("-------------------");
				System.out.println("1. Create"+"\n"+"2. Display"+"\n"+"3. Raise Salary"+"\n"+"4. Remove"+"\n"+"5. Search"+"\n"+"6. Exit");
				System.out.println("-------------------");
				choice1=Menu.readChoice(6);

				switch(choice1)
				{
					case 1:
						do
						{
							System.out.println("-------------------");
							System.out.println("1. CEO\n"+"2. Clerk"+"\n"+"3. Programmer"+"\n"+"4. Manager"+"\n"+"5. Exit");
							System.out.println("-------------------");
							choice2=Menu.readChoice(5);

							switch(choice2)
							{

								case 1:
									try
									{
										EmployeeFactory.getEmployee("CEO", employeeList);
									}
									catch(Exception e)
									{
										System.out.println(e.getMessage());
									}
									break;
								case 2:
									EmployeeFactory.getEmployee("Clerk", employeeList);
									break;
								case 3:
									EmployeeFactory.getEmployee("Programmer", employeeList);
									break;
								case 4:
									EmployeeFactory.getEmployee("Manager", employeeList);
									break;
								case 5:
									break;	
							}

						}
						while(choice2!=5);
						break;
					case 2:
						do
						{
							System.out.println("-------------------");
							System.out.println("1. Based on ID\n"+"2. Based on Name"+"\n"+"3. Based on Age"+"\n"+"4. Based on Salary"+"\n"+"5. Based on Designation"+"\n"+"6. Default order"+"\n"+"7. Exit");
							System.out.println("-------------------");
							choice3=Menu.readChoice(7);

							switch(choice3)
							{
								case 1:
									EmployeeFactory.displayEmployee("ID", employeeList);
									break;
								case 2:
									EmployeeFactory.displayEmployee("name", employeeList);
									break;
								case 3:	
									EmployeeFactory.displayEmployee("age", employeeList);
									break;
								case 4:
									EmployeeFactory.displayEmployee("salary", employeeList);
									break;
								case 5:
									EmployeeFactory.displayEmployee("designation", employeeList);
									break;
								case 6:
									EmployeeFactory.displayEmployee("default", employeeList);
									break;
							}

						}
						while(choice3!=7);
						break;
					case 3:
						System.out.println("Salary raised for employees!");
						Iterator<Employee> iterator2 = employeeList.iterator();
                   				while (iterator2.hasNext()) 
						{
                        				Employee employee = iterator2.next();
                        				employee.raiseSalary(employeeList, employee.empId); 
                    				}
						break;
					case 4:
						Employee.count=Employee.removeEmp(employeeList);
						break;
					case 5:
						do
						{
							System.out.println("-------------------");
							System.out.println("1. Search by ID\n"+"2. Based on Name"+"\n"+"3. Based on Designation"+"\n"+"4. Exit");
							System.out.println("-------------------");
							choice4=Menu.readChoice(4);
							switch(choice4)
							{
								case 1:
									EmployeeFactory.searchEmployee("ID", employeeList);
									break;
								case 2:
									EmployeeFactory.searchEmployee("name", employeeList);
									break;
								case 3:
									EmployeeFactory.searchEmployee("designation", employeeList);
									break;	
							}
						}
						while(choice4!=4);
						break;
					case 6:	
						break;
				}			
			}
			while(choice1!=6);
			raf.setLength(0);
            		raf.seek(0);
            		raf.writeBytes("empId,name,age,salary,designation\n");
            		for (Employee emp : employeeList) 
			{
                		raf.writeBytes(emp.empId + "," + emp.getName() + "," + emp.getAge() + "," + emp.getSalary() + "," + emp.designation + "\n");
            		}
            		raf.close();
		}
		catch (IOException e)
        	{
            		System.out.println("Error opening file: " + e.getMessage());
       		}
			
		System.out.println("Total no. of employees created/added :"+Employee.count);
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
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static String readName()
	{
		while(true)
		{
			try
			{
				System.out.print("Enter full name: ");
				String name = reader.readLine();
				Pattern p = Pattern.compile("[A-Z][a-z]+\\s[A-Z][a-z]*$");
				Matcher m = p.matcher(name);
				if(!m.matches()) 
					throw new InvalidNameException();
				return name;
			}
			catch(InvalidNameException e)
			{
				e.displayMessage();
			}
			catch (IOException e)
        		{
            			System.out.println("Error opening file: " + e.getMessage());
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
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static int readAge(int min, int max)
	{
		minAge = min;
		maxAge = max;
		while(true)
		{
			try
			{
				System.out.print("Enter age: ");
				int age = Integer.parseInt(reader.readLine());
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
			catch (IOException e)
        		{
            			System.out.println("Error opening file: " + e.getMessage());
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

class EmpId
{
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static int readId(List<Employee> employeeList)
	{
    		while(true)
    		{
        		try
        		{
            			System.out.print("Enter employee ID: ");
            			int empID = Integer.parseInt(reader.readLine());
				for (Employee emp : employeeList) 
				{
                			if (emp.empId == empID) 
					{
                    				throw new InvalidIDException();                 					}
            			}
            			return empID;  
       	 		}
        		catch(InvalidIDException e)
        		{
            			e.displayMessage();  
        		}
			catch (IOException e)
        		{
            			System.out.println("Error opening raf: " + e.getMessage());
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