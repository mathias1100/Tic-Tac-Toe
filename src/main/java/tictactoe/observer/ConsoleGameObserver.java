public class ConsoleGameObserver implements GameObserver {

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}