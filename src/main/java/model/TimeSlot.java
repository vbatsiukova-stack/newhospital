package model;

import java.util.Date;

public record TimeSlot(Date start, Date end) {
    public TimeSlot {
        if (start == null || end == null || !end.after(start)) {
            throw new IllegalArgumentException("Invalid model.TimeSlot: end must be after start");
        }
    }

    public boolean overlaps(TimeSlot other) {
        return start.before(other.end) && other.start.before(end);
    }
}
