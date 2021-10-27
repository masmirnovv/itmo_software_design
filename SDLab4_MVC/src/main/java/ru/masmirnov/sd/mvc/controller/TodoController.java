package ru.masmirnov.sd.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.masmirnov.sd.mvc.dao.TodoDao;
import ru.masmirnov.sd.mvc.model.TodoEntry;
import ru.masmirnov.sd.mvc.model.TodoFilter;
import ru.masmirnov.sd.mvc.model.TodoRemove;
import ru.masmirnov.sd.mvc.utils.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {

    private final TodoDao todoDao;

    public TodoController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @RequestMapping(value = "/show-all", method = RequestMethod.GET)
    public String showAllEntries(ModelMap map) {
        prepareModelMap(map, todoDao.getEntries());
        return "index";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showEntries(@ModelAttribute("todoFilter") TodoFilter todoFilter, ModelMap map) {
        List<TodoEntry> filtered = new ArrayList<>();
        Filter.get(todoFilter.getFilter()).ifPresent(
                filter -> filtered.addAll(filter.filter(todoDao, todoFilter.getTags()))
        );
        prepareModelMap(map, filtered);
        return "index";
    }

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public String putEntry(@ModelAttribute("todoEntry") TodoEntry todoEntry) {
        todoDao.putEntry(todoEntry);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeEntry(@ModelAttribute("todoRemove") TodoRemove todoRemove) {
        todoDao.removeEntry(todoRemove);
        return "redirect:/show-all";
    }

    private void prepareModelMap(ModelMap map, List<TodoEntry> entries) {
        map.addAttribute("entries", entries);
        map.addAttribute("todoFilter", new TodoFilter());
        map.addAttribute("todoEntry", new TodoEntry());
        map.addAttribute("todoRemove", new TodoRemove());
    }

}
