package com.sizaif.emsdemo.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class PermissionVO implements Serializable{
	private static final long serialVersionUID = -2783081162690878303L;
	private Integer id;

	private String title;

    private Integer pid;

	private Integer istype;

	private String code;

	private String page;

	private boolean checked;

	private boolean open;

    private List<PermissionVO> children = new ArrayList<PermissionVO>();


	public List<PermissionVO> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionVO> children) {
		this.children = children;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getIstype() {
        return istype;
    }

    public void setIstype(Integer istype) {
        this.istype = istype;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getOpen() {
		return true;
	}

	/*public void setOpen(boolean open) {
		this.open = open;
	}*/

    @Override
    public String toString() {
        return "PermissionVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", istype=" + istype +
                ", code='" + code + '\'' +
                ", page='" + page + '\'' +
                ", checked=" + checked +
                ", open=" + open +
                ", children=" + children +
                '}';
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}