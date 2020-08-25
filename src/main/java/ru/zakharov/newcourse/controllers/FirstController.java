package ru.zakharov.newcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.zakharov.newcourse.Role;
import ru.zakharov.newcourse.domains.User;
import ru.zakharov.newcourse.domains.Words;
import ru.zakharov.newcourse.repos.DictionaryRepo;
import ru.zakharov.newcourse.repos.UserRepo;
import ru.zakharov.newcourse.repos.WordsRepo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FirstController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WordsRepo wordsRepo;

    @Autowired
    private EntityManager entityManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "default";
    }

    @GetMapping("/homepage")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepo.findUserById(user.getId());
        System.out.println("=================================================================================");
        System.out.println(currentUser);
        System.out.println("Roles: "+currentUser.getRoles());
        System.out.println("Dictionaries size: "+currentUser.getDictionaries().size());
        if (currentUser.getDictionaries().size()>0) {
            System.out.println("Description:");
            currentUser.getDictionaries().forEach(s-> System.out.println(s.getDescription().getDescription()));
        }
        System.out.println("=================================================================================");
        List<User> allUsers = userRepo.findAll().stream()
                .sorted(Comparator.comparing(User::countOfAllWords, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Query query = entityManager
                .createNativeQuery("SELECT word FROM words GROUP BY word ORDER BY COUNT (*) DESC LIMIT 3");
        List resultOfQuery = query.getResultList();
        Map<String, BigInteger> top3Words = new LinkedHashMap<>();
        Query q = entityManager.createNativeQuery("SELECT count(*) FROM words WHERE word = ? GROUP BY word");
        for (int i = 0; i < resultOfQuery.size(); i++) {
            q.setParameter(1, resultOfQuery.get(i));
            top3Words.put((String) resultOfQuery.get(i), (BigInteger) q.getSingleResult());
        }
        top3Words.entrySet()
                .forEach(pair-> System.out.println(pair.getKey()+" - "+pair.getValue()));
        System.out.println(top3Words);
        if (resultOfQuery!=null) {
            model.addAttribute("top3words", top3Words);
        }
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("currentUser", currentUser);
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("GET");
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute("newUser") User user,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User userFromDB = userRepo.findUserByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("errorMsg", "Это имя занято!");
            return "registration";
        }
        else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
            return "login";
        }

    }
}
