package com.mall.app.sqlbeen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/5/18.
 * 历史记录
 * @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 @Property：可以自定义字段名，注意外键不能使用该属性
 @NotNull：属性不能为空
 @Transient：使用该注释的属性不会被存入数据库的字段中
 @Unique：该属性值必须在数据库中是唯一值
 @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */

@Entity
public class History {

    @Id(autoincrement = true)
    public  Long id;
    public String searchText;
    public long searchTime;
    public int searchType;
    @Generated(hash = 1303323772)
    public History(Long id, String searchText, long searchTime, int searchType) {
        this.id = id;
        this.searchText = searchText;
        this.searchTime = searchTime;
        this.searchType = searchType;
    }
    @Generated(hash = 869423138)
    public History() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSearchText() {
        return this.searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    public long getSearchTime() {
        return this.searchTime;
    }
    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }
    public int getSearchType() {
        return this.searchType;
    }
    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }


}
