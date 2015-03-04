package sy.model;

import java.math.BigDecimal;

/**
 * Syportal entity. @author MyEclipse Persistence Tools
 */

public class Syportal implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String src;
	private BigDecimal height;
	private String closable;
	private String collapsible;
	private BigDecimal seq;

	// Constructors

	/** default constructor */
	public Syportal() {
	}

	/** minimal constructor */
	public Syportal(String id, String title, String src) {
		this.id = id;
		this.title = title;
		this.src = src;
	}

	/** full constructor */
	public Syportal(String id, String title, String src, BigDecimal height, String closable, String collapsible, BigDecimal seq) {
		this.id = id;
		this.title = title;
		this.src = src;
		this.height = height;
		this.closable = closable;
		this.collapsible = collapsible;
		this.seq = seq;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getClosable() {
		return this.closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	public String getCollapsible() {
		return this.collapsible;
	}

	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}