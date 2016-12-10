package com.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_personal")          //创建的表名
public class PersonanlBean {
    @DatabaseField(generatedId = true)             //id为主键且自动生成
    private int id;
    @DatabaseField(columnName = "personal_name")   //列名为persinal_name
    private String name;
    @DatabaseField(columnName = "personal_desc")
    private String desc;

    public PersonanlBean() {

    }

    public PersonanlBean(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    //利用快捷键快速生成setter、getter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
