package com.example.goldbarlift.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface InternalStorageManager {

    public void safeObject(int distance, boolean notification) throws IOException;

    public void restoreSettings() throws IOException, FailedToRestoreFromInternalStorageException;

    public boolean getNotificationSetting();

    public int getDistanceSetting();

}
