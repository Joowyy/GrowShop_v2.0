package ClientesProductos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class Producto {

//	ATRIBUTOS
	private int id;
	private String nombre;
	private double precio;
	private boolean stock;

//	CONSTRUCTORES
	public Producto() {
		
	}
	public Producto(String nombre, double precio, boolean stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	public Producto(int id, String nombre, double precio, boolean stock) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	
//	GETTERS SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isStock() {
		return stock;
	}
	public void setStock(boolean stock) {
		this.stock = stock;
	}
	
//	METODOS
//	Actualiza la BDD en el ArrayList<Producto>
	public static ArrayList<Producto> cargarProductos(Statement s) {
	    ArrayList<Producto> productos = new ArrayList<>();
	    
	    try {
	    	
	        ResultSet rs = s.executeQuery("SELECT * FROM productos");
	        
	        while (rs.next()) {
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            boolean stock = rs.getBoolean("stock");

	            productos.add(new Producto(nombre, precio, stock));
	            
	        }
	        
	    } catch (SQLException e) {
	    	
	        e.printStackTrace();
	        
	    }

	    return productos;
	}

	public void mostrarProducto() {
		
		System.out.println("\n________________________\n    --PRODUCTOS--");
		System.out.println("------------------------");
		System.out.println("ID -> " + id);
		System.out.println("Nombre -> " + nombre);
		System.out.println("Precio -> " + precio);
		System.out.println("Stock -> " + stock);
		System.out.println("------------------------\n");
		
	}
	
}
