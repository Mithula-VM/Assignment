import java.util.Scanner;
import java.util.ArrayList;

class Employee
{
	Scanner s= new Scanner(System.in);
	private String name;
	private int age;
	private double salary;
	private String designation;
	Employee(String name,int age,double salary,String designation)
	{
		this.name=name;
		this.age=age;
		this.salary=salary;
		this.designation=designation;	
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
	public void display()
	{
		System.out.println("Name: "+ name);
		System.out.println("Age: "+ age);
		System.out.println("Salary: "+ salary);
		System.out.println("Designation: "+ designation+"\n");
	}
	public void raiseSalary()
	{
		System.out.println("Salary of employees raised");
	}

	
}

class Clerk extends Employee
{
	Clerk(String name,int age,double salary)
	{
		super(name, age, salary, "Clerk");
	}
	public void raiseSalary()
	{
		setSalary(super.getSalary()+2000);
	}
}

class  Programmer extends Employee
{
	Programmer(String name,int age,double salary)
	{
		super(name, age, salary, "Programmer");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+5000);
	}

}

class Manager extends Employee
{
	Manager(String name,int age,double salary)
	{
		super(name, age, salary, "Manager");
	}
	public void raiseSalary()
	{
		setSalary(getSalary()+15000);
	}
}

class EmployeeManagementApp1
{
	public static void main(String args[])
	{
		Scanner s= new Scanner(System.in);
		
		int choice1, choice2;
		String name;
		int age;
		ArrayList<Employee> employees= new ArrayList<>();
		do
		{
			System.out.println("-------------------");
			System.out.println("1. Create"+"\n"+"2. Display"+"\n"+"3. Raise Salary"+"\n"+"4. Exit");
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
								System.out.print("Enter name: ");
								name=s.next();
								System.out.print("Enter age: ");
								age=s.nextInt();
								employees.add(new Clerk(name, age, 20000));
								break;
	
							case 2:
								System.out.print("Enter name: ");
								name=s.next();
								System.out.print("Enter age: ");
								age=s.nextInt();
								employees.add(new Programmer(name, age, 30000));
								break;
							case 3:
								System.out.print("Enter name: ");
								name=s.next();
								System.out.print("Enter age: ");
								age=s.nextInt();
								employees.add(new Manager(name, age, 100000));
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
					for(Employee e: employees)
						e.display();
					break;

				case 3:
					System.out.println("Salary raised for employees");
					for(Employee e: employees)
						e.raiseSalary();

					break;
				case 4:
					break;
				default:
					System.out.println("Invalid option");
			}
		}
		while(choice1!=4);
		System.out.println("Total no. of employees created/added :"+employees.size());
		
	}
}