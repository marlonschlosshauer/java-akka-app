import example_3.WebSocketStreamExample;

public class QueueApp {
    public static void main(String[] args) {
        try {
            WebSocketStreamExample.run();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
