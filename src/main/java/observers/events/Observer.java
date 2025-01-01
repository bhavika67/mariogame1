package observers.events;

import jade.GameObject;

public interface Observer {
    void onNotify(GameObject object, Event event);
}
