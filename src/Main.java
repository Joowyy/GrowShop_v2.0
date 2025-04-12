import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import ClientesProductos.Producto;

public class Main {
	
//	URL MySQL Global
	static String url = "jdbc:mysql://localhost:3306/jowygrowshop";
	private static Connection conexion;
	
	public static void main(String[] args) {
//		Conexion a la BDD
		Statement s = conexionBDD();
		
		ArrayList<Producto> C_productos = Producto.cargarProductos(s);
		
		char opc;
		
		do {
		
			opc = menu();
			switch (opc) {
            case '1':
                menuProductos(C_productos);
                break;
            
            case '2':
                menuClientes();
                break;
            
            case '3':
                //procesarVenta();
                break;
            
            case '4':
                mostrarTablas(s);
                break;
            
            case '0':
            	cerrarConexion();
                System.out.println("¡Gracias por usar Jowy GrowShop!");
                return;
            
            default:
                System.out.println("⚠️ Opción inválida. Intente nuevamente.");
                
			}
		
		} while (opc != '5');

	}

//	---- IMPRIMIR TABLAS ----
	public static void mostrarTablas (Statement s) {
		Scanner sc = new Scanner (System.in);
		
		System.out.println("¿Quieres ver la tabla productos o clientes?");
		String tabla = sc.nextLine();
		
		if (tabla.equalsIgnoreCase("clientes")) {
		
//			'while' hace falta para imprimir todas las filas y columnas
			System.out.println("________________________\n    --CLIENTES--");
			System.out.println("------------------------");
			System.out.println("ID | Nombre | Edad\n");
			try {
				
				ResultSet rs = s.executeQuery("SELECT * FROM clientes");
				while (rs.next()) {
	
					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
					
				}
				System.out.println("------------------------");
				
			} catch (SQLException e) {
	
				e.printStackTrace();
				
			}
		
		} else if (tabla.equalsIgnoreCase("productos")) {
			
			System.out.println("________________________\n    --PRODUCTOS--");
			System.out.println("------------------------");
			System.out.println("ID | Nombre | Precio | Stock\n");
			try {
				
				ResultSet rs = s.executeQuery("SELECT * FROM productos");
				while (rs.next()) {
	
					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4));
					
				}
				System.out.println("------------------------");
				
			} catch (SQLException e) {
	
				e.printStackTrace();
				
			}
			
		}
		
	}
	
//	---- CONEXION A LA BDD ----
	private static Statement conexionBDD () {

		try {
			
			conexion = DriverManager.getConnection(url, "root", "");
			Statement s = conexion.createStatement();
			System.out.println("✅ Conexión a la BDD establecida");
			
			return s;
			
		} catch (SQLException e) {

			System.err.println("❌ Error al conectar a la BDD: " + e.getMessage());
            System.exit(1);
			
		}
		
		return null;
	}
	
	private static void cerrarConexion() {
        try {
        	
        	System.out.println("\n✅ Conexión a la BDD cerrada con éxito");
            if (conexion != null) conexion.close();
            
        } catch (SQLException e) {
        	
            System.err.println("Error al cerrar conexión: " + e.getMessage());
            
        }
    }

//	---- MENU PRINCIPAL ----
	public static char menu () {
		Scanner sc = new Scanner(System.in);
		char opc;
		
		System.out.println("\n🌿 JOWY GROWSHOP 🌿");
        System.out.println("1. Gestión de Productos");
        System.out.println("2. Gestión de Clientes");
        System.out.println("3. Procesar Venta");
        System.out.println("4. Mostrar tablas SQL");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
		opc = sc.nextLine().charAt(0);
		
		return opc;
	}
	
// 	--- SUBMENU PRODUCTOS ---
    public static void menuProductos(ArrayList<Producto> C_productos) {
        Scanner sc = new Scanner(System.in);
        char opc;
        
    	do {
            System.out.println("\n📦 GESTIÓN DE PRODUCTOS");
            System.out.println("1. Listar productos");
            System.out.println("2. Añadir producto");
            System.out.println("3. Modificar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            opc = sc.nextLine().charAt(0);

            switch (opc) {
                case '1':
                    listarProductos(C_productos);
                    break;
                    
                case '2':
                    //añadirProducto();
                    break;
                    
                case '3':
                    //modificarProducto();
                    break;
                    
                case '4':
                    //eliminarProducto();
                    break;
                    
                case '0':
                    return;
                    
                default:
                    System.out.println("Opción inválida");
                    
            }
            
        } while (opc != '0');
    	
    }
    
 // --- SUBMENU CLIENTES ---
    private static void menuClientes() {
    	Scanner sc = new Scanner(System.in);
        char opc;
    	
    	do {
            System.out.println("\n👥 GESTIÓN DE CLIENTES");
            System.out.println("1. Listar clientes");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opc = sc.nextLine().charAt(0);

            switch (opc) {
                case '1':
                    //listarClientes();
                    break;
                    
                case '2':
                    //registrarCliente();
                    break;
                    
                case '3':
                    //buscarCliente();
                    break;
                    
                case '0':
                    return;
                    
                default:
                    System.out.println("Opción inválida");
            }
            
        } while (opc != '0');
    	
    }
    
 // --- FUNCIONES CLIENTES ---
    public static void listarProductos (ArrayList<Producto> C_productos) {
    	
    	for (Producto p1 : C_productos) {
    		
    		p1.mostrarProducto();
    		
    	}
    	
    }
	
}