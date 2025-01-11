import java.util.Scanner;

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
	
	public void display()
	{
		System.out.println("Employee details:");
		System.out.println("Name: "+ name);
		System.out.println("Age: "+ age);
		System.out.println("Salary: "+ salary);
		System.out.println("Designation: "+ designation+"\n");
	}

	public void raiseSalary()
	{
		System.out.print("Enter amount to raise salary of " + name +":");
		salary+=s.nextInt();
		System.out.println("Name: "+ name);
		System.out.println("Updated Salary: "+ salary+"\n");
	}
	
}

class Clerk extends Employee
{
	Clerk(String name,int age,double salary)
	{
		super(name, age, salary, "Clerk");
	}
}

class  Programmer extends Employee
{
	Programmer(String name,int age,double salary)
	{
		super(name, age, salary, "Programmer");
	}

}

class Manager extends Employee
{
	Manager(String name,int age,double salary)
	{
		super(name, age, salary, "Manager");
	}

}

class EmployeeManagementApp1
{
	public static void main(String args[])
	{
		Scanner s= new Scanner(System.in);
		
		Clerk e1=new Clerk("Alice", 25,10000);
		Programmer e2=new Programmer("Bob", 35,20000);
		Manager e3=new Manager("John", 45,40000);

		e1.display();
		e2.display();
		e3.display();
		
		e1.raiseSalary();
		e2.raiseSalary();
		e3.raiseSalary();
	}
}


		

		
		