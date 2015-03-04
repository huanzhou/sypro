package sy.model;

/**
 * SyroleSyresources entity. @author MyEclipse Persistence Tools
 */

public class SyroleSyresources implements java.io.Serializable {

	// Fields

	private String id;
	private Syrole syrole;
	private Syresources syresources;

	// Constructors

	/** default constructor */
	public SyroleSyresources() {
	}

	/** full constructor */
	public SyroleSyresources(String id, Syrole syrole, Syresources syresources) {
		this.id = id;
		this.syrole = syrole;
		this.syresources = syresources;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Syrole getSyrole() {
		return this.syrole;
	}

	public void setSyrole(Syrole syrole) {
		this.syrole = syrole;
	}

	public Syresources getSyresources() {
		return this.syresources;
	}

	public void setSyresources(Syresources syresources) {
		this.syresources = syresources;
	}

}