package com.spring.controller;

import com.spring.model.domain.Assignment;
import com.spring.model.service.RouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteController {

    private final RouteService routeService;

    @GetMapping("route")
    public String viewCheck() {
        return "/route";
    }

    @PostMapping(value = "/route", params = "btnAddAssignment")
    public String addAssignment(Model model, HttpSession session,
                                @RequestParam("code") Integer code, @RequestParam("name") String name,
                                @RequestParam("tax") Double tax, @RequestParam("journey") Integer journey) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (assignments == null) {
            assignments = new ArrayList<>();
            session.setAttribute("addAssignment", assignments);
        }
        Assignment assignment = routeService.addAssignment(code, name, tax, journey);
        if (assignment != null) {
            assignments.add(assignment);
        } else {
            if (code != null) {
                model.addAttribute("busCodeNotFound", code);
            } else {
                model.addAttribute("busNameNotFound", name);
            }
        }
        return "/route";
    }

    @PostMapping(value = "/route", params = "btnCreateRoute")
    public String createRoute(HttpSession session, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (assignments != null && assignments.size() > 0) {
            try {
                routeService.addRoute(assignments);
                request.setAttribute("addedRoute", true);
                assignments.clear();
            } catch (Exception e) {
                request.setAttribute("addedRoute", false);
                assignments.clear();
                log.error("Route can not created", e);
            }
        }
        return "/route";
    }

    @PostMapping(value = "/route", params = "btnCancelRoute")
    public String clearRoute(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (assignments != null) {
            assignments.clear();
        }
        return "/route";
    }

    @GetMapping("/route/del/{count}")
    public ModelAndView editAssignments(HttpSession session, @PathVariable Integer count) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (assignments.size() >= count && count > 0) {
            assignments.remove(count - 1);
        }
        return new ModelAndView("redirect:/route");
    }
}
