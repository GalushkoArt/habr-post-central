package tech.mtright.habrpostcentral.cache.cache_loaders;

import com.google.common.cache.CacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.model.Tag;
import tech.mtright.habrpostcentral.repository.TagRepository;

import java.util.Optional;

@Component
public class TagCacheLoader extends CacheLoader<String, Tag> {
    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag load(String s) throws Exception {
        Optional<Tag> tag = tagRepository.getByName(s);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            Tag newTag = Tag.builder().name(s).build();
            tagRepository.save(newTag);
            return newTag;
        }
    }
}
