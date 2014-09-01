package br.com.wal.delivery.controller;

import br.com.wal.delivery.business.MalhaBusiness;
import br.com.wal.delivery.exception.MalhaInvalidaException;
import br.com.wal.delivery.model.Malha;
import br.com.wal.delivery.validator.MalhaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Controller
@RequestMapping("api")
public class MalhaController {

    @Autowired
    private MalhaBusiness malhaBusiness;

    @RequestMapping(value = "malha", method = RequestMethod.POST,
            consumes = {"application/json"}, headers = "content-type=application/json")
    public
    @ResponseBody
    ResponseEntity<String> create(@RequestBody final Malha malha, UriComponentsBuilder builder,
                                  final HttpServletRequest request, final HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        try {
            MalhaValidator.validarMalha(malha);

            String idDaMalha = malhaBusiness.create(malha);

            headers.setLocation(builder.path("/api/malha/{id}").buildAndExpand(idDaMalha).toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (MalhaInvalidaException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}