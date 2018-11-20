package org.projeto.gamelandia.simple.userpermission;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.projeto.gamelandia.simple.utils.BaseEntity;

@Entity
@Table(name = "tb_user_permission")
public class UserPermissionEntity extends BaseEntity<UserPermissionKey> {

	private static final long serialVersionUID = 201602010251L;

}