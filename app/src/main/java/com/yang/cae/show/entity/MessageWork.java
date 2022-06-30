package com.yang.cae.show.entity;


import java.io.Serializable;


public class MessageWork implements Serializable {
    private static final long serialVersionUID =  1L;
    /**
     * 用户基本信息id
     */

    private String id;


    /**
     * 办事名称
     */

    private String workName;

    /**
     * 所需材料
     */

    private String materialsNeeded;

    /**
     * 部门
     */
    private String department;

    /**
     * 办事流程
     */

    private String process;
    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getMaterialsNeeded() {
        return materialsNeeded;
    }

    public void setMaterialsNeeded(String materialsNeeded) {
        this.materialsNeeded = materialsNeeded;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 信息地址
     */
    private String address;

    @Override
    public String toString() {
        return "MessageWork{" +
                "id='" + id + '\'' +
                ", workName='" + workName + '\'' +
                ", materialsNeeded='" + materialsNeeded + '\'' +
                ", department='" + department + '\'' +
                ", process='" + process + '\'' +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
