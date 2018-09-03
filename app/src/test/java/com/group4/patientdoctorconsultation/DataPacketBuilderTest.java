package com.group4.patientdoctorconsultation;

import com.group4.patientdoctorconsultation.data.model.DataPacket;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;
import com.group4.patientdoctorconsultation.utilities.DataPacketItemManager;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.group4.patientdoctorconsultation.data.model.DataPacketItem.DataPacketItemType.COMMENT;
import static com.group4.patientdoctorconsultation.data.model.DataPacketItem.DataPacketItemType.DOCUMENT_REFERENCE;
import static com.group4.patientdoctorconsultation.data.model.DataPacketItem.DataPacketItemType.HEART_RATE;
import static com.group4.patientdoctorconsultation.data.model.DataPacketItem.DataPacketItemType.LOCATION;
import static com.group4.patientdoctorconsultation.data.model.DataPacketItem.DataPacketItemType.NOTE;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class DataPacketBuilderTest {

    private static final String VALUE_HEART_RATE = "20 BPM";
    private static final String VALUE_DOCUMENT_REFERENCE_1 = "http://test.com/1";
    private static final String VALUE_DOCUMENT_REFERENCE_2 = "http://test.com/2";
    private static final String VALUE_LOCATION = "http://www.maps.google.com/location";
    private static final String VALUE_COMMENT_1 = "Comment 1";
    private static final String VALUE_COMMENT_2 = "Comment 2";
    private static final String VALUE_COMMENT_3 = "Comment 3";
    private static final String VALUE_NOTE_1 = "Note 1";
    private static final String VALUE_NOTE_2 = "Note 2";
    private static final String VALUE_NOTE_3 = "Note 3";


    private List<DataPacketItem> dataPacketItems;
    private DataPacket dataPacket;

    @Before
    public void buildUp(){
        dataPacket = new DataPacket();
        dataPacketItems = new ArrayList<>();
        dataPacketItems.add(new DataPacketItem(HEART_RATE, VALUE_HEART_RATE));
        dataPacketItems.add(new DataPacketItem(DOCUMENT_REFERENCE, VALUE_DOCUMENT_REFERENCE_1));
        dataPacketItems.add(new DataPacketItem(DOCUMENT_REFERENCE, VALUE_DOCUMENT_REFERENCE_2));
        dataPacketItems.add(new DataPacketItem(LOCATION, VALUE_LOCATION));
        dataPacketItems.add(new DataPacketItem(COMMENT, VALUE_COMMENT_1));
        dataPacketItems.add(new DataPacketItem(COMMENT, VALUE_COMMENT_2));
        dataPacketItems.add(new DataPacketItem(COMMENT, VALUE_COMMENT_3));
        dataPacketItems.add(new DataPacketItem(NOTE, VALUE_NOTE_1));
        dataPacketItems.add(new DataPacketItem(NOTE, VALUE_NOTE_2));
        dataPacketItems.add(new DataPacketItem(NOTE, VALUE_NOTE_3));
    }

    @Test
    public void buildUpDataPacket(){
        dataPacket.setHeartRate(VALUE_HEART_RATE);
        dataPacket.setDocumentReferences(Arrays.asList(VALUE_DOCUMENT_REFERENCE_1, VALUE_DOCUMENT_REFERENCE_2));
        dataPacket.setLocations(Collections.singletonList(VALUE_LOCATION));
        dataPacket.setComments(Arrays.asList(VALUE_COMMENT_1, VALUE_COMMENT_2, VALUE_COMMENT_3));
        dataPacket.setNotes(Arrays.asList(VALUE_NOTE_1, VALUE_NOTE_2, VALUE_NOTE_3));

        DataPacket testPacket = DataPacketItemManager.buildDataPacket(new DataPacket(), dataPacketItems);

        assertEquals(dataPacket.getHeartRate(), testPacket.getHeartRate());

        for(String documentReference: testPacket.getDocumentReferences()){
            assertTrue(dataPacket.getDocumentReferences().contains(documentReference));
        }

        for(String location: testPacket.getLocations()){
            assertTrue(dataPacket.getLocations().contains(location));
        }

        for(String comment: testPacket.getComments()){
            assertTrue(dataPacket.getComments().contains(comment));
        }

        for(String note: testPacket.getNotes()){
            assertTrue(dataPacket.getNotes().contains(note));
        }
    }

    @Test
    public void breakDownDataPacket(){
        dataPacket = DataPacketItemManager.buildDataPacket(new DataPacket(), dataPacketItems);
        assertEquals(
                dataPacketItems.size(),
                DataPacketItemManager.breakDownDataPacket(dataPacket).size()
        );
    }

}
