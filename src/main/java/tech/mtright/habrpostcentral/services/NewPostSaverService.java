package tech.mtright.habrpostcentral.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.cache.DailyPostCache;
import tech.mtright.habrpostcentral.model.Post;
import tech.mtright.habrpostcentral.repository.PostRepository;

@Service
public class NewPostSaverService implements PostSaverService {
    @Autowired
    private DailyPostCache postCache;
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
        postCache.put(post);
    }
}
