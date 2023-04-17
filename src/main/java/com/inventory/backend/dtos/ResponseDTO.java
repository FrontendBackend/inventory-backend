package com.inventory.backend.dtos;

import java.util.Date;
import java.util.List;

import com.inventory.backend.exception.PgimException.TipoResultado;
import com.inventory.backend.utils.FechasUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data	
@NoArgsConstructor
public class ResponseDTO {
    
    public static final String MENSAJE_CONTACTE_ADMINISTRADOR = "Por favor vuelva a intentarlo y, si el problema persiste, repórtelo al correo %s";

	private String status; // success, info, error
	private Object data; //Devuelve el Object
	private String mensaje;//Mensaje
	private List<?> lista;//Retorna la lista siempre y cuando sea Success 	
	private Date timestamp;	//La fecha y hora de consulta o transaccion
	private Long id; //Id del objeto persistido cuando la transaccion es success
	private Number valor; //Devuelve el valor de la consulta
	
	/**
	 * Tipo de resultado de la operación realizada.
	 */
	private TipoResultado tipoResultado;

	public ResponseDTO(String status, Object data, String mensaje){
		this.status = status;
		this.data = data;
		this.mensaje = mensaje;
	} 

	public ResponseDTO(TipoResultado tipoResultado, Object data, String mensaje){
		this.tipoResultado = tipoResultado;
		this.data = data;
		this.mensaje = mensaje;
	} 

	public ResponseDTO(TipoResultado tipoResultado, Number valor, String mensaje){
		this.tipoResultado = tipoResultado;
		this.valor = valor;
		this.mensaje = mensaje;
	} 

	public ResponseDTO(String status, List<?> lista, String mensaje){
		this.status = status;
		this.lista = lista;
		this.mensaje = mensaje;
	} 

	public ResponseDTO(TipoResultado tipoResultado, String mensaje, Long id){
		this.tipoResultado = tipoResultado;
		this.mensaje = mensaje;
		this.id = id;
	} 

	public ResponseDTO(String status, String mensaje, Long id){
		this.status = status;
		this.mensaje = mensaje;
		this.id = id;
	} 

	public ResponseDTO(String status, String mensaje){
		this.status = status;
		this.mensaje = mensaje;
	} 	

	public ResponseDTO(TipoResultado tipoResultado, String mensaje){
		this.tipoResultado = tipoResultado;		
		this.mensaje = mensaje;
	}	
	
	public ResponseDTO(String status, String mensaje, Number valor){
		this.status = status;
		this.mensaje = mensaje;
		this.valor = valor;
	}

	public ResponseDTO(TipoResultado tipoResultado, String mensaje, Number valor){
		this.tipoResultado = tipoResultado;		
		this.mensaje = mensaje;
		this.valor = valor;
	}

	/**
	 * Permite configurar los datos de contacto del error.
	 * @param datosContacto
	 */
	public void configurarError(String datosContacto) {
		if (tipoResultado.equals(TipoResultado.ERROR)) {
			this.mensaje = this.mensaje + ". " + String.format(MENSAJE_CONTACTE_ADMINISTRADOR, datosContacto);
		}
	}

	public Date getTimestamp() {
		return timestamp!=null?timestamp:FechasUtil.getToFullDay();
	}
}
