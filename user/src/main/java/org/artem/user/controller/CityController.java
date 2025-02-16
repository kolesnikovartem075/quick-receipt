package org.artem.user.controller;

import lombok.RequiredArgsConstructor;
import org.artem.user.dto.nova.post.CityReadDto;
import org.artem.user.service.CityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {


    private final CityService cityService;

    @GetMapping(value = "/{queryString}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CityReadDto> find(@PathVariable String queryString) {
        return cityService.findByQueryString(queryString);
    }
}