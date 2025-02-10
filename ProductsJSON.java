package PostGreConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

class DBConnection
{
	private static Connection con = null;
	static 
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/salesdb", "postgres", "tiger");
        	} 
		catch (SQLException | ClassNotFoundException e) 
		{
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

class Products
{
	private int id;
	private String name;
	private int price;
	private int quantity;
	private String category;
	private boolean availability;
	
	public Products()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter product ID : ");
			this.id = Integer.parseInt(reader.readLine());
			System.out.println("Enter product name : ");
			this.name = reader.readLine();
			System.out.println("Enter product price : ");
			this.price = Integer.parseInt(reader.readLine());
			System.out.println("Enter product quantity : ");
			this.quantity = Integer.parseInt(reader.readLine());
			System.out.println("Enter product category : ");
			this.category = reader.readLine();
			System.out.println("Enter product availability : ");
			String in_stock = reader.readLine();
			this.availability = Boolean.parseBoolean(in_stock);
		}
		catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

interface ProductsDAO
{
	public void create(Products p);
	public void update(int id, int price, int qty, String category, boolean availability);
	public void display();
	public void delete(int id);
}

class ProductsDAOimpl
{
	void create(Products p)
	{
		Connection con = DBConnection.getConnection();
		JSONObject details = new JSONObject();
		details.put("price", p.getPrice()); 
		details.put("quantity", p.getQuantity()); 
		details.put("categories", new String[]{p.getCategory()}); 
		details.put("in_stock", p.isAvailability()); 
		String sql = "INSERT INTO products (product_name, details) VALUES (?, ?::jsonb)";
		            
		try (PreparedStatement pstmt = con.prepareStatement(sql)) 
		{
		        pstmt.setString(1, p.getName()); 
		        pstmt.setString(2, details.toString()); 

		        int rowsAffected = pstmt.executeUpdate();
		        System.out.println(rowsAffected + " row(s) inserted.");
		} 
		catch (SQLException e) 
		{
		        System.out.println("Error inserting data: " + e.getMessage());
		}
	}
	void update(int id, int price, int qty, String category, boolean availability)
	{
		Connection con = DBConnection.getConnection();
		JSONObject updatedDetails = new JSONObject();
        updatedDetails.put("price", price);
        updatedDetails.put("quantity", qty);
        updatedDetails.put("categories", category);
        updatedDetails.put("in_stock", availability);

        String updateSql = "UPDATE products SET details = ?::jsonb WHERE product_id = ?";
        try (PreparedStatement updatePstmt = con.prepareStatement(updateSql)) 
        {
            updatePstmt.setString(1, updatedDetails.toString());
            updatePstmt.setInt(2, id);

            int rowsAffected = updatePstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error updating data: " + e.getMessage());
        }
	}
	void display()
	{
		Connection con = DBConnection.getConnection();
		String query = "SELECT * FROM products";
        try (PreparedStatement selectPstmt = con.prepareStatement(query);
        	ResultSet rs = selectPstmt.executeQuery()) 
        {
        
        	System.out.println("Product List: ");
        	while (rs.next()) 
        	{
        		int productId = rs.getInt("product_id");
        		String productName = rs.getString("product_name");
        		String details2 = rs.getString("details"); 
            
        		System.out.println("Product ID: " + productId);
        		System.out.println("Product Name: " + productName);
        		System.out.println("Details: " + details2);
        		System.out.println("---------------------------------");
        	}
        } 
        catch (SQLException e) 
        {
        System.out.println("Error retrieving data: " + e.getMessage());
        }
	}
	void delete(int id)
	{
		Connection con = DBConnection.getConnection();
		String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement deletePstmt = con.prepareStatement(query)) 
        {
            deletePstmt.setInt(1, id);

            int rowsAffected = deletePstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error deleting data: " + e.getMessage());
        }
	}
}

public class ProductsJSON {

	public static void main(String[] args) 
	{
		int choice1 = 0;
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	ProductsDAOimpl productsdao = new ProductsDAOimpl();
    	Products p = null;
    	do 
    	{
        		try
        		{
        			System.out.println("-------------------");
        			System.out.println("1. Create" + "\n" + "2. Update" + "\n" + "3. Display" + "\n" + "4. Delete" + "\n"  + "5. Exit");
        			System.out.println("-------------------");
        			System.out.print("Enter choice : ");
        			choice1 = Integer.parseInt(reader.readLine());
        
        			switch (choice1) 
        			{
            			case 1:		
            				p = new Products();
            				productsdao.create(p);
            				break;
            			case 2:
            				System.out.print("Enter product ID to update: ");
            				int id = Integer.parseInt(reader.readLine());
            				System.out.print("Enter new product price : ");
            				int price = Integer.parseInt(reader.readLine());
            				System.out.print("Enter new product quantity : ");
            				int quantity = Integer.parseInt(reader.readLine());
            				System.out.print("Enter new product category : ");
            				String category = reader.readLine();
            				System.out.print("Enter new product availability : ");
            				String in_stock = reader.readLine();
            				boolean availability = Boolean.parseBoolean(in_stock);
            				productsdao.update(id, price, quantity, category, availability);
            			case 3:
            				productsdao.display();
            				break;
            			case 4:
            				System.out.print("Enter product ID to delete : ");
            	            int id2 = Integer.parseInt(reader.readLine());
            	            productsdao.delete(id2);
            	            break;
            			case 5:
            				DBConnection.closeConnection();
            				break;
        			}
        		}
    			catch(Exception ex)
    			{
    				System.out.println(ex.getMessage());
    			}
            } 
    		while (choice1 != 5);
	}
}