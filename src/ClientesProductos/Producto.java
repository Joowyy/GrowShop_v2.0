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
	private int cantidad;
	private int stock;

//	CONSTRUCTORES
	public Producto() {
		
	}
	public Producto(String nombre, double precio, int cantidad, int stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.stock = stock;
	}
	public Producto(int id_producto, String nombre, double precio, int cantidad, int stock) {
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
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
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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
	    ArrayList<Producto> C_productos = new ArrayList<>();
	    
	    try {
	    	
	        ResultSet rs = s.executeQuery("SELECT * FROM productos");
	        
	        while (rs.next()) {
	        	int id_producto = rs.getInt("id_producto");
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            int cantidad = rs.getInt("cantidad");
	            int stock = rs.getInt("stock");

	            C_productos.add(new Producto(id_producto, nombre, precio, cantidad, stock));
	            
	        }
	        
	    } catch (SQLException e) {
	    	
	        e.printStackTrace();
	        
	    }

	    return C_productos;
	}

	public void mostrarProducto() {
		
		System.out.println("\n________________________\n    --PRODUCTOS--");
		System.out.println("------------------------");
		System.out.println("ID -> " + id_producto);
		System.out.println("Nombre -> " + nombre);
		System.out.println("Precio -> " + precio);
		System.out.println("Cantidad -> " + cantidad);
		System.out.println("Stock -> " + stock);
		System.out.println("------------------------\n");
		
	}
	
	public void añadirProducto(ArrayList<Producto> C_productos, Statement s) {
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Nombre del producto: ");
		String nombreP = sc.nextLine();
		System.out.println("Precio del producto: ");
		double precioP = sc.nextDouble();
		System.out.println("Cuanto stock habrá: ");
		int cantidadP = sc.nextInt();
		int stockP;
        
		do {
        	
            System.out.println("¿Habrá stock en 2d max.? 1-si / 0-no");
            stockP = sc.nextInt();
            
            if (stockP != 0 && stockP != 1) {
            	
                System.out.println("❌ Error: Solo se permite 0 (no) o 1 (sí).");
                
            }
            
        } while (stockP != 0 && stockP != 1);
		
		String comandoSQL = String.format("INSERT INTO productos (nombre, precio, cantidad, stock) VALUES ('%s', '%s', '%s', '%s')", nombreP, precioP, cantidadP, stockP);
		
		try {

//			Se utiliza para vincular el ID del AI (AutoIncrement) de las tabla SQL.
			// Ejecutamos la inserción especificando que queremos las claves generadas
			s.executeUpdate(comandoSQL, Statement.RETURN_GENERATED_KEYS);

	        // Obtener ID generado
	        ResultSet generatedKeys = s.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	
	            int idGenerado = generatedKeys.getInt(1);
	            C_productos.add(new Producto(idGenerado, nombreP, precioP, cantidadP, stockP));
	            
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
				System.out.println("2. Cantidad");
				System.out.println("3. Stock");
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
						System.out.println("Cual es el precio nuevo: ");
						double newCantidad = sc.nextDouble();

//						Se ejecuta en el SQL
						String comandoSQL_Cantidad = String.format("UPDATE productos SET cantidad = '%s' WHERE id_producto = '%s'", newCantidad, idUsuario);
						s.executeUpdate(comandoSQL_Cantidad);
						
//						Se agrega en el Array
						p2.setPrecio(newCantidad);
						
						System.out.println("✅ Cantidad actualizada correctamente");
						
						break;

					case '3':
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
	
	public void eliminarProducto (ArrayList<Producto> C_productos, Statement s) {
		Scanner sc = new Scanner (System.in);
		boolean eliminado = false;
		
		for (Producto p1 : C_productos) {
			
			p1.mostrarProducto();
			
		}
		
		System.out.println("Estos son los productos actuales\nDime el ID del producto:");
		int idEliminar = sc.nextInt();
		
		for (Producto p2 : C_productos) {
			
			if (p2.getId_producto() == idEliminar) {
				
				try {
				
					C_productos.remove(p2);
					eliminado = true;
				
					String comandoSQL = String.format("DELETE FROM productos WHERE id_producto = '%s'", idEliminar);
				
					s.execute(comandoSQL);
					System.out.println("✅ Producto eliminado correctamente");
					break;
					
				} catch (SQLException e) {

					System.out.println("❌ Error al actualizar en la base de datos");
					e.printStackTrace();
					
				}
				
			}
			
		}
		
		if (!eliminado) {
			
			System.out.println("❌ No existe producto con ID: " + idEliminar);
			
		}
		
//		if (eliminado) {
//			
//	        try {
//	        	
//	            // Resetear AUTO_INCREMENT al máximo ID + 1
//	            s.execute("SET @new_auto_inc = (SELECT MAX(id_producto) + 1 FROM productos)");
//	            s.execute("ALTER TABLE productos AUTO_INCREMENT = @new_auto_inc");
//	            
//	        } catch (SQLException e) {
//	        	
//	            System.out.println("⚠️ No se pudo reajustar el AUTO_INCREMENT, pero el producto se eliminó.");
//	            
//	        }
//	    }
		
	}
	
}
