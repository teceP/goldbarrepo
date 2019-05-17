package com.example.goldbarlift.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalStorageManagerImpl implements InternalStorageManager {

    //Mementofile
    private File storage;
    private Integer distance;
    private Boolean notification;

    //MEMENTO PATTERN FOR INTERNAL STORAGE
    public InternalStorageManagerImpl(File storage){
        this.storage = storage;
    }


    @Override
    public void safeObject(int distance, boolean notification) throws IOException {
        FileOutputStream fos = new FileOutputStream(this.storage);
        DataOutputStream dos = new DataOutputStream(fos);

        //Per definition: 1:distance, 2.notification
        String content = distance+"~~"+notification;

        dos.writeUTF(content);
        dos.close();
    }

    @Override
    public void restoreSettings() throws IOException, FailedToRestoreFromInternalStorageException {

        FileInputStream fis = new FileInputStream(this.storage);
        DataInputStream dis = new DataInputStream(fis);

        String content = dis.readUTF();
        dis.close();
        String[] split = this.splitObjects(content);

        this.distance = Integer.parseInt(split[0]);

        if(split[1].equals("true")){
            this.notification = true;
        }else{
            this.notification = false;
        }

        if(this.notification == null || this.distance == null){
            throw new FailedToRestoreFromInternalStorageException(content);
        }

    }

    @Override
    public boolean getNotificationSetting() {
        return this.notification;
    }

    @Override
    public int getDistanceSetting() {
        return this.distance;
    }

    private String[] splitObjects(String content){

        String[] splitted = content.split("\\~~");

        return splitted;
    }


}
