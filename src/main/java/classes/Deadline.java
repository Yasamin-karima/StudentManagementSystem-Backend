package classes;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Deadline {
    Date date;
    Time time;
//    Time now = Time;

    public Deadline(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time  getTime() {
        return time;
    }


}
