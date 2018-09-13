package com.group4.patientdoctorconsultation.data.model;

import com.google.firebase.firestore.Exclude;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.IndexedFirestoreResource;

import java.io.Serializable;

public class DataPacketItem extends IndexedFirestoreResource implements Serializable {

    public enum DataPacketItemType{
        HEART_RATE,
        DOCUMENT_REFERENCE,
        LOCATION,
        NOTE
    }

    private DataPacketItemType dataPacketItemType;
    private int iconResourceId;
    private String value;
    private String comment;
    private String displayValue;

    public DataPacketItem(DataPacketItemType dataPacketItemType, String value, String displayValue){
        this.displayValue = displayValue;
        this.dataPacketItemType = dataPacketItemType;
        this.value = value;
        setIconResourceId();
    }

    public DataPacketItem(DataPacketItemType dataPacketItemType, String value) {
        this(
            dataPacketItemType,
            value,
            dataPacketItemType == DataPacketItemType.DOCUMENT_REFERENCE ? null : value
        );
    }

    public DataPacketItem(){ //no args constructor for firebase
        this(
            DataPacketItemType.NOTE,
            ""
        );
    }

    /*
    * Firebase getters and setters
    */

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.displayValue = dataPacketItemType == DataPacketItemType.DOCUMENT_REFERENCE ? null : value;
    }

    public String getDataPacketItemTypeString(){
        return dataPacketItemType.toString();
    }

    public void setDataPacketItemTypeString(String dataPacketItemTypeString){
        setDataPacketItemType(DataPacketItemType.valueOf(dataPacketItemTypeString));
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*
     * Local getters and setters
     */

    @Exclude
    public void setDataPacketItemType(DataPacketItemType dataPacketItemType) {
        this.dataPacketItemType = dataPacketItemType;
        setIconResourceId();
    }

    @Exclude
    public DataPacketItemType getDataPacketItemType() {
        return dataPacketItemType;
    }

    @Exclude
    public int getIconResourceId() {
        return iconResourceId;
    }

    @Exclude
    private void setIconResourceId() {
        switch(dataPacketItemType){
            case HEART_RATE:
                iconResourceId = R.drawable.ic_favorite_black_24dp;
                break;
            case DOCUMENT_REFERENCE:
                iconResourceId = R.drawable.ic_attach_file_black_24dp;
                break;
            case LOCATION:
                iconResourceId = R.drawable.ic_location_on_black_24dp;
                break;
            case NOTE:
                iconResourceId = R.drawable.ic_text_fields_black_24dp;
                break;
        }
    }

    @Exclude
    public String getImageUrl(){
        return dataPacketItemType == DataPacketItemType.DOCUMENT_REFERENCE ? value : null;
    }

    @Exclude
    public String getDisplayValue() {
        return displayValue;
    }

    @Exclude
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
