package ru.itis.rssnews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.rssnews.dto.ArticlesPage;
import ru.itis.rssnews.services.ArticlesService;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ArticlesService articlesService;

    @GetMapping("/")
    public String getMainPage(@RequestParam(value = "page", required = false) String page, ModelMap modelMap) {
        int intPage;
        try {
            intPage = Integer.parseInt(page);
        } catch (NumberFormatException ex) {
            intPage = 1;
        }

        ArticlesPage articlesPage = articlesService.getAll(intPage);
        modelMap.put("articles", articlesPage.getArticles());
        modelMap.put("pagesCount", articlesPage.getTotalPagesCount());
        modelMap.put("page", intPage);
        return "index";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errMsg", "Wrong password or username");
        }
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
