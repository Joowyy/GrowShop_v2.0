package ClientesProductos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {

//	ATRIBUTOS
	private int id_cliente;
	private String nombre;
	private int edad;
	
//	CONSTRUCTORES
	public Cliente () {
		
	}
	public Cliente (int id_cliente, String nombre, int edad) {
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.edad = edad;
	}
	
//	GETTERS SETTERS
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
//	METODOS
//	Actualiza la BDD en el ArrayList<Producto>
	public static ArrayList<Cliente> cargarClientes(Statement s) {
	    ArrayList<Cliente> C_clientes = new ArrayList<>();
	    
	    try {
	    	
	        ResultSet rs = s.executeQuery("SELECT * FROM clientes");
	        
	        while (rs.next()) {
	        	int id_cliente = rs.getInt("id_cliente");
	            String nombre = rs.getString("nombre");
	            int edad = rs.getInt("edad");

	            C_clientes.add(new Cliente(id_cliente, nombre, edad));
	            
	        }
	        
	    } catch (SQLException e) {
	    	
	        e.printStackTrace();
	        
	    }

	    return C_clientes;
	}
	
	public void mostrarCliente() {
		
		System.out.println("\n________________________\n    --CLIENTES--");
		System.out.println("------------------------");
		System.out.println("ID -> " + id_cliente);
		System.out.println("Nombre -> " + nombre);
		System.out.println("Edad -> " + edad);
		System.out.println("------------------------\n");
		
	}
	
	public void registrarCliente (ArrayList<Cliente> C_clientes, Statement s) {
		Scanner sc = new Scanner (System.in);
		boolean clienteReg = false;
		
		do {
	
			System.out.println("Dime el nombre del cliente: ");
			String nombreC = sc.nextLine();
			System.out.println("Dime su edad: ");
			int edadC = sc.nextInt();

			try {
			
				if (edadC < 18) {

					System.out.println("❌ Error: Solo pueden mayores de edad");
					break;

				} else {

					String comandoSQL = String.format("INSERT INTO clientes (nombre, edad) VALUES ('%s', '%s');", nombreC, edadC);
					
					// Ejecutamos la inserción especificando que queremos las claves generadas
					s.executeUpdate(comandoSQL, Statement.RETURN_GENERATED_KEYS);

			        // Obtener ID generado
			        ResultSet generatedKeys = s.getGeneratedKeys();
			        if (generatedKeys.next()) {
			        	
			            int idGenerado = generatedKeys.getInt(1);
			            C_clientes.add(new Cliente(idGenerado, nombreC, edadC));
			            
			            clienteReg = true;
			            System.out.println("✅ Cliente registrado correctamente");
			            
			        }

				}
			
			} catch (SQLException e) {
				
				System.out.println("❌ Error al crear el producto.");
				e.printStackTrace();
				
			}
		
		} while (!clienteReg);
		
		if (!clienteReg) {
			
			System.out.println("❌ Error registrar al cliente.");
			
		}
		
	}
	
	public void eliminarCliente (ArrayList<Cliente> C_clientes, Statement s) {
		Scanner sc = new Scanner (System.in);
		boolean clienteEli = false;
		
		for (Cliente c1 : C_clientes) {
			
			c1.mostrarCliente();
			
		}
		
		System.out.println("Dime el ID del cliente a eliminar: ");
		int idEliminar = sc.nextInt();
		
		for (Cliente c2 : C_clientes) {
			
			try {
			
				if (c2.getId_cliente() == idEliminar) {

					String comandoSQL = String.format("DELETE FROM clientes WHERE id_cliente = '%s'", idEliminar);
					s.execute(comandoSQL);

					C_clientes.remove(c2);

					clienteEli = true;
					System.out.println("✅ Cliente eliminado correctamente");
					break;

				}

			} catch (SQLException e) {
				
				System.out.println("❌ Error al crear el producto.");
				e.printStackTrace();
				
			}
			
		}
		
		if (!clienteEli) {
			
			System.out.println("❌ Error al eliminar cliente.");
			
		}
		
	}
	
}
