package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TermRoom;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.YearRoom;


/**
 * Created by James on 29/05/2019.
 */
@Dao
public interface StudentClassAttendanceDAO {

    @Query("SELECT * FROM StudentClassAttendance WHERE  YearFor = :year AND TermFor = :term AND StudID = :StudID")
    List<StudentClassAttendance> getStudentClassAttendance(String year, String term, Integer StudID);

    @Query("SELECT * FROM StudentClassAttendance WHERE StudID = :StudID")
    List<StudentClassAttendance> getStudentClasAttendanceListForPercentage(Integer StudID);


    @Insert
    long[] insertStudentClassAttendance(List<StudentClassAttendance> studentClassAttendanceList);

    @Query("DELETE FROM StudentClassAttendance WHERE StudID = :StudID")
    int deleteStudentClassAttendance(Integer StudID);

    @Query("SELECT DISTINCT TermFor FROM StudentClassAttendance WHERE StudID = :StudID")
    List<TermRoom> getTermForList(Integer StudID);

    @Query("SELECT DISTINCT YearFor FROM StudentClassAttendance WHERE StudID = :StudID")
    List<YearRoom> getYearForList(Integer StudID);

    @Query("DELETE FROM StudentClassAttendance")
    int deleteAllStudentClassAttendance();
}
