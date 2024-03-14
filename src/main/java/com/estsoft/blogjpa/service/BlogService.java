package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.dto.ArticleRequest;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Article save(ArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id" + id));
        // return blogRepository.findById(id).orElse(new Article());
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional  // 실제 쿼리로 보낸다.
    public Article update(Long id, ArticleRequest request) {
        Article article = findById(id);
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    @Transactional
    public Article updateTitle(Long id, ArticleRequest request) {
        Article article = findById(id);
        blogRepository.updateTitle(id, request.getTitle(), request.getContent());
        return article;
    }
}
