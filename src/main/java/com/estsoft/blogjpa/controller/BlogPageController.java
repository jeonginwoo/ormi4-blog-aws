package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.ArticleViewResponse;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogPageController {
    private final BlogService blogService;

    public BlogPageController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/articles")
    public String GetArticles(Model model) {
        List<ArticleViewResponse> articles = blogService.findAll().stream()
                .map(ArticleViewResponse::new)
                .toList();
        model.addAttribute("articles" /* html에서 사용하는 변수명 */, articles);

        return "articleList";   // articleList.html 조회
    }

    @GetMapping("/articles/{id}")
    public String showIdArticle(Model model, @PathVariable Long id) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(Model model, @RequestParam(required = false/*파라미터가 없어도 페이지 요청 가능*/) Long id) {
        if (id == null) {   // 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else {    // 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
