package tech.mtright.habrpostcentral.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.mtright.habrpostcentral.model.Post;

@Service
@Log4j2
public class TelegramBotSenderService implements BotSenderService {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public void sendPostToBot(Post post) {
        String response = restTemplate.postForObject("http://TELEGRAMBOT/posts/newPost", post, String.class);
        if (response == null || !response.equals("ACCEPTED")) {
            log.error("Failed to send post " + post.getPostId());
        }
    }
}
