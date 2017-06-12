package com.example.nanchen.aiyaschoolpush.im;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.im
 * @date 2016/10/28  08:52
 */

public interface DataSyncListener {
    /**
     * sync complete
     * @param success true：data sync successful，false: failed to sync data
     */
    void onSyncComplete(boolean success);
}
