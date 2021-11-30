package com.reksoft.holiday.controller;

import com.reksoft.holiday.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Controller
public class ChartController {
    @Autowired
    private ChartService chartService;

    @RequestMapping(value = "/getImgAsBytes/{id}.jpeg", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImgAsBytes(@PathVariable("id") final Integer id,
                                                final HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        Map<Integer,byte[]> map;
        byte[] media = null;
        try {
            map = chartService.getImages();
        } catch (IOException ioException){
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        media = map.get(id);
        if(media==null) {
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
