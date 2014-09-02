package br.com.wal.delivery.controller;

import br.com.wal.delivery.business.DeliveryMapBusiness;
import br.com.wal.delivery.exception.InvalidDeliveryMapException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.validator.DeliveryMapValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Controller
@RequestMapping("api")
public class DeliveryMapController {

    @Autowired
    private DeliveryMapBusiness deliveryMapBusiness;

    @RequestMapping(value = "mapa", method = RequestMethod.POST,
            consumes = {"application/json"}, headers = "content-type=application/json")
    public
    @ResponseBody
    ResponseEntity<String> create(@RequestBody(required = true) final DeliveryMap deliveryMap, UriComponentsBuilder builder,
                                  final HttpServletRequest request, final HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        try {
            DeliveryMapValidator.validateDeliveryMap(deliveryMap);

            String deliveryMapToken = deliveryMapBusiness.create(deliveryMap);

            headers.setLocation(builder.path("/api/mapa/{id}").buildAndExpand(deliveryMapToken).toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (InvalidDeliveryMapException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "mapa/{token}", method = RequestMethod.GET, produces = {"application/json"})
    public
    @ResponseBody
    ResponseEntity<DeliveryMap> show(@PathVariable(value = "token") final String token,
                                     final HttpServletRequest request, final HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();

        try {
            DeliveryMap deliveryMap = deliveryMapBusiness.show(token);

            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(deliveryMap, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}