package logicaDAO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import excepciones.NoExistePaquete;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import logica.DTOferta;
import logica.DTPaquete;
import logica.Fabrica;
import logica.IOferta;
import logica.Oferta.Estados;


@Entity

//Definimos un nombre específico para el nombre de la tabla. Por defecto sería EmpresaDAO.
@Table(name="OFERTAS_FINALIZADAS")
public class OfertaDAO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_oferta", unique = true, nullable = false)
    private int  idoferta; // Clave principal autoincremental

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(length = 1000, nullable = false) // Ajusta el tamaño según tus necesidades
    private String descripcion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private String horario;

    @Column(nullable = false)
    private double remuneracion;

	@Column(name = "tipo_publicacion", nullable = false)
	private String miTPublicacion;

	@Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;


	@Column(name = "fecha_baja", nullable =false)
    private Date fechaBaja;

    @Column(nullable = false)
    private double costo;

	private String paquete;

	private String imagen;

	@ManyToOne
	@JoinTable(name = "EMPRESAS_OFERTAS_FINALIZADAS",joinColumns = @JoinColumn(name = "ID_OFERTA"), inverseJoinColumns = @JoinColumn(name = "ID_EMPRESA", nullable = false))
    private EmpresaDAO idempresa; // Clave foránea


    @OneToMany(mappedBy = "idoferta",cascade = CascadeType.PERSIST)
	private Set<PostulacionDAO> misPostulaciones;

    @ManyToMany()
    @JoinTable(
    	    name = "KEYWORDS_OFERTAS_FINALIZADAS",
    	    joinColumns = @JoinColumn(name = "ID_OFERTA"),
    	    inverseJoinColumns = @JoinColumn(name = "ID_KEYWORD")
    	)
    private Set<KeywordDAO> misKeywords;


	public OfertaDAO() {}

	/**
    *
    */
   public OfertaDAO(String nom, String des, String ciu, String dep, String hora, double rem, Date fAlta,Date fBaja, String tipoPub, String img) {
   	nombre = nom;
   	descripcion = des;
   	ciudad = ciu;
   	departamento = dep;
   	imagen = img;
   	horario = hora;
   	remuneracion = rem;
   	fechaAlta = fAlta;
   	miTPublicacion = tipoPub;
   	fechaBaja = fBaja;
   	misPostulaciones = new HashSet<>();
   }




   /**
    * @return
    */
   public String getNombre() {
       return nombre;
   }
   /**
    * @param nom
    */
   public void setNombre(String nom) {
       nombre = nom;
   }

   /**
    * @return
    */
   public String getDescripcion() {
       return descripcion;
   }

   /**
    * @param desc
    */
   public void setDescripcion(String desc) {
       descripcion = desc;
   }

   /**
    * @return
    */
   public String getCiudad() {
       return ciudad;
   }

   /**
    * @param ciu
    */
   public void setCiudad(String ciu) {
       ciudad = ciu;
   }

   /**
    * @return
    */
   public String getDepartamento() {
       return departamento;
   }

   /**
    * @param dep
    */
   public void setDepartamento(String dep) {
       departamento = dep;
   }

   public String getHorario() {
   	return horario;
   }

   /**
    * @param value
    */
   public void setHorario(String value) {
       horario = value;
   }

   /**
    * @return
    */
   public double getRemuneracion() {
       return remuneracion;
   }

   /**
    * @param rem
    */
   public void setRemuneracion(double rem) {
       remuneracion = rem;
   }

   /**
    * @return
    */
   public Date getFechaAlta() {
       return fechaAlta;
   }

   /**
    * @param fA
    */
   public void setFechaAlta(Date fAl) {
       fechaAlta = fAl;
   }
   /**
    * @return
    */
   public Date getFechaBaja() {
       return fechaBaja;
   }

   /**
    * @param fA
    */
   public void setFechaBaja(Date fBaj) {
       fechaBaja = fBaj;
   }

   public void setEmpresa(EmpresaDAO emp) {
	   idempresa = emp;
	}

	public EmpresaDAO getEmpresa() {
		return idempresa;
	}

	public String getTipoPublicacion() {
    	return miTPublicacion;
    }

    public void setTipoPublicacion(String tpub) {
    	miTPublicacion = tpub;
    }

    public Set<PostulacionDAO> getPostulacionesDAO() {

        return misPostulaciones;
    }

    public void setPostulacionDAO(Set<PostulacionDAO> postulaciones) {
    	misPostulaciones = postulaciones;
    }

    public void addPostulacionDAO(PostulacionDAO post) {
    	misPostulaciones.add(post);
    }

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getIdoferta() {
		return idoferta;
	}

	public void setIdoferta(int idoferta) {
		this.idoferta = idoferta;
	}

	public DTOferta getDTOferta() {
		IOferta iOf = Fabrica.getIOferta();
		Set<String> nomPost = new HashSet<>();
		String desc = descripcion.toString();
		for (PostulacionDAO post : misPostulaciones) {
			nomPost.add(post.getIdPostulante().getNickname());
		}
		if(paquete!=null) {

		}
		DTOferta ofe =  new DTOferta(nombre,desc,ciudad,departamento,horario,remuneracion,fechaAlta,fechaBaja,nomPost,miTPublicacion,imagen,idempresa.getNickname());
		if(paquete!=null) {
			DTPaquete paq;
			try {
				paq = iOf.getInfoPaquete(paquete);
				ofe.setDTPaqueteAsociado(paq);

			} catch (NoExistePaquete e) {
			}
		}
		ofe.setEstado(Estados.finalizada);
		Set<String> keys = new HashSet<>();
		for(KeywordDAO key:misKeywords){
			keys.add(key.getNombre());
		}
		ofe.setKeywords(keys);
		return ofe;
	}

	public Set<KeywordDAO> getMisKeywords() {
		return misKeywords;
	}

	public void setMisKeyWords(Set<KeywordDAO> misKeywords) {
		this.misKeywords = misKeywords;
	}
}
