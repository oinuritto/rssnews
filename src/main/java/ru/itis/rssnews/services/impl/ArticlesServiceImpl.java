package ru.itis.rssnews.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.rssnews.dto.ArticlesPage;
import ru.itis.rssnews.exceptions.NotFoundException;
import ru.itis.rssnews.models.Article;
import ru.itis.rssnews.repositories.ArticlesRepository;
import ru.itis.rssnews.services.ArticlesService;

@Service
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    @Value("${articles.default.page-size}")
    private int defaultPageSize;


    @Override
    public Article getByLink(String link) {
        return getArticleOrElseThrow(link);
    }


    @Override
    public ArticlesPage getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, defaultPageSize);
        Page<Article> articlesPage = articlesRepository.findAllByOrderByIdAsc(pageRequest);

        return ArticlesPage.builder()
                .articles(articlesPage.getContent())
                .totalPagesCount(articlesPage.getTotalPages())
                .build();
    }

    @Override
    public void addArticle(Article article) {
        articlesRepository.save(article);
    }

    private Article getArticleOrElseThrow(String link) {
        return articlesRepository.findByLink(link)
                .orElseThrow(() -> new NotFoundException("Article with link = <" + link + "> is not found"));
    }
}
