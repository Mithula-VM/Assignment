import java.util.*;
import java.util.stream.*;

class Student 
{
    	private int rollNo;
    	private String name;
    	private int age;
    	private String standard;
    	private String school;
    	private String gender;
    	private double percentage;
    	private Fees fees;

    	public Student(int rn, String name, int age, String std, String school, String gender, double percentage, Fees fees) 
	{
        	this.rollNo = rn;
        	this.name = name;
        	this.age = age;
        	this.standard = std;
        	this.school = school;
        	this.gender = gender;
        	this.percentage = percentage;
        	this.fees = fees;
    	}

    	public int getRollNo() 
	{
        	return rollNo;
    	}

    	public String getName() 
	{
        	return name;
    	}

    	public int getAge() 
	{
        	return age;
    	}

    	public String getStandard() 
	{
        	return standard;
    	}

    	public String getSchool() 
	{
        	return school;
    	}

    	public String getGender() 	
	{
        	return gender;
    	}

    	public double getPercentage() 
	{
        	return percentage;
    	}

    	public Fees getFees() 
	{
        	return fees;
    	}

    	public boolean isPassed() 
	{
        	return percentage >= 40;
    	}
	public String toString() 
	{
        	return "Student{name='" + name + "', percentage=" + percentage + "}";
    	}
}

class Fees 
{
    	private double totalFees;
    	private double feesPaid;
    	private double feesPending;

    	public Fees(double totalFees, double feesPaid, double feesPending) 
	{
        	this.totalFees = totalFees;
        	this.feesPaid = feesPaid;
        	this.feesPending = feesPending;
    	}

    	public double getTotalFees() 
	{
        	return totalFees;
    	}

    	public double getFeesPaid() 
	{
        	return feesPaid;
    	}

    	public double getFeesPending() 
	{
        	return feesPending;
   	}
}

public class StudentManagement 
{
    	public static void main(String[] args) 
	{
        	List<Student> students = Arrays.asList
		(
           	 	new Student(1, "Raju", 16, "10th", "KV", "Male", 85.0, new Fees(10000, 8000, 2000)),
            		new Student(2, "Sunita", 15, "9th", "KV", "Female", 50.0, new Fees(12000, 12000, 0)),
            		new Student(3, "Sanju", 16, "10th", "DAV Public", "Female", 30.0, new Fees(11000, 5000, 6000)),
            		new Student(4, "Manju", 16, "9th", "KV", "Male", 65.0, new Fees(9500, 9500, 0)),
            		new Student(5, "Arun", 16, "10th", "DAV Public", "Male", 90.0, new Fees(10000, 10000, 0))
        	);

        	Map<String, Long> studentsInEachStandard = students.stream().collect(Collectors.groupingBy(Student::getStandard, Collectors.counting()));
        	System.out.println("Students in each standard: " + studentsInEachStandard);
		System.out.println();

	        Map<String, Long> studentsByGender = students.stream().collect(Collectors.groupingBy(Student::getGender, Collectors.counting()));
        	System.out.println("Students by gender: " + studentsByGender);
		System.out.println();

	        long passedCount = students.stream().filter(Student::isPassed).count();
        	long failedCount = students.size() - passedCount;
        	System.out.println("Passed students in university: " + passedCount);
        	System.out.println("Failed students in university: " + failedCount);
		System.out.println();

		Map<String, Map<Boolean, Long>> schoolWisePassFail = students.stream().collect(Collectors.groupingBy(Student::getSchool,Collectors.partitioningBy(Student::isPassed, Collectors.counting())));
		schoolWisePassFail.forEach((school, passFailMap) -> {
    			long passed = passFailMap.getOrDefault(true, 0L); 
    			long failed = passFailMap.getOrDefault(false, 0L);
    			System.out.println(school + " - Passed students: " + passed + ", Failed students: " + failed);
		});
		System.out.println();

        	List<Student> top3Students = students.stream().sorted(Comparator.comparingDouble(Student::getPercentage).reversed()).limit(3).collect(Collectors.toList());
		System.out.println("Top 3 students: ");
		for (Student student : top3Students) 
		{
    			System.out.println(student);  
		}
		System.out.println();

       		Map<String, Optional<Student>> topScorerBySchool = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.maxBy(Comparator.comparingDouble(Student::getPercentage))));
		System.out.println("Top scorer school wise: " + topScorerBySchool);
		System.out.println();

		Map<String, Double> averageAgeByGender = students.stream().collect(Collectors.groupingBy(Student::getGender, Collectors.averagingInt(Student::getAge)));
 		System.out.println("Average age by gender: " + averageAgeByGender);
		System.out.println();

		Map<String, Double> totalFeesPaidBySchool = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.summingDouble(student -> student.getFees().getFeesPaid())));
		System.out.println("Total fees collected school wise: " + totalFeesPaidBySchool);
		System.out.println();

		Map<String, Double> totalFeesPendingBySchool = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.summingDouble(student -> student.getFees().getFeesPending())));
		System.out.println("Total fees pending school wise: " + totalFeesPendingBySchool);
		System.out.println();

	 	long totalStudents = students.size();
		System.out.println("Total number of students in university: " + totalStudents);
    }
}
