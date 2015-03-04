package sy.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Symenu entity. @author MyEclipse Persistence Tools
 */

public class Symenu implements java.io.Serializable {

	// Fields

	private String id;
	private Symenu symenu;
	private String text;
	private String iconcls;
	private String src;
	private BigDecimal seq;
	private Set symenus = new HashSet(0);

	// Constructors

	/** default constructor */
	public Symenu() {
	}

	/** minimal constructor */
	public Symenu(String id, String text, BigDecimal seq) {
		this.id = id;
		this.text = text;
		this.seq = seq;
	}

	/** full constructor */
	public Symenu(String id, Symenu symenu, String text, String iconcls, String src, BigDecimal seq, Set symenus) {
		this.id = id;
		this.symenu = symenu;
		this.text = text;
		this.iconcls = iconcls;
		this.src = src;
		this.seq = seq;
		this.symenus = symenus;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Symenu getSymenu() {
		return this.symenu;
	}

	public void setSymenu(Symenu symenu) {
		this.symenu = symenu;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public Set getSymenus() {
		return this.symenus;
	}

	public void setSymenus(Set symenus) {
		this.symenus = symenus;
	}

}