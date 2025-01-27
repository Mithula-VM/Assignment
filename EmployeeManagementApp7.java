package emp.assignment;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.lang.*;

abstract class Employee
{
	private String name;
	int empId;
	private int age;
	private double salary;
	String designation;
	static int count=0;
	Employee(HashMap<Integer, Employee> employees, double salary,String designation) 
	{
		this.name=Name.readName();
		this.age=Age.readAge(21, 60);
		this.empId=EmpId.readId(employees, count);
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
	public void setSalary(double salary)
	{
		if(salary>0)
			this.salary=salary;
		else
			System.out.println("Invalid salary");
	}
	final public void display()
	{
		System.out.println("Name: "+ name);
		System.out.println("Emp ID: "+ empId);
		System.out.println("Age: "+ age);
		System.out.println("Salary: "+ salary);
		System.out.println("Designation: "+ designation+"\n");
	}

	public abstract void raiseSalary();

	public static int removeEmp(HashMap<Integer, Employee> employees)
	{
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter emp id :");
		int empIdToRemove=sc.nextInt();
		if(employees.containsKey(empIdToRemove))
		{
			Employee empToRemove = employees.get(empIdToRemove);
			empToRemove.display();
			System.out.print("Do you really want to remove this employee record (Y/N) :");
			char c=sc.next().charAt(0);
			if(c=='Y' || c=='y')
			{
				employees.remove(empIdToRemove);
				 count--; 
				System.out.println("Employee removed!");
				return count;
			}
			else 
				return count;
		}
		else
		{
			System.out.println("Employee with ID " + empIdToRemove + " not found.");
			return count;
		}
	}		
}

final class Clerk extends Employee
{
	Clerk(HashMap<Integer, Employee> employees) 
	{
		super(employees, 20000, "Clerk");
	}
	public void raiseSalary()
	{
		setSalary(super.getSalary()+2000);
	}
	public static Employee getClerk(HashMap<Integer, Employee> employees)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Clerk(employees);
	}
}

final class  Programmer extends Employee
{
	Programmer(HashMap<Integer, Employee> employees) 
	{
		super(employees, 30000, "Programmer");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+5000);
	}
	public static Employee getProgrammer(HashMap<Integer, Employee> employees)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Programmer(employees);
	}
}

final class Manager extends Employee
{
	Manager(HashMap<Integer, Employee> employees)
	{
		super(employees, 100000, "Manager");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+15000);
	}
	public static Employee getManager(HashMap<Integer, Employee> employees)
	{
		if(Employee.count==0)
		{
			System.out.println("CEO should be created first");
			return null;
		}
		return new Manager(employees);
	}
}

final class CEO extends Employee
{
	private static CEO c1 = null;
	private CEO(HashMap<Integer, Employee> employees)	
	{
		super(employees, 200000, "CEO");
		System.out.println("CEO object created....");
	}
	public static CEO getCEO(HashMap<Integer, Employee> employees)
	{
		if(c1==null)
			c1 = new CEO(employees);
		else
			throw new InstanceAlreadyExistsException("\nCEO can be created only once\n");
		return c1;
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+25000);
	}
}

abstract class EmployeeFactory
{
	public static HashMap<Integer, Employee> employees = new HashMap<Integer, Employee>();
	public static void getEmployee(String designation)
	{
		Employee e = null;
		switch(designation)
		{
			case "Clerk":
				e =  Clerk.getClerk(employees);
				break;
			case "Programmer":
				e = Programmer.getProgrammer(employees);	
				break;
			case "Manager":
				e = Manager.getManager(employees);
				break;
			case "CEO":
				e = CEO.getCEO(employees);
				break;
		}
		if(e!=null)
			employees.put(e.empId, e);
	}
	public static void displayEmployee(HashMap<Integer, Employee> employees, String basedOn)
	{
		System.out.println("-------------------");
		System.out.println("Employee Details:");
		System.out.println("-------------------");
		
		List<Employee> employeeList = new ArrayList<>(employees.values());

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
	public static void searchEmployee(String searchBy)
	{
		Scanner sc = new Scanner(System.in);
		switch(searchBy)
		{
			case "ID":
				System.out.print("Enter emp ID to search:");
                		int empIdToSearch = sc.nextInt();
				System.out.println();
                		if (EmployeeFactory.employees.containsKey(empIdToSearch)) 
				{
                			Employee emp = EmployeeFactory.employees.get(empIdToSearch);
                        		emp.display();
                		} 
				else 
				{
                        		System.out.println("Employee with this ID not found!");
                		}
				break;
			case "name":
				System.out.print("Enter Employee Name to search: ");
            			String nameToSearch = sc.next();
            			boolean nameFound = false;
            			for (Employee emp : employees.values()) 
				{
                			if (emp.getName().equalsIgnoreCase(nameToSearch)) 
					{
                    				emp.display();
                    				nameFound = true;
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
            			for (Employee emp : employees.values()) 
				{
                			if (emp.designation.equalsIgnoreCase(designationToSearch)) 
					{
                    				emp.display();
                    				designationFound = true;
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


class EmployeeManagementApp7
{
	public static void main(String args[])
	{
		int choice1=0, choice2=0, choice3=0, choice4=0;
		
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
									EmployeeFactory.getEmployee("CEO");
								}
								catch(Exception e)
								{
									System.out.println(e.getMessage());
								}
								break;
							case 2:
								EmployeeFactory.getEmployee("Clerk");
								break;
							case 3:
								EmployeeFactory.getEmployee("Programmer");
								break;
							case 4:
								EmployeeFactory.getEmployee("Manager");
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
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "ID");
								break;
							case 2:
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "name");
								break;
							case 3:
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "age");
								break;
							case 4:
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "salary");
								break;
							case 5:
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "designation");
								break;
							case 6:
								EmployeeFactory.displayEmployee(EmployeeFactory.employees, "default");
								break;
						}

					}
					while(choice3!=7);
					break;


				case 3:
					System.out.println("Salary raised for employees!");
					Iterator<Map.Entry<Integer, Employee>> iterator2 = EmployeeFactory.employees.entrySet().iterator();
                   			while (iterator2.hasNext()) 
					{
                        			Map.Entry<Integer, Employee> entry = iterator2.next();
                        			entry.getValue().raiseSalary(); 
                    			}
					break;
				case 4:
					Employee.count=Employee.removeEmp(EmployeeFactory.employees);
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
								EmployeeFactory.searchEmployee("ID");
								break;
							case 2:
								EmployeeFactory.searchEmployee("name");
								break;
							case 3:
								EmployeeFactory.searchEmployee("designation");
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
		System.out.println("Total no. of employees created/added :"+Employee.count);
	}
}
class Menu
{
	private static int maxChoice;
	public static int readChoice(int max)
	{
		maxChoice =  max;
		while(true)
		{
			System.out.print("Enter Choice :");
			try
			{
				int choice =  new Scanner(System.in).nextInt();
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

class EmpId
{
	public static int readId(HashMap<Integer, Employee> employees, int count)
	{
    		while(true)
    		{
        		try
        		{
            			System.out.print("Enter employee ID: ");
            			int empID = new Scanner(System.in).nextInt();
            			if(employees.containsKey(empID))  
            			{
                			throw new InvalidIDException(); 
            			}
            			return empID;  
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