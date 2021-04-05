package ultratude.com.mzizi.roomdatabaseclasses.Util;



import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by James on 25/06/2018.
 */

public class DateRoomConverter {

    @TypeConverter
    public static Date toDate(Long value){

        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value){
        return value == null ? null : value.getTime();
    }
}
