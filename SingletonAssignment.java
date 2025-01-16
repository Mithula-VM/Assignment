class A
{
	static private A a1 = null;
	protected A()
	{
		if(this.getClass() == A.class && a1!=null)
			throw new RuntimeException("Cannot create more than one object for class A");
		else if (this.getClass() == A.class && a1==null)
		{
			a1 =  this;
			System.out.println("A class object created.....");
		}
				
	}
	public static A getObject()
	{
			
		if(a1==null)	
			a1 = new A();
		return a1;
	}
}
class B extends A
{
	static private final B b1 = new B();
	private B()
	{
		if(b1!=null)
			throw new RuntimeException("Cannot create more than one object for class B");
		else
			System.out.println("B class object created.....");	

	}
	public static B getObject()
	{
		
		return b1;
	}
}
 
public class SingletonAssignment
{
	public static void main(String args[])
	{
		try
		{
			//A a4  = new A();

			A a3 = new A();
			//System.out.println(a3);
			A a1 = A.getObject();
			B b1 = B.getObject();
		
			System.out.println(a1);
			System.out.println(b1);
			System.out.println(a3);


			A a2 = A.getObject();
			B b2 = B.getObject();

			System.out.println(a2);
			System.out.println(b2);

			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}