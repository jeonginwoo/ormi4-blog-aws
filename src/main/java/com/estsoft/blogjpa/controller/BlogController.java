package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.ArticleRequest;
import com.estsoft.blogjpa.dto.ArticleResponse;
import com.estsoft.blogjpa.external.ExampleAPIClient;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody ArticleRequest request) {
        Article article = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article.toResponse());
    }

    @RequestMapping(value = "/api/articles", method = RequestMethod.GET)    // == @GetMapping
    public ResponseEntity<List<ArticleResponse>> showArticle() {
        List<Article> articleList = blogService.findAll();
        List<ArticleResponse> responseList = articleList.stream().map(ArticleResponse::new).toList();


        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> showIdArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);
        ArticleResponse response = new ArticleResponse(article);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteIdArticle(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/api/articles/{id}")
//    public ResponseEntity<ArticleResponse> updateIdArticle(@PathVariable Long id, @RequestBody AddArticleRequest request) {
//        Article article = blogService.update(id, request);
//        ArticleResponse response = new ArticleResponse(article);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateIdArticle(@PathVariable Long id, @RequestBody ArticleRequest request) {
        Article update = blogService.updateTitle(id, request);
        return ResponseEntity.ok(update);
    }
}
