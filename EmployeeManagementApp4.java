package emp.assignment;

import java.util.*;
import java.io.*;
import java.lang.*;

abstract class Employee
{
	private String name;
	private int empId;
	private int age;
	private double salary;
	private String designation;
	static int count=0;
	Employee(Employee employees[], double salary,String designation) 
	{
		getDetails(employees);
		this.salary=salary;
		this.designation=designation;	
	}
	public void getDetails(Employee employees[]) 
	{
		Scanner s= new Scanner(System.in);
		System.out.print("Enter name: ");
		name=s.next();
		while(true)
		{
			try
			{
				System.out.print("Enter employee ID: ");
				empId=s.nextInt();
				for(int i=0;i<count;i++)
				{	
					if(employees[i].empId==empId)
					{
						throw new IDException("\nEmployee with this ID already exists!\n");
					}
				}
				break;
			}
			catch(IDException e)
			{
				System.out.println(e.getMessage());
			}
		}
		while(true)
		{
			try
			{
				System.out.print("Enter age: ");
				age=s.nextInt();
				if(age<21 || age>60)
				{
					throw new AgeException("\nAge not between 21 and 60!\n");
				}
				break;
			}
			catch(InputMismatchException e)
			{
				System.out.println("\nInvalid input!\n");
				s.nextLine();
			}
			catch(AgeException e)
			{
				System.out.println(e.getMessage());
			}
		}
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

class EmployeeManagementApp4
{
	public static void main(String args[])
	{
		Scanner s= new Scanner(System.in);
		
		int choice1=0, choice2=0;
		Employee employees[]= new Employee[100];
		do
		{
			try
			{
				System.out.println("-------------------");
				System.out.println("1. Create"+"\n"+"2. Display"+"\n"+"3. Raise Salary"+"\n"+"4. Remove"+"\n"+"5. Exit");
				System.out.println("-------------------");
				System.out.print("Enter choice: ");
				choice1=s.nextInt();

				switch(choice1)
				{
					case 1:
						do
						{
							try
							{
								System.out.println("-------------------");
								System.out.println("1. Clerk"+"\n"+"2. Programmer"+"\n"+"3. Manager"+"\n"+"4. Exit");
								System.out.println("-------------------");
								System.out.print("Enter choice: ");
								choice2=s.nextInt();

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
									default:
										throw new ChoiceException("\nInvalid choice!\n");
								}
							}
							catch(InputMismatchException e)
							{
								System.out.println("\nInvalid input!\n");
								s.nextLine();
							}
							catch(ChoiceException e)
							{
								System.out.println(e.getMessage());
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
					default:
						throw new ChoiceException("\nInvalid choice!\n");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("\nInvalid input!\n");
				s.nextLine();
			}
			catch(ChoiceException e)
			{
				System.out.println(e.getMessage());
			}	
		}
		while(choice1!=5);
		System.out.println("Total no. of employees created/added :"+Employee.count);
	}
}

class AgeException extends Exception
{
	public AgeException()
	{
		super();
	}
	public AgeException(String msg)
	{
		super(msg);
	}
}
class IDException extends Exception
{
	public IDException()
	{
		super();
	}
	public IDException(String msg)
	{
		super(msg);
	}
}
class ChoiceException extends Exception
{
	public ChoiceException()
	{
		super();
	}
	public ChoiceException(String msg)
	{
		super(msg);
	}
}
