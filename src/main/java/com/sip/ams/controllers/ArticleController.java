package com.sip.ams.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.Article;
import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ArticleRepository;
import com.sip.ams.repositories.ProviderRepository;

@RestController
@CrossOrigin(origins="*")
@RequestMapping({"/articles"})
public class ArticleController {
	
	private final ArticleRepository articleRepository;
	private final ProviderRepository providerRepository;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";
    
	
	@Autowired //Injection des deux dépendances articleRepository et providerRepositorys
    public ArticleController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
        this.articleRepository = articleRepository;
        this.providerRepository = providerRepository;
    }
    
    @GetMapping("list")
    public List<Article> listProviders() {
        return (List<Article>) articleRepository.findAll();
    }
    
    
    @PostMapping("add/{providerId}")
    public Article addArticle(@PathVariable (value="providerId") Long providerId, @Valid @RequestBody Article article) {
    	//C'est quoi optional?
    	return providerRepository.findById(providerId).map(provider -> {
    		article.setProvider(provider);
       	 return  articleRepository.save(article);
    	}).orElseThrow(()-> new IllegalArgumentException("Invalid provider Id : " +providerId ));
    
    }
    
    @DeleteMapping("delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") long articleId) {
        return articleRepository.findById(articleId).map( article -> {
        	articleRepository.delete(article);
        	return ResponseEntity.ok().build();
        }).orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + articleId));
    }
    
 
    @PutMapping("update/{providerId}/{articleId}")
    public Article updateArticle( @PathVariable(value ="providerId") Long providerId,@PathVariable(value ="articleId") Long articleId, @Valid @RequestBody Article articleRequest) {
         if( !providerRepository.existsById(providerId)) {
        	throw new IllegalArgumentException("Invalid provider Id:" + providerId);
         }
         
         return articleRepository.findById(articleId).map( article -> {
         	article.setLabel(articleRequest.getLabel());
         	article.setPrice(articleRequest.getPrice());
         	article.setPicture(articleRequest.getPicture());
         	//article.setProvider(articleRequest.getProvider());
         	return articleRepository.save(article);
         	 }).orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + articleId));
     }
    

}
