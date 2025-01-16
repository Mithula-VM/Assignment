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
	private String designation;
	static int count=0;
	Employee(Employee employees[], double salary,String designation) 
	{
		name=Name.readName();
		age=Age.readAge(21, 60);
		empId=EmpId.readId(employees, count);
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

	public static int removeEmp(Employee employees[])
	{
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter emp id :");
		int empIdToRemove=sc.nextInt();
		for(int i=0;i<count;i++)
		{
			if(employees[i].empId==empIdToRemove)
				employees[i].display();
			System.out.print("Do you really want to remove this employee record (Y/N) :");
			char c=sc.next().charAt(0);
			if(c=='Y' || c=='y')
			{
				for(int j=i;j<count-1;j++)
				{	
					employees[j]=employees[j+1];
				}
				System.out.println("Employee removed!");
				return count-1;
			}
			else 
				return count;
		}
		return count;
	}
		
}

final class Clerk extends Employee
{
	Clerk(Employee employees[]) 
	{
		super(employees, 20000, "Clerk");
	}
	public void raiseSalary()
	{
		setSalary(super.getSalary()+2000);
	}
}

final class  Programmer extends Employee
{
	Programmer(Employee employees[]) 
	{
		super(employees, 30000, "Programmer");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+5000);
	}
}

final class Manager extends Employee
{
	Manager(Employee employees[])
	{
		super(employees, 100000, "Manager");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+15000);
	}
}

class EmployeeManagementApp5
{
	public static void main(String args[])
	{
		int choice1=0, choice2=0;
		Employee employees[]= new Employee[100];
		do
		{
			System.out.println("-------------------");
			System.out.println("1. Create"+"\n"+"2. Display"+"\n"+"3. Raise Salary"+"\n"+"4. Remove"+"\n"+"5. Exit");
			System.out.println("-------------------");
			choice1=Menu.readChoice(5);

			switch(choice1)
			{
				case 1:
					do
					{
						System.out.println("-------------------");
						System.out.println("1. Clerk"+"\n"+"2. Programmer"+"\n"+"3. Manager"+"\n"+"4. Exit");
						System.out.println("-------------------");
						choice2=Menu.readChoice(4);

						switch(choice2)
						{

							case 1:
								employees[Employee.count]=new Clerk(employees);
								break;
							case 2:
								employees[Employee.count]=new Programmer(employees);
								break;
							case 3:
								employees[Employee.count]=new Manager(employees);
								break;
							case 4:
								break;
									
						}
					}
					while(choice2!=4);
					break;
				case 2:
					System.out.println("-------------------");
					System.out.println("Employee Details:");
					System.out.println("-------------------");
					for(int i=0;i<Employee.count;i++)
						employees[i].display();
					break;

				case 3:
					System.out.println("Salary raised for employees!");
					for(int i=0;i<Employee.count;i++)
						employees[i].raiseSalary();
					break;
				case 4:
					Employee.count=Employee.removeEmp(employees);
					break;
				case 5:	
					break;
			}
				
		}
		while(choice1!=5);
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
	public static int readId(Employee employees[], int count)
	{
		while(true)
		{
			try
			{
				System.out.print("Enter employee ID: ");
				int empId=new Scanner(System.in).nextInt();
				for(int i=0;i<count;i++)
				{	
					if(employees[i].empId==empId)
					{
						throw new InvalidIDException();
					}
				}
				return empId;
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