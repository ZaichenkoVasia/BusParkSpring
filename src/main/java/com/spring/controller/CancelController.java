package com.spring.controller;

import com.spring.model.domain.Assignment;
import com.spring.model.domain.Route;
import com.spring.model.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CancelController {

    private final RouteService routeService;

    @GetMapping("/cancel")
    public String cancelView() {
        return "/cancel";
    }

    @PostMapping(value = "/cancel", params = "btnSearchRoute")
    public String searchAssignments(HttpSession session,
                                    @RequestParam("routeId") Long routeId) {
        Route route = routeService.findById(routeId);
        List<Assignment> assignments = routeService.findAssignmentsByRoute(routeId);
        session.setAttribute("route", route);
        session.setAttribute("assignments", assignments);
        session.setAttribute("routeFind", routeId);

        return "/cancel";
    }

    @GetMapping("/cancel/edit/{count}")
    public ModelAndView cancelAssignment(HttpSession session, @PathVariable Integer count) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("assignments");
        routeService.cancelRouteAssignments(assignments, count);

        return new ModelAndView("redirect:/cancel");
    }

    @PostMapping(value = "/cancel", params = "btnCancelRoute")
    public String cancelRoute(HttpSession session) {
        Route route = (Route) session.getAttribute("route");
        routeService.cancelRouteAssignments(route);

        return "/cancel";
    }
}
