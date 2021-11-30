package com.reksoft.holiday.controller;

import com.reksoft.holiday.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;


@Controller
public class ChartController {
    @Autowired
    private ChartService chartService;


    @RequestMapping(value = "/getImgAsBytes/1.jpeg", method = RequestMethod.GET)
    //public ResponseEntity<byte[]> getImgAsBytes(@PathVariable("id") final Long id, final HttpServletResponse response) {
    public ResponseEntity<byte[]> getImgAsBytes(final HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        byte[] media = chartService.getImageAsByteArray();

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        //return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
