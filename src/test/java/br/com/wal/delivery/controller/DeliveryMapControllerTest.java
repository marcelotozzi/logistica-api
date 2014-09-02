package br.com.wal.delivery.controller;

import br.com.wal.delivery.business.DeliveryMapBusiness;
import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.helper.DeliveryMapHelper;
import br.com.wal.delivery.model.DeliveryMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DeliveryMapControllerTest {

    @Autowired
    private DeliveryMapController deliveryMapController;
    @Mock
    private DeliveryMapBusiness deliveryMapBusiness;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(deliveryMapController).build();

        setField(deliveryMapController, "deliveryMapBusiness", deliveryMapBusiness);
    }

    @Test
    public void itShouldReturnStatus201WhenRegisterADeliveryMap() throws IOException, RepositoryException {
        //GIVEN
        String deliveryMapId = "1x4V";
        URI uri = UriComponentsBuilder.fromPath("/api/mapa/" + deliveryMapId).buildAndExpand(deliveryMapId).toUri();
        DeliveryMap deliveryMap = DeliveryMapHelper.deliveryMap();
        when(deliveryMapBusiness.create(deliveryMap)).thenReturn(deliveryMapId);

        //WHEN
        ResponseEntity<String> response = deliveryMapController.create(
                deliveryMap, UriComponentsBuilder.newInstance(),
                new MockHttpServletRequest(), new MockHttpServletResponse());

        //THEN
        assertThat("Deve ser retornado 201 (criado).", response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
        assertThat("Deve retornar o corpo da resposta com null", response.getBody(), is(nullValue()));
        assertThat("Deve retornar o Header preenchido", response.getHeaders(), is(notNullValue()));
        assertThat("Deve retornar o location da deliveryMap criada", response.getHeaders().getLocation(), is(equalTo(uri)));
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterANullDeliveryMap() {

        //WHEN
        ResponseEntity<String> resposta = deliveryMapController.create(
                null, UriComponentsBuilder.newInstance(),
                new MockHttpServletRequest(), new MockHttpServletResponse());

        //THEN
        assertThat("Não deve ser criada a deliveryMap esta nula.", resposta.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
        assertThat("Deve retornar o Header preenchido", resposta.getHeaders(), is(notNullValue()));
    }

    @Test
    public void itShouldReturnStatus201WhenRegisterADeliveryMapViaPOST() throws Exception {
        //GIVEN
        String deliveryMapJson = DeliveryMapHelper.deliveryMapJson();

        //WHEN

        ResultActions resposta = mockMvc.perform(
                post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJson));

        //THEN
        resposta.andExpect(status().isCreated());
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterADeliveryMapWithoutNameViaPOST() throws Exception {
        //GIVEN
        String deliveryMapJsonWithoutName = DeliveryMapHelper.deliveryMapJsonWithoutName();

        //WHEN

        ResultActions resposta = mockMvc.perform(
                post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJsonWithoutName));

        //THEN
        resposta.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterADeliveryMapWithoutRoutesViaPOST() throws Exception {
        //GIVEN
        String deliveryMapJsonWithoutRoutes = DeliveryMapHelper.deliveryMapJsonWithoutRoutes();

        //WHEN
        ResultActions resposta = mockMvc.perform(
                post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJsonWithoutRoutes));

        //THEN
        resposta.andExpect(status().isBadRequest());
    }
}