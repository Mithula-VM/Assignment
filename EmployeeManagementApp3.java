package emp.assignment;

import java.util.Scanner;

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
		System.out.print("Enter employee ID: ");
		empId=s.nextInt();
		for(int i=0;i<count;i++)
		{	
			if(employees[i].empId==empId)
			{
				System.out.println("Employee with this ID already exists!\n");
				return;
			}
		}
		System.out.print("Enter age: ");
		age=s.nextInt();
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
		if(age<0 || age>60)
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

class EmployeeManagementApp3
{
	public static void main(String args[])
	{
		Scanner s= new Scanner(System.in);
		
		int choice1, choice2;
		Employee employees[]= new Employee[100];
		do
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
								System.out.println("Invalid option");
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
					System.out.println("Invalid option");
			}
		}
		while(choice1!=5);
		System.out.println("Total no. of employees created/added :"+Employee.count);
	}
}