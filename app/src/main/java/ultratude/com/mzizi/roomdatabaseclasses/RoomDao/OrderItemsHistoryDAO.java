//package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//
//import java.util.List;
//
//import ultratude.com.mzizi.modelclasses.response.OrderItemsHistory;
//
//@Dao
//public interface OrderItemsHistoryDAO {
//
//    @Query("SELECT * FROM OrderItemsHistory")
//    List<OrderItemsHistory> getOrderItemsHistory();
//
//    @Insert
//    long[] insertOrderItemsHistory(List<OrderItemsHistory> response);
//
//    @Query("DELETE FROM OrderItemsHistory")
//    int deleteOrderItemsHistory();
//}
