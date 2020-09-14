package tech.mtright.habrcrawler.cache;

import org.springframework.stereotype.Component;
import tech.mtright.habrcrawler.model.Hub;

import java.util.HashMap;
import java.util.Map;

@Component
public class HubCache {
    private final Map<String, Hub> hubMap = new HashMap<>();

    public Hub getHub(String hubName) {
        if (hubMap.containsKey(hubName)) {
            return hubMap.get(hubName);
        } else {
            Hub hub = Hub.builder().name(hubName).build();
            hubMap.put(hubName, hub);
            return hub;
        }
    }
}
