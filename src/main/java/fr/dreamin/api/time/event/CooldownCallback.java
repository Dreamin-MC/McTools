package fr.dreamin.api.time.event;

public interface CooldownCallback {
    void onCooldownEnd(Object key);
}