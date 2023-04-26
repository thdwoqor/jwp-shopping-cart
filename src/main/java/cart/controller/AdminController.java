package cart.controller;

import cart.dto.ItemRequest;
import cart.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ItemService itemService;

    public AdminController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String displayItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "admin";
    }

    @PostMapping("/items/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String addItem(@Valid @RequestBody ItemRequest itemRequest) {
        itemService.save(itemRequest);
        return "ok";
    }

    @PostMapping("/items/edit/{itemId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String editItem(@PathVariable Long itemId, @Valid @RequestBody ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest);
        return "ok";
    }

    @PostMapping("/items/delete/{itemId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return "ok";
    }
}