package com.transportpark.controller;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.service.BusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusController {

    private final BusService busService;

    @GetMapping("/bus")
    public String viewBuses(Model model, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        addPagination(model, page, size);
        return "/bus";
    }

    @PostMapping("/bus")
    public String addBus(Model model, @ModelAttribute Bus bus,
                         @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        busService.addBus(bus);
        model.addAttribute("addedBus", bus.getModel());
        addPagination(model, page, size);
        return "/bus";
    }

    @GetMapping("/bus/edit/{code}")
    public String editBus(Model model, @PathVariable Integer code, @RequestParam("page") Integer page,
                          @RequestParam("size") Integer size) {
        model.addAttribute("editCode", code);
        addPagination(model, page, size);
        return "/bus";
    }

    @PostMapping("/bus/edit/{code}")
    public ModelAndView updateBus(Model model, @PathVariable Integer code,
                                  @RequestParam("changeMileage") Double changeMileage, @RequestParam("changeConsumption") Double changeConsumption,
                                  @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        busService.changeBus(code, changeMileage, changeConsumption);
        addPagination(model, page, size);
        return new ModelAndView("redirect:/bus");
    }

    private void addPagination(Model model, Integer currentPage, Integer pageSize) {
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
