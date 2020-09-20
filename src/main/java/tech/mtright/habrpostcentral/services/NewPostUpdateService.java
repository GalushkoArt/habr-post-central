package tech.mtright.habrpostcentral.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.mtright.habrpostcentral.model.Post;

import java.util.Optional;

@Service
@Log4j2
public class NewPostUpdateService implements PostUpdateService {
    @Autowired
    private SiteSearchService siteSearchService;
    @Autowired
    private PostSearchService postSearchService;
    @Autowired
    private BotSenderService senderService;
    @Autowired
    private PostSaverService saverService;
    private int lastPost;

    @Override
    @Scheduled(fixedDelay = 60 * 1000)
    public void checkUpdate() {
        int lastPostOnSite = siteSearchService.findLastPostOnSite();
        if (lastPostOnSite > lastPost) {
            log.info("Found new post with postId: " + lastPostOnSite);
            Optional<Post> post = postSearchService.searchPostById(lastPostOnSite);
            post.ifPresent(senderService::sendPostToBot);
            post.ifPresent(saverService::savePost);
            lastPost = lastPostOnSite;
        }
    }
}
