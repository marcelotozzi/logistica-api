package br.com.wal.delivery.controller;

import br.com.wal.delivery.business.DeliveryRouteBusiness;
import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.exception.InvalidQueryRouteException;
import br.com.wal.delivery.validator.QueryRouteValidator;
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
 * Created by marcelotozzi on 02/09/14.
 */
@Controller
@RequestMapping("api")
public class DeliveryRouteController {
    @Autowired
    private DeliveryRouteBusiness deliveryRouteBusiness;

    @RequestMapping(value = "rota", method = RequestMethod.GET,
            consumes = {"application/json"}, produces = {"application/json"},
            headers = "content-type=application/json")
    public
    @ResponseBody
    ResponseEntity<QueryResult> query(@RequestBody(required = true) final QueryRoute queryRoute, UriComponentsBuilder builder,
                                      final HttpServletRequest request, final HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        try {
            QueryRouteValidator.validateQueryRoute(queryRoute);

            QueryResult result = deliveryRouteBusiness.query(queryRoute);

            return new ResponseEntity<QueryResult>(result, headers, HttpStatus.OK);
        } catch (InvalidQueryRouteException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
