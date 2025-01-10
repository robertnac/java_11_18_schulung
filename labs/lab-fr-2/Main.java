import jdk.jfr.*;

public class Main {
    public static void main(String[] args) {

        SimpleApplicationStartedEvent event = new SimpleApplicationStartedEvent();
        event.begin();
        event.message = "I'm an event!";
        event.commit();

        System.out.println("Hello World!");
    }
}

@Name(SimpleApplicationStartedEvent.NAME)
@Label("application started")
@Category("Trion")
@Description("application started")
@StackTrace(false)
class SimpleApplicationStartedEvent extends Event {
    static final String NAME = "Main";
    @Label("Message")
    String message;
}
