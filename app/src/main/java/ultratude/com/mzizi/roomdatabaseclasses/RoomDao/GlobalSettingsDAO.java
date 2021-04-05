package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;

/**
 * Created by James on 05/06/2019.
 */
@Dao
public interface GlobalSettingsDAO {
    @Query("SELECT * FROM GlobalSettings WHERE globalSettingName = :settingName AND StudID = :StudID")
    List<GlobalSettings> getGlobalSettings(String settingName,Integer StudID);

    @Insert
    long insertGlobalSettings(GlobalSettings globalSettings);

    @Query("DELETE FROM GlobalSettings WHERE globalSettingName = :settingName AND StudID = :StudID")
    int deleteGlobalSetting(String settingName, Integer StudID);

    @Query("DELETE FROM GlobalSettings WHERE StudID = :StudID")
    int deleteGlobalSettings(Integer StudID);

    @Query("DELETE FROM GlobalSettings")
    int deleteAllGlobalSettings();

}
