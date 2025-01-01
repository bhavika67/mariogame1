package editor;

import imgui.ImGui;
import observers.ObserverHandler;
import observers.events.Event;
import observers.events.EventType;

public class MenuBar {

    public void imgui() {
        ImGui.beginMainMenuBar();

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save")) {
                ObserverHandler.notify(null, new Event(EventType.SaveLevel));
            } else if (ImGui.menuItem("Load Level")) {
                ObserverHandler.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Settings")) {
            if (ImGui.menuItem("Toggle Physics Debug Draw")) {
                ObserverHandler.notify(null, new Event(EventType.TogglePhysicsDebugDraw));
            }
            ImGui.endMenu();
        }

        ImGui.endMainMenuBar();
    }
}