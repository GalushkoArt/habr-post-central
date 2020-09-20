package tech.mtright.habrpostcentral.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.mtright.habrpostcentral.model.Post;

import java.util.List;
import java.util.Optional;

@Service
public class HabrPostSearchService implements PostSearchService {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public Optional<Post> searchPostById(int id) {
        Post post = restTemplate.getForObject("http://POSTPARSER/api/getPost?id={id}", Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> searchPostWithIdsBetween(int start, int end) {
        String uri = UriComponentsBuilder.fromHttpUrl("http://POSTPARSER/api/getPosts")
                .queryParam("start", start).queryParam("end", end).build().toString();
        ResponseEntity<List<Post>> posts = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Post>>() {});
        return posts.getBody();
    }

    @Override
    public List<Post> searchPostsWithIds(List<Integer> ids) {
        String uri = UriComponentsBuilder.fromHttpUrl("http://POSTPARSER/api/getPostsById")
                .queryParam("ids", ids).build().toString();
        ResponseEntity<List<Post>> posts = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Post>>() {});
        return posts.getBody();
    }
}
