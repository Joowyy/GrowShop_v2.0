package Compras;

public class Compra {

//  ATRIBUTOS
	private int id_cliente;
	private int id_producto;
	private int cantidad_producto;
	
//  CONSTRUCTORES
	public Compra() {
		
	}
	public Compra(int id_cliente, int id_producto, int cantidad_producto) {
		this.id_cliente = id_cliente;
		this.id_producto = id_producto;
		this.cantidad_producto = cantidad_producto;
	}
	
//  GETTERS SETTERS
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_producto() {
		return id_producto;
	}
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	public int getCantidad_producto() {
		return cantidad_producto;
	}
	public void setCantidad_producto(int cantidad_producto) {
		this.cantidad_producto = cantidad_producto;
	}

}
