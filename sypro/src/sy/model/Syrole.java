package sy.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Syrole entity. @author MyEclipse Persistence Tools
 */

public class Syrole implements java.io.Serializable {

	// Fields

	private String id;
	private Syrole syrole;
	private String text;
	private BigDecimal seq;
	private String descript;
	private Set syuserSyroles = new HashSet(0);
	private Set syroles = new HashSet(0);
	private Set syroleSyresourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Syrole() {
	}

	/** minimal constructor */
	public Syrole(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public Syrole(String id, Syrole syrole, String text, BigDecimal seq, String descript, Set syuserSyroles, Set syroles, Set syroleSyresourceses) {
		this.id = id;
		this.syrole = syrole;
		this.text = text;
		this.seq = seq;
		this.descript = descript;
		this.syuserSyroles = syuserSyroles;
		this.syroles = syroles;
		this.syroleSyresourceses = syroleSyresourceses;
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Set getSyuserSyroles() {
		return this.syuserSyroles;
	}

	public void setSyuserSyroles(Set syuserSyroles) {
		this.syuserSyroles = syuserSyroles;
	}

	public Set getSyroles() {
		return this.syroles;
	}

	public void setSyroles(Set syroles) {
		this.syroles = syroles;
	}

	public Set getSyroleSyresourceses() {
		return this.syroleSyresourceses;
	}

	public void setSyroleSyresourceses(Set syroleSyresourceses) {
		this.syroleSyresourceses = syroleSyresourceses;
	}

}