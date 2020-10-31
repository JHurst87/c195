package Exception;

public class AppointmentOverlappingException extends AppointmentTimeException{
    public AppointmentOverlappingException(String msg){
        super(msg);
    }
}
