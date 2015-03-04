package sy.model;

/**
 * SyuserSyrole entity. @author MyEclipse Persistence Tools
 */

public class SyuserSyrole implements java.io.Serializable {

	// Fields

	private String id;
	private Syrole syrole;
	private Syuser syuser;

	// Constructors

	/** default constructor */
	public SyuserSyrole() {
	}

	/** full constructor */
	public SyuserSyrole(String id, Syrole syrole, Syuser syuser) {
		this.id = id;
		this.syrole = syrole;
		this.syuser = syuser;
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

	public Syuser getSyuser() {
		return this.syuser;
	}

	public void setSyuser(Syuser syuser) {
		this.syuser = syuser;
	}

}