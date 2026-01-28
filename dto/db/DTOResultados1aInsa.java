package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "RESULTADOS_1A_INSA", schema = "INSA1")
public class DTOResultados1aInsa implements Serializable {

	private static final long serialVersionUID = 1196956203073202571L;
	
	@NotNull
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@NotNull
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@NotNull
	@Column(name = "SECCION", nullable = false, precision = 4, scale = 0)
	private Integer seccion;
	
	@Id
	@NotNull
	@Column(name = "ID_CASILLA", nullable = false, precision = 2, scale = 0)
	private Integer idCasilla;
	
	@Id
	@NotNull
	@Column(name = "TIPO_CASILLA", nullable = false, length = 1)
	private String tipoCasilla;
	
	@Column(name = "EXT_CONTIGUA", nullable = true, precision = 2, scale = 0)
	private Integer extContigua;
	
	@Id
	@NotNull
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	@Column(name = "LISTA_NOMINAL", nullable = true, precision = 6, scale = 0)
	private Integer listaNominal;
	
	@Column(name = "ENERO", nullable = true, precision = 4, scale = 0)
	private Integer enero = 0;
	
	@Column(name = "FEBRERO", nullable = true, precision = 4, scale = 0)
	private Integer febrero = 0;
	
	@Column(name = "MARZO", nullable = true, precision = 4, scale = 0)
	private Integer marzo = 0;
	
	@Column(name = "ABRIL", nullable = true, precision = 4, scale = 0)
	private Integer abril = 0;
	
	@Column(name = "MAYO", nullable = true, precision = 4, scale = 0)
	private Integer mayo = 0;
	
	@Column(name = "JUNIO", nullable = true, precision = 4, scale = 0)
	private Integer junio = 0;
	
	@Column(name = "JULIO", nullable = true, precision = 4, scale = 0)
	private Integer julio = 0;
	
	@Column(name = "AGOSTO", nullable = true, precision = 4, scale = 0)
	private Integer agosto = 0;
	
	@Column(name = "SEPTIEMBRE", nullable = true, precision = 4, scale = 0)
	private Integer septiembre = 0;
	
	@Column(name = "OCTUBRE", nullable = true, precision = 4, scale = 0)
	private Integer octubre = 0;
	
	@Column(name = "NOVIEMBRE", nullable = true, precision = 4, scale = 0)
	private Integer noviembre = 0;
	
	@Column(name = "DICIEMBRE", nullable = true, precision = 4, scale = 0)
	private Integer diciembre = 0;
	
	@Column(name = "HOMBRES", nullable = true, precision = 5, scale = 0)
	private Integer hombres = 0;
	
	@Column(name = "MUJERES", nullable = true, precision = 5, scale = 0)
	private Integer mujeres = 0;
	
	@Column(name = "NO_BINARIO", nullable = true, precision = 5, scale = 0)
	private Integer noBinario = 0;
	
	@Column(name = "PORCENTAJE_HOMBRES", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeHombres = 0;
	
	@Column(name = "PORCENTAJE_MUJERES", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeMujeres = 0;
	
	@Column(name = "PORCENTAJE_NO_BINARIOS", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeNoBinarios = 0;
	
	@Column(name = "DOBLE_NACIONALIDAD", nullable = true, precision = 4, scale = 0)
	private Integer dobleNacionalidad= 0;
	
	@Column(name = "PORCENTAJE_DOBLE_NACIONALIDAD", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeDobleNacionalidad = 0;
	
	@Column(name = "HOMBRES_DOBLE_NACIONALIDAD", nullable = true, precision = 4, scale = 0)
	private Integer hombresDobleNacionalidad= 0;
	
	@Column(name = "POR_HOMBRES_DOBLE_NACIONALIDAD", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeHombresDobleNacionalidad = 0;
	
	@Column(name = "MUJERES_DOBLE_NACIONALIDAD", nullable = true, precision = 4, scale = 0)
	private Integer mujeresDobleNacionalidad= 0;
	
	@Column(name = "POR_MUJERES_DOBLE_NACIONALIDAD", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeMujeresDobleNacionalidad = 0;
	
	@Column(name = "NO_BINARIO_DOBLE_NACIONALIDAD", nullable = true, precision = 4, scale = 0)
	private Integer noBinarioDobleNacionalidad= 0;
	
	@Column(name = "POR_NO_BIN_DOBLE_NACIONALIDAD", nullable = true, precision = 5, scale = 0)
	private Integer porcentajeNoBinDobleNacionalidad = 0;
	
	@Formula(" HOMBRES + MUJERES + NO_BINARIO ")
	private Integer insaculados;

	public DTOResultados1aInsa() {
		super();
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public Integer getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Integer idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public Integer getExtContigua() {
		return extContigua;
	}

	public void setExtContigua(Integer extContigua) {
		this.extContigua = extContigua;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Integer getListaNominal() {
		return listaNominal;
	}

	public void setListaNominal(Integer listaNominal) {
		this.listaNominal = listaNominal;
	}

	public Integer getEnero() {
		return enero;
	}

	public void setEnero(Integer enero) {
		this.enero = enero;
	}

	public Integer getFebrero() {
		return febrero;
	}

	public void setFebrero(Integer febrero) {
		this.febrero = febrero;
	}

	public Integer getMarzo() {
		return marzo;
	}

	public void setMarzo(Integer marzo) {
		this.marzo = marzo;
	}

	public Integer getAbril() {
		return abril;
	}

	public void setAbril(Integer abril) {
		this.abril = abril;
	}

	public Integer getMayo() {
		return mayo;
	}

	public void setMayo(Integer mayo) {
		this.mayo = mayo;
	}

	public Integer getJunio() {
		return junio;
	}

	public void setJunio(Integer junio) {
		this.junio = junio;
	}

	public Integer getJulio() {
		return julio;
	}

	public void setJulio(Integer julio) {
		this.julio = julio;
	}

	public Integer getAgosto() {
		return agosto;
	}

	public void setAgosto(Integer agosto) {
		this.agosto = agosto;
	}

	public Integer getSeptiembre() {
		return septiembre;
	}

	public void setSeptiembre(Integer septiembre) {
		this.septiembre = septiembre;
	}

	public Integer getOctubre() {
		return octubre;
	}

	public void setOctubre(Integer octubre) {
		this.octubre = octubre;
	}

	public Integer getNoviembre() {
		return noviembre;
	}

	public void setNoviembre(Integer noviembre) {
		this.noviembre = noviembre;
	}

	public Integer getDiciembre() {
		return diciembre;
	}

	public void setDiciembre(Integer diciembre) {
		this.diciembre = diciembre;
	}

	public Integer getHombres() {
		return hombres;
	}

	public void setHombres(Integer hombres) {
		this.hombres = hombres;
	}

	public Integer getMujeres() {
		return mujeres;
	}

	public void setMujeres(Integer mujeres) {
		this.mujeres = mujeres;
	}

	public Integer getNoBinario() {
		return noBinario;
	}

	public void setNoBinario(Integer noBinario) {
		this.noBinario = noBinario;
	}

	public Integer getPorcentajeHombres() {
		return porcentajeHombres;
	}

	public void setPorcentajeHombres(Integer porcentajeHombres) {
		this.porcentajeHombres = porcentajeHombres;
	}

	public Integer getPorcentajeMujeres() {
		return porcentajeMujeres;
	}

	public void setPorcentajeMujeres(Integer porcentajeMujeres) {
		this.porcentajeMujeres = porcentajeMujeres;
	}

	public Integer getPorcentajeNoBinarios() {
		return porcentajeNoBinarios;
	}

	public void setPorcentajeNoBinarios(Integer porcentajeNoBinarios) {
		this.porcentajeNoBinarios = porcentajeNoBinarios;
	}

	public Integer getDobleNacionalidad() {
		return dobleNacionalidad;
	}

	public void setDobleNacionalidad(Integer dobleNacionalidad) {
		this.dobleNacionalidad = dobleNacionalidad;
	}
	
	public Integer getPorcentajeDobleNacionalidad() {
		return porcentajeDobleNacionalidad;
	}

	public void setPorcentajeDobleNacionalidad(Integer porcentajeDobleNacionalidad) {
		this.porcentajeDobleNacionalidad = porcentajeDobleNacionalidad;
	}
	
	public Integer getHombresDobleNacionalidad() {
		return hombresDobleNacionalidad;
	}

	public void setHombresDobleNacionalidad(Integer hombresDobleNacionalidad) {
		this.hombresDobleNacionalidad = hombresDobleNacionalidad;
	}

	public Integer getPorcentajeHombresDobleNacionalidad() {
		return porcentajeHombresDobleNacionalidad;
	}

	public void setPorcentajeHombresDobleNacionalidad(Integer porcentajeHombresDobleNacionalidad) {
		this.porcentajeHombresDobleNacionalidad = porcentajeHombresDobleNacionalidad;
	}

	public Integer getMujeresDobleNacionalidad() {
		return mujeresDobleNacionalidad;
	}

	public void setMujeresDobleNacionalidad(Integer mujeresDobleNacionalidad) {
		this.mujeresDobleNacionalidad = mujeresDobleNacionalidad;
	}

	public Integer getPorcentajeMujeresDobleNacionalidad() {
		return porcentajeMujeresDobleNacionalidad;
	}

	public void setPorcentajeMujeresDobleNacionalidad(Integer porcentajeMujeresDobleNacionalidad) {
		this.porcentajeMujeresDobleNacionalidad = porcentajeMujeresDobleNacionalidad;
	}

	public Integer getNoBinarioDobleNacionalidad() {
		return noBinarioDobleNacionalidad;
	}

	public void setNoBinarioDobleNacionalidad(Integer noBinarioDobleNacionalidad) {
		this.noBinarioDobleNacionalidad = noBinarioDobleNacionalidad;
	}

	public Integer getPorcentajeNoBinDobleNacionalidad() {
		return porcentajeNoBinDobleNacionalidad;
	}

	public void setPorcentajeNoBinDobleNacionalidad(Integer porcentajeNoBinDobleNacionalidad) {
		this.porcentajeNoBinDobleNacionalidad = porcentajeNoBinDobleNacionalidad;
	}

	public Integer getInsaculados() {
		return insaculados;
	}

	public void setInsaculados(Integer insaculados) {
		this.insaculados = insaculados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCasilla, idDetalleProceso, idParticipacion, seccion, tipoCasilla, idTipoVoto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOResultados1aInsa other = (DTOResultados1aInsa) obj;
		return Objects.equals(idCasilla, other.idCasilla) && Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idParticipacion, other.idParticipacion) && Objects.equals(seccion, other.seccion)
				&& Objects.equals(tipoCasilla, other.tipoCasilla)
				&& Objects.equals(idTipoVoto, other.idTipoVoto);
	}

	@Override
	public String toString() {
		return "DTOResultados1aInsa [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idParticipacion=" + idParticipacion + ", seccion=" + seccion + ", idCasilla="
				+ idCasilla + ", tipoCasilla=" + tipoCasilla + ", extContigua=" + extContigua + ", idTipoVoto="
				+ idTipoVoto + ", listaNominal=" + listaNominal + ", enero=" + enero + ", febrero=" + febrero
				+ ", marzo=" + marzo + ", abril=" + abril + ", mayo=" + mayo + ", junio=" + junio + ", julio=" + julio
				+ ", agosto=" + agosto + ", septiembre=" + septiembre + ", octubre=" + octubre + ", noviembre="
				+ noviembre + ", diciembre=" + diciembre + ", hombres=" + hombres + ", mujeres=" + mujeres
				+ ", noBinario=" + noBinario + ", porcentajeHombres=" + porcentajeHombres + ", porcentajeMujeres="
				+ porcentajeMujeres + ", porcentajeNoBinarios=" + porcentajeNoBinarios + ", dobleNacionalidad="
				+ dobleNacionalidad + ", porcentajeDobleNacionalidad=" + porcentajeDobleNacionalidad
				+ ", hombresDobleNacionalidad=" + hombresDobleNacionalidad + ", porcentajeHombresDobleNacionalidad="
				+ porcentajeHombresDobleNacionalidad + ", mujeresDobleNacionalidad=" + mujeresDobleNacionalidad
				+ ", porcentajeMujeresDobleNacionalidad=" + porcentajeMujeresDobleNacionalidad
				+ ", noBinarioDobleNacionalidad=" + noBinarioDobleNacionalidad + ", porcentajeNoBinDobleNacionalidad="
				+ porcentajeNoBinDobleNacionalidad + ", insaculados=" + insaculados + "]";
	}

}
