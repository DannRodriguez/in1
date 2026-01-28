package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

/**
 * Clase que representa un objeto para Parametros Generales, del esquema ADMIN
 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
 * @version 1.0
 * @since 06/12/2016
 */
public class DTOParametrosGenerales implements Serializable {
	
	/**
	 * Serializado con java 11 25/09/2020
	 */
	private static final long serialVersionUID = 1153057890823059537L;
	// Valores PK
    private Integer idProcesoElectoral;
    private Integer idDetalleProceso;
    private Integer idEstado;
    private Integer idDistritoFed;
    private Integer idDistritoLoc;
    private Integer idMunicipio;
    private Integer idLocalidad;
    private Integer idComunidad;
    private Integer idRegiduria;
    private String tipoParametro;
    private Integer idParametro;

    // Campo de la tabla
    private String valorParametro;

    // Provienen de C_PARAMETROS
    private String descripcionParametro;
    private String descripcionValores;

    /**
     * @return el atributo idProcesoElectoral
     */
    public Integer getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    /**
     * @param idProcesoElectoral
     *        parametro idProcesoElectoral a actualizar
     */
    public void setIdProcesoElectoral(Integer idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

    /**
     * @return el atributo idDetalleProceso
     */
    public Integer getIdDetalleProceso() {
        return idDetalleProceso;
    }

    /**
     * @param idDetalleProceso
     *        parametro idDetalleProceso a actualizar
     */
    public void setIdDetalleProceso(Integer idDetalleProceso) {
        this.idDetalleProceso = idDetalleProceso;
    }

    /**
     * @return el atributo idEstado
     */
    public Integer getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado
     *        parametro idEstado a actualizar
     */
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * @return el atributo idDistritoFed
     */
    public Integer getIdDistritoFed() {
        return idDistritoFed;
    }

    /**
     * @param idDistritoFed
     *        parametro idDistritoFed a actualizar
     */
    public void setIdDistritoFed(Integer idDistritoFed) {
        this.idDistritoFed = idDistritoFed;
    }

    /**
     * @return el atributo idDistritoLoc
     */
    public Integer getIdDistritoLoc() {
        return idDistritoLoc;
    }

    /**
     * @param idDistritoLoc
     *        parametro idDistritoLoc a actualizar
     */
    public void setIdDistritoLoc(Integer idDistritoLoc) {
        this.idDistritoLoc = idDistritoLoc;
    }

    /**
     * @return el atributo idLocalidad
     */
    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    /**
     * @param idLocalidad
     *        parametro idLocalidad a actualizar
     */
    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    /**
     * @return el atributo idComunidad
     */
    public Integer getIdComunidad() {
        return idComunidad;
    }

    /**
     * @param idComunidad
     *        parametro idComunidad a actualizar
     */
    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    /**
     * @return el atributo tipoParametro
     */
    public String getTipoParametro() {
        return tipoParametro;
    }

    /**
     * @param tipoParametro
     *        parametro tipoParametro a actualizar
     */
    public void setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    /**
     * @return el atributo idParametro
     */
    public Integer getIdParametro() {
        return idParametro;
    }

    /**
     * @param idParametro
     *        parametro idParametro a actualizar
     */
    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    /**
     * @return el atributo valorParametro
     */
    public String getValorParametro() {
        return valorParametro;
    }

    /**
     * @param valorParametro
     *        parametro valorParametro a actualizar
     */
    public void setValorParametro(String valorParametro) {
        this.valorParametro = valorParametro;
    }

    /**
     * @return el atributo descripcionParametro
     */
    public String getDescripcionParametro() {
        return descripcionParametro;
    }

    /**
     * @param descripcionParametro
     *        parametro descripcionParametro a actualizar
     */
    public void setDescripcionParametro(String descripcionParametro) {
        this.descripcionParametro = descripcionParametro;
    }

    /**
     * @return el atributo descripcionValores
     */
    public String getDescripcionValores() {
        return descripcionValores;
    }

    /**
     * @param descripcionValores
     *        parametro descripcionValores a actualizar
     */
    public void setDescripcionValores(String descripcionValores) {
        this.descripcionValores = descripcionValores;
    }

    /**
     * @return el atributo idMunicipio
     */
    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * @param idMunicipio
     *        parametro idMunicipio a actualizar
     */
    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    /**
     * @return el atributo idRegiduria
     */
    public Integer getIdRegiduria() {
        return idRegiduria;
    }

    /**
     * @param idRegiduria
     *        parametro idRegiduria a actualizar
     */
    public void setIdRegiduria(Integer idRegiduria) {
        this.idRegiduria = idRegiduria;
    }

}
