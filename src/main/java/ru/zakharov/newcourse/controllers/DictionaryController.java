package ru.zakharov.newcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zakharov.newcourse.domains.Description;
import ru.zakharov.newcourse.domains.Dictionary;
import ru.zakharov.newcourse.domains.User;
import ru.zakharov.newcourse.domains.Words;
import ru.zakharov.newcourse.repos.*;

import javax.persistence.CascadeType;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/dict")
public class DictionaryController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DictionaryRepo dictionaryRepo;

    @Autowired
    private DescriptionRepo descriptionRepo;

    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private WordsRepo wordsRepo;

    //Methods above were copied from FirstController
    //You can edit it if you need!

    @GetMapping("/dictionary")
    public String toDictionary(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepo.findUserById(user.getId());
        model.addAttribute("allDictionaries", currentUser.getDictionaries());
        return "dictionary";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepo.findUserById(user.getId());
        return "addform";
    }

    @PostMapping("/add")
    public String addDictionary(@AuthenticationPrincipal User user,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "description") String description) {
        User currentUser = userRepo.findUserById(user.getId());
        Dictionary dictionary = new Dictionary(name);
        Description description1 = new Description(description);
        dictionary.setDescription(description1);
        description1.setDictionary(dictionary);
        currentUser.addDictionary(dictionary);
        dictionaryRepo.save(dictionary);
        descriptionRepo.save(description1);
        return "redirect:/homepage";
    }

    @GetMapping("/open/{id}")
    public String openDictionary(@PathVariable int id, Model model) {
        Dictionary dictionary = dictionaryRepo.findById(id).get();
        List<Words> dictionaryWords = dictionary.getWords();
        model.addAttribute("currentDictionary", dictionary);
        model.addAttribute("owner", true);
        return "dictionary";
    }

    @GetMapping("/open/foreign/{id}")
    public String openForeignDictionary(@PathVariable int id, Model model,
                                        @AuthenticationPrincipal User user) {
        Dictionary dictionary = dictionaryRepo.findById(id).get();
        List<Words> dictionaryWords = dictionary.getWords();
        User currentUser = userRepo.findUserById(user.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("owner", false);
        model.addAttribute("currentDictionary", dictionary);
        return "dictionary";
    }

    @GetMapping("/add-word/{id}")
    public String addWordGet(@PathVariable int id, Model model) {
        Dictionary currentDictionary = dictionaryRepo.findById(id).get();
        List<Words> dictionaryWords = currentDictionary.getWords();
        model.addAttribute("currentDictionary", currentDictionary);
        return "newword";
    }

    @PostMapping("/add-word/{id}")
    public String addWordPost(@PathVariable int id,
                              @RequestParam("word") String word,
                              @RequestParam("translate") String translate) {
        Dictionary dictionary = dictionaryRepo.findById(id).get();
        dictionary.getWords();
        Words newWord = new Words(word, translate);
        dictionary.addWord(newWord);
        wordsRepo.save(newWord);
        dictionary.setUpdateDate(Calendar.getInstance());
        dictionaryRepo.save(dictionary);
        return "redirect:/dict/open/{id}";
    }

    @GetMapping("/edit/{id}")
    public String editDictionaryGet(@PathVariable int id, Model model) {
        Dictionary dictionary = dictionaryRepo.findById(id).get();
        model.addAttribute("currentDictionary", dictionary);
        return "editdictionary";
    }

    @PostMapping("/edit/{id}")
    public String editDictionaryName(@PathVariable int id,
                                     @RequestParam("name") String name,
                                     @RequestParam("description") String description) {
        Dictionary dictionary = dictionaryRepo.findById(id).get();
        if (name != null && !name.isEmpty()) {
            dictionary.setDictionaryName(name);
        }
        if (description != null && !description.isEmpty()) {
            dictionary.addDescription(new Description(description));
        }
        dictionary.setUpdateDate(Calendar.getInstance());
        dictionaryRepo.save(dictionary);
        return "redirect:/homepage";
    }

    @GetMapping("/del/{id}")
    public String deleteDictionary(@PathVariable int id) {
        Dictionary currentDictionary = dictionaryRepo.findById(id).get();
        dictionaryRepo.delete(currentDictionary);
        return "redirect:/homepage";
    }

    @GetMapping("/show/{id}")
    public String showUserDictionary(@PathVariable int id, Model model) {
        User user = userRepo.findUserById(id);
        List<Dictionary> dictionaries = user.getDictionaries();
        model.addAttribute("owner", user);
        model.addAttribute("dictionaries", dictionaries);
        return "usersdictionaries";
    }

}
