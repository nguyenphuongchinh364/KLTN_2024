package vn.shopttcn.model;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class User {
	private int userId;

	@NotEmpty
	private String userFullname;

	@NotEmpty
	@Email
	private String userEmail;

	@NotEmpty
	private String userPhone;

	private Address userAddress;
	private String avatar;

	@NotEmpty
	private String username;

	private String password;
	private Role role;
	private int enabled; // trạng thái tài khoản (1: sử dụng được, 0: bị vô hiệu hoá)
	private Timestamp createAt;
	private Timestamp updateAt;

	public User(int userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}

	public User(int userId) {
		super();
		this.userId = userId;
	}

	// sử dụng trong validate
	public User(String userEmail, String userPhone, String username) {
		super();
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.username = username;
	}

	// sử dụng trong update status enabled
	public User(int userId, int enabled) {
		super();
		this.userId = userId;
		this.enabled = enabled;
	}

	// join table order
	public User(int userId, @NotEmpty String userFullname, @NotEmpty String username) {
		super();
		this.userId = userId;
		this.userFullname = userFullname;
		this.username = username;
	}

	// reviews
	public User(int userId, @NotEmpty String userFullname, String avatar, @NotEmpty String username) {
		super();
		this.userId = userId;
		this.userFullname = userFullname;
		this.avatar = avatar;
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Address getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public User(int userId, @NotEmpty String userFullname, @NotEmpty @Email String userEmail,
			@NotEmpty String userPhone, Address userAddress, String avatar, @NotEmpty String username, String password,
			Role role, int enabled, Timestamp createAt, Timestamp updateAt) {
		super();
		this.userId = userId;
		this.userFullname = userFullname;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.avatar = avatar;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}

}
