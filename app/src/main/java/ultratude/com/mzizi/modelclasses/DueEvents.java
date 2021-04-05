package ultratude.com.mzizi.modelclasses;

public class DueEvents {

    private String dueDate;
    private String dueTitle;


    public DueEvents(String dueDate, String dueTitle) {
        this.dueDate = dueDate;
        this.dueTitle = dueTitle;

    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTitle() {
        return dueTitle;
    }


    @Override
    public String toString() {
        return "DueEvents{" +
                "dueDate='" + dueDate + '\'' +
                ", dueTitle='" + dueTitle + '\'' +
                '}';
    }
}
