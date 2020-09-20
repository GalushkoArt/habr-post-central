package tech.mtright.habrpostcentral.services;

import tech.mtright.habrpostcentral.model.Post;

public interface BotSenderService {
    void sendPostToBot(Post post);
}
