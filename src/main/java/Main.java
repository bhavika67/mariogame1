import jade.Window;

public class Main {
    public static void main(String[] args) {
        Window window = Window.get();
        window.run();
        window.setWidth(500);
        window.setHeight(600);
    }
}