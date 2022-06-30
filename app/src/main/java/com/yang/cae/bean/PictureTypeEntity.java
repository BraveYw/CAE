package com.yang.cae.bean;



public class PictureTypeEntity
//        implements CircleItemLabel
{
    public String id;

    public String typeName;


    public PictureTypeEntity() {
    }

    public PictureTypeEntity(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

//    @Override
//    public String getItemLabel() {
//        return typeName;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
