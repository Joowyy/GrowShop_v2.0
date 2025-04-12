package ClientesProductos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Producto {

//	ATRIBUTOS
	private int id_producto;
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
	public Producto(int id_producto, String nombre, double precio, int stock) {
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	
//	GETTERS SETTERS
	public int getId_producto() {
		return id_producto;
	}
	public void setId(int id) {
		this.id_producto = id_producto;
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
	        	int id_producto = rs.getInt("id_producto");
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            int stock = rs.getInt("stock");

	            productos.add(new Producto(id_producto, nombre, precio, stock));
	            
	        }
	        
	    } catch (SQLException e) {
	    	
	        e.printStackTrace();
	        
	    }

	    return productos;
	}

	public void mostrarProducto() {
		
		System.out.println("\n________________________\n    --PRODUCTOS--");
		System.out.println("------------------------");
		System.out.println("ID -> " + id_producto);
		System.out.println("Nombre -> " + nombre);
		System.out.println("Precio -> " + precio);
		System.out.println("Stock -> " + stock);
		System.out.println("------------------------\n");
		
	}
	
	public void añadirProducto(ArrayList<Producto> C_productos, Statement s) {
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Nombre del producto: ");
		String nombreP = sc.nextLine();
		System.out.println("Precio del producto: ");
		double precioP = sc.nextDouble();
		int stockP;
        
		do {
        	
            System.out.println("¿Habrá stock en 2d max.? 1-si / 0-no");
            stockP = sc.nextInt();
            
            if (stockP != 0 && stockP != 1) {
            	
                System.out.println("❌ Error: Solo se permite 0 (no) o 1 (sí).");
                
            }
            
        } while (stockP != 0 && stockP != 1);
		
		String comandoSQL = String.format("INSERT INTO productos (nombre, precio, stock) VALUES ('%s', '%s', '%s')", nombreP, precioP, stockP);

// 		Verificar consulta
		System.out.println("DEBUG: " + comandoSQL);
		
		try {

//			Se utiliza para vincular el ID del AI (AutoIncrement) de las tabla SQL.
			// Ejecutamos la inserción especificando que queremos las claves generadas
			s.executeUpdate(comandoSQL, Statement.RETURN_GENERATED_KEYS);

	        // Obtener ID generado
	        ResultSet generatedKeys = s.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	
	            int idGenerado = generatedKeys.getInt(1);
	            C_productos.add(new Producto(idGenerado, nombreP, precioP, stockP));
	            
	            System.out.println("✅ Producto añadido con éxito. ID: " + idGenerado);
	            
	        }

		} catch (SQLException e) {
		    System.out.println("❌ Error al crear el producto.");
		    e.printStackTrace();
		}

	}
	
	public void modificarProducto (ArrayList<Producto> C_productos, Statement s) {
		Scanner sc = new Scanner (System.in);
		
//		Mostramos los productos
		for (Producto p1 : C_productos) {
			
			p1.mostrarProducto();
			
		}
		
		System.out.println("Dime el ID del producto que quieres buscar -> ");
		int idUsuario = sc.nextInt();
		sc.nextLine();
		
		boolean encontrado = false;
		
		for (Producto p2 : C_productos) {
			
//			Buscamos por ID
			if (idUsuario == p2.getId_producto()) {
				encontrado = true;
				
				System.out.println("Que desea modificar ->");
				System.out.println("1. Precio");
				System.out.println("2. Stock");
				char opc = sc.nextLine().charAt(0);
			
				try {
				
					switch(opc) {
					case '1':
						System.out.println("Cual es el precio nuevo: ");
						double newPrecio = sc.nextDouble();

//						Se ejecuta en el SQL
						String comandoSQL_Precio = String.format("UPDATE productos SET precio = '%s' WHERE id_producto = '%s'", newPrecio, idUsuario);
						s.executeUpdate(comandoSQL_Precio);
						
//						Se agrega en el Array
						p2.setPrecio(newPrecio);
						
						System.out.println("✅ Precio actualizado correctamente");
						
						break;

					case '2':
						System.out.println("Cual es el stock nuevo: 1-hay / 0-no");
						int newStock;
						
						do {
							
				            newStock = sc.nextInt();
				            
				            if (newStock != 0 && newStock != 1) {
				            	
				                System.out.println("❌ Error: Solo se permite 0 (no) o 1 (sí).");
				                
				            }
				            
				        } while (newStock != 0 && newStock != 1);

//						Se ejecuta en el SQL
						String comandoSQL_Stock = String.format("UPDATE productos SET stock = '%s' WHERE id_producto = '%s'", newStock, idUsuario);
						s.executeUpdate(comandoSQL_Stock);
						
//						Se agrega en el Array
						p2.setStock(newStock);
						
						System.out.println("✅ Stock actualizado correctamente");
						break;
						
					default:
						System.out.println("❌ Opción no válida");

					}
				
				} catch (SQLException e) {
					
					System.out.println("❌ Error al actualizar en la base de datos");
	                e.printStackTrace();
					
				}
				
			}
			
		}
		
		if(!encontrado) {
	        System.out.println("❌ No existe producto con ID: " + idUsuario);
	    }
		
	}
	
}
