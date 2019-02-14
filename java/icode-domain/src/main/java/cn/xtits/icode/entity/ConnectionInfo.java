package cn.xtits.icode.entity;

import java.io.Serializable;
import java.util.Date;

public class ConnectionInfo implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.Id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.host
     *
     * @mbg.generated
     */
    private String host;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.user
     *
     * @mbg.generated
     */
    private String user;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.MakeBillMan
     *
     * @mbg.generated
     */
    private String makeBillMan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.CreateDate
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.ModifyDate
     *
     * @mbg.generated
     */
    private Date modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.Modifier
     *
     * @mbg.generated
     */
    private String modifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column connectioninfo.DeleteFlag
     *
     * @mbg.generated
     */
    private Boolean deleteFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table connectioninfo
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.Id
     *
     * @return the value of connectioninfo.Id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.Id
     *
     * @param id the value for connectioninfo.Id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.host
     *
     * @return the value of connectioninfo.host
     *
     * @mbg.generated
     */
    public String getHost() {
        return host;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.host
     *
     * @param host the value for connectioninfo.host
     *
     * @mbg.generated
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.user
     *
     * @return the value of connectioninfo.user
     *
     * @mbg.generated
     */
    public String getUser() {
        return user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.user
     *
     * @param user the value for connectioninfo.user
     *
     * @mbg.generated
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.password
     *
     * @return the value of connectioninfo.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.password
     *
     * @param password the value for connectioninfo.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.MakeBillMan
     *
     * @return the value of connectioninfo.MakeBillMan
     *
     * @mbg.generated
     */
    public String getMakeBillMan() {
        return makeBillMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.MakeBillMan
     *
     * @param makeBillMan the value for connectioninfo.MakeBillMan
     *
     * @mbg.generated
     */
    public void setMakeBillMan(String makeBillMan) {
        this.makeBillMan = makeBillMan == null ? null : makeBillMan.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.CreateDate
     *
     * @return the value of connectioninfo.CreateDate
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.CreateDate
     *
     * @param createDate the value for connectioninfo.CreateDate
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.ModifyDate
     *
     * @return the value of connectioninfo.ModifyDate
     *
     * @mbg.generated
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.ModifyDate
     *
     * @param modifyDate the value for connectioninfo.ModifyDate
     *
     * @mbg.generated
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.Modifier
     *
     * @return the value of connectioninfo.Modifier
     *
     * @mbg.generated
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.Modifier
     *
     * @param modifier the value for connectioninfo.Modifier
     *
     * @mbg.generated
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column connectioninfo.DeleteFlag
     *
     * @return the value of connectioninfo.DeleteFlag
     *
     * @mbg.generated
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column connectioninfo.DeleteFlag
     *
     * @param deleteFlag the value for connectioninfo.DeleteFlag
     *
     * @mbg.generated
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}