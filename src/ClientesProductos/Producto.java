package ClientesProductos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Producto {

//	ATRIBUTOS
	private int id;
	private String nombre;
	private double precio;
	private int stock;

//	CONSTRUCTORES
	public Producto() {
		
	}
	public Producto(String nombre, double precio, int stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	public Producto(int id, String nombre, double precio, int stock) {
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
//	METODOS
//	Actualiza la BDD en el ArrayList<Producto>
	public static ArrayList<Producto> cargarProductos(Statement s) {
	    ArrayList<Producto> productos = new ArrayList<>();
	    
	    try {
	    	
	        ResultSet rs = s.executeQuery("SELECT * FROM productos");
	        
	        while (rs.next()) {
	        	int id = rs.getInt("id_producto");
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            int stock = rs.getInt("stock");

	            productos.add(new Producto(id, nombre, precio, stock));
	            
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
	
	public void añadirProducto(ArrayList<Producto> C_productos, Statement s) throws SQLException {
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Nombre del producto: ");
		String nombreP = sc.nextLine();
		System.out.println("Precio del producto: ");
		double precioP = sc.nextDouble();
		System.out.println("¿Habrá stock en 2d max.? 1-si / 0-no");
		int stockP = sc.nextInt();
		
		String comandoSQL = String.format("INSERT INTO productos (nombre, precio, stock) VALUES ('%s', '%s', '%s')", nombreP, precioP, stockP);

		try {

//			Se utiliza para vincular el ID del AI (AutoIncrement) de las tabla SQL.
			// Ejecutamos la inserción especificando que queremos las claves generadas
			s.executeUpdate(comandoSQL, Statement.RETURN_GENERATED_KEYS);

			// Obtenemos el ID generado
			ResultSet generatedKeys = s.getGeneratedKeys();
			int idGenerado = 0;

			if (generatedKeys.next()) {
				idGenerado = generatedKeys.getInt(1);
			}
			
			C_productos.add(new Producto(nombreP, precioP, stockP));
			System.out.println("Producto añadido con éxito.");
			
		} catch (SQLException e) {
		    System.out.println("❌ Error al crear el producto.");
		    e.printStackTrace();
		}

		
	}
	
}
