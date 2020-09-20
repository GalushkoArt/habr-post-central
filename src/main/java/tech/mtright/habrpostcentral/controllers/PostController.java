package tech.mtright.habrpostcentral.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.mtright.habrpostcentral.model.Post;
import tech.mtright.habrpostcentral.services.PostSearchService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostSearchService postSearchService;

    @GetMapping(value = "/getPost", produces = "application/json;charset=UTF-8")
    public Optional<Post> getPost(@RequestParam int id) {
        return postSearchService.searchPostById(id);
    }
}
