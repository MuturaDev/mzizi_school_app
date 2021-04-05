package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.room.ColumnInfo;

/**
 * Created by James on 29/05/2019.
 */

public class YearRoom {
    @ColumnInfo(name="YearFor")
    private String year;

    @Override
    public String toString() {
        return "YearRoom{" +
                "year='" + year + '\'' +
                '}';
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
