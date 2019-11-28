package com.spring.controller;

import com.spring.model.domain.Bus;
import com.spring.model.service.BusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusController {

    private final BusService busService;

    @GetMapping("/bus")
    public String viewBuses(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        addPagination(model, page, size);
        return "/bus";
    }

    @PostMapping("/bus")
    public String addBus(Model model, @RequestParam("code") Integer code, @RequestParam("model") String name,
                         @RequestParam("mileage") Double mileage, @RequestParam("consumption") Double consumption, @RequestParam("status") String status,
                         @RequestParam("comments") String comments,
                         @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        busService.addBus(code, name, mileage, consumption, status, comments);
        model.addAttribute("addedBus", name);
        addPagination(model, page, size);
        return "/bus";
    }

    @GetMapping("/bus/edit/{code}")
    public String editBus(Model model, @PathVariable Integer code, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("editCode", code);
        addPagination(model, page, size);
        return "/bus";
    }

    @PostMapping("/bus/edit/{code}")
    public ModelAndView updateBus(Model model, @PathVariable Integer code,
                                  @RequestParam("changeMileage") Double changeMileage, @RequestParam("changeConsumption") Double changeConsumption,
                                  @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        busService.changeBus(code, changeMileage, changeConsumption);
        addPagination(model, page, size);
        return new ModelAndView("redirect:/bus");
    }

    private void addPagination(Model model, Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Bus> buses = busService.showPageList(currentPage, pageSize);
        model.addAttribute("bus", buses);
        model.addAttribute("currentPage", currentPage);
        int totalPages = buses.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
