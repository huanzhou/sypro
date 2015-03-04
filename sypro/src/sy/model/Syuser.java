package sy.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Syuser entity. @author MyEclipse Persistence Tools
 */

public class Syuser implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String password;
	private Date createdatetime;
	private Date modifydatetime;
	private Set syuserSyroles = new HashSet(0);

	// Constructors

	/** default constructor */
	public Syuser() {
	}

	/** minimal constructor */
	public Syuser(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	/** full constructor */
	public Syuser(String id, String name, String password, Date createdatetime, Date modifydatetime, Set syuserSyroles) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
		this.syuserSyroles = syuserSyroles;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public Set getSyuserSyroles() {
		return this.syuserSyroles;
	}

	public void setSyuserSyroles(Set syuserSyroles) {
		this.syuserSyroles = syuserSyroles;
	}

}