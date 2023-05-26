package model;



public class Rapport{
	private String Type;
	private Integer idRapport;
    private String filePath;
    private String nometudiant;
    private String classe;
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Integer getIdRapport() {
		return idRapport;
	}
	public void setIdRapport(Integer idRapport) {
		this.idRapport = idRapport;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getNometudiant() {
		return nometudiant;
	}
	public Rapport(String type, Integer idRapport, String filePath, String nometudiant, String classe) {
	
		this.Type = type;
		this.idRapport = idRapport;
		this.filePath = filePath;
		this.nometudiant = nometudiant;
		this.classe = classe;
	}
	public void setNometudiant(String nometudiant) {
		this.nometudiant = nometudiant;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Rapport(String type, String filePath, String nometudiant, String classe) {
		super();
		Type = type;
		this.filePath = filePath;
		this.nometudiant = nometudiant;
		this.classe = classe;
	}
	@Override
	public String toString() {
		return "Rapport [Type=" + Type + ", idRapport=" + idRapport + ", filePath=" + filePath + ", nometudiant="
				+ nometudiant + ", classe=" + classe + "]";
	}
    
 
	

	
	
	
}