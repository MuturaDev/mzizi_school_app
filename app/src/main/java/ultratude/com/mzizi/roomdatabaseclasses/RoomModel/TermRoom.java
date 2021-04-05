package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.room.ColumnInfo;

/**
 * Created by James on 29/05/2019.
 */

public class TermRoom {
    @ColumnInfo(name="TermFor")
    private String term;

    @Override
    public String toString() {
        return "TermRoom{" +
                "term='" + term + '\'' +
                '}';
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
