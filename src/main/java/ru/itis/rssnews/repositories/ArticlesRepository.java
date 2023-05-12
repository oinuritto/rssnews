package ru.itis.rssnews.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.rssnews.models.Article;

import java.util.Optional;

@Repository
@Transactional
public interface ArticlesRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByLink(String link);
    Page<Article> findAllByOrderByIdDesc(Pageable pageable);
    Page<Article> findAllByCategoryName(String categoryName, Pageable pageable);
    @Query("SELECT a FROM Article a LEFT JOIN a.likes l GROUP BY a.id ORDER BY SIZE(l) DESC")
    Page<Article> findAllOrderByLikesDesc(Pageable pageable);
    void deleteBySourceId(Long id);
}
