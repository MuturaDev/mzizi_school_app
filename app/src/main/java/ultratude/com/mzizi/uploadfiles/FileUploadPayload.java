package ultratude.com.mzizi.uploadfiles;

import java.util.Arrays;

import ultratude.com.mzizi.BuildConfig;

public class FileUploadPayload {


    private String FileName;
    private String StudentID;
    private String TeacherMemoID;
    private String FileExtension;
    private String Comment;
    private String AppVersion;


    public FileUploadPayload(String fileName, String studentID, String teacherMemoID, String fileExtension, String comment) {
        FileName = fileName;
        StudentID = studentID;
        TeacherMemoID = teacherMemoID;
        FileExtension = fileExtension;
        Comment = comment;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getFileName() {
        return FileName;
    }


    public String getStudentID() {
        return StudentID;
    }

    public String getTeacherMemoID() {
        return TeacherMemoID;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public String getComment() {
        return Comment;
    }

    @Override
    public String toString() {
        return "FileUploadPayload{" +
                "FileName='" + FileName + '\'' +
                ", StudentID='" + StudentID + '\'' +
                ", TeacherMemoID='" + TeacherMemoID + '\'' +
                ", FileExtension='" + FileExtension + '\'' +
                ", Comment='" + Comment + '\'' +
                '}';
    }
}
