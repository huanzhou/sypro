package sy.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Syresources entity. @author MyEclipse Persistence Tools
 */

public class Syresources implements java.io.Serializable {

	// Fields

	private String id;
	private Syresources syresources;
	private String text;
	private BigDecimal seq;
	private String src;
	private String descript;
	private String onoff;
	private Set syroleSyresourceses = new HashSet(0);
	private Set syresourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Syresources() {
	}

	/** minimal constructor */
	public Syresources(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public Syresources(String id, Syresources syresources, String text, BigDecimal seq, String src, String descript, String onoff, Set syroleSyresourceses, Set syresourceses) {
		this.id = id;
		this.syresources = syresources;
		this.text = text;
		this.seq = seq;
		this.src = src;
		this.descript = descript;
		this.onoff = onoff;
		this.syroleSyresourceses = syroleSyresourceses;
		this.syresourceses = syresourceses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Syresources getSyresources() {
		return this.syresources;
	}

	public void setSyresources(Syresources syresources) {
		this.syresources = syresources;
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

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getOnoff() {
		return this.onoff;
	}

	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}

	public Set getSyroleSyresourceses() {
		return this.syroleSyresourceses;
	}

	public void setSyroleSyresourceses(Set syroleSyresourceses) {
		this.syroleSyresourceses = syroleSyresourceses;
	}

	public Set getSyresourceses() {
		return this.syresourceses;
	}

	public void setSyresourceses(Set syresourceses) {
		this.syresourceses = syresourceses;
	}

}