package vn.shopttcn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Role {
	// 3 quyền: ADMIN, MOD (nhân viên), USER (khách hàng)
	private int roleId;
	private String roleName;
	private String roleDesc;

	public Role(int roleId) {
		super();
		this.roleId = roleId;
	}
	public Role() {
		// TODO Auto-generated constructor stub
	}
	public Role(int roleId, String roleName, String roleDesc) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	

}
