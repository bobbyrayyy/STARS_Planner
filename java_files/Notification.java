public interface Notification {
    public abstract void addNotification(Student student, Course course);
    public abstract void dropNotification(Course course, int indexDropped, Student student);
    public abstract void accessPeriodNotification(Student student);
}
