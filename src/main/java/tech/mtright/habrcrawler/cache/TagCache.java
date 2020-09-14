package tech.mtright.habrcrawler.cache;

import org.springframework.stereotype.Component;
import tech.mtright.habrcrawler.model.Tag;

import java.util.HashMap;
import java.util.Map;

@Component
public class TagCache {
    private final Map<String, Tag> tagMap = new HashMap<>();

    public Tag getTag(String tagName) {
        if (tagMap.containsKey(tagName)) {
            return tagMap.get(tagName);
        } else {
            Tag tag = Tag.builder().name(tagName).build();
            tagMap.put(tagName, tag);
            return tag;
        }
    }
}
