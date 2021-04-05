package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.modelclasses.response.OrderItemResponse;

@Dao
public interface OrderItemResponseDAO {

    @Query("SELECT * FROM OrderItemResponse WHERE StudID = :StudID")
    List<OrderItemResponse> getOrderItemResponse(Integer StudID);

    @Insert
    long[] insertOrderItemResponse(List<OrderItemResponse> response);

    @Query("DELETE FROM OrderItemResponse WHERE StudID = :StudID")
    int deleteOrderItemResponse(Integer StudID);

    @Query("DELETE FROM OrderItemResponse")
    int deleteAllOrderItemResponse();
}
