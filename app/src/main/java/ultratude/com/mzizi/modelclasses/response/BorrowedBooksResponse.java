package ultratude.com.mzizi.modelclasses.response;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class BorrowedBooksResponse {

    @PrimaryKey
    @NonNull
    @SerializedName("BookNo")
    private String BookNo;
    @SerializedName("BookName")
    private String BookName;
    @SerializedName("CategoryName")
    private String CategoryName;
    @SerializedName("PublisherName")
    private String PublisherName;
    @SerializedName("TypeName")
    private String TypeName;
    @SerializedName("UnitName")
    private String UnitName;
    @SerializedName("DateBorrowed")
    private String DateBorrowed;
    @SerializedName("DateReturned")
    private String DateReturned;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public BorrowedBooksResponse(){}

    @Ignore
    public BorrowedBooksResponse(String bookNo, String bookName, String categoryName, String publisherName, String typeName, String unitName, String dateBorrowed, String dateReturned) {
        BookNo = bookNo;
        BookName = bookName;
        CategoryName = categoryName;
        PublisherName = publisherName;
        TypeName = typeName;
        UnitName = unitName;
        DateBorrowed = dateBorrowed;
        DateReturned = dateReturned;
    }

    public String getBookNo() {
        return BookNo;
    }

    public String getBookName() {
        return BookName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getPublisherName() {
        return PublisherName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public String getUnitName() {
        return UnitName;
    }

    public String getDateBorrowed() {
        return DateBorrowed;
    }

    public String getDateReturned() {
        return DateReturned;
    }

    @Override
    public String toString() {
        return "BorrowedBooksResponseDAO{" +
                "BookNo='" + BookNo + '\'' +
                ", BookName='" + BookName + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", PublisherName='" + PublisherName + '\'' +
                ", TypeName='" + TypeName + '\'' +
                ", UnitName='" + UnitName + '\'' +
                ", DateBorrowed='" + DateBorrowed + '\'' +
                ", DateReturned='" + DateReturned + '\'' +
                '}';
    }


    public void setBookNo(String bookNo) {
        BookNo = bookNo;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setPublisherName(String publisherName) {
        PublisherName = publisherName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public void setDateBorrowed(String dateBorrowed) {
        DateBorrowed = dateBorrowed;
    }

    public void setDateReturned(String dateReturned) {
        DateReturned = dateReturned;
    }
}
