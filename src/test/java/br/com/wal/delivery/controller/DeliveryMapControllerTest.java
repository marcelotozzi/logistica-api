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
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        assertThat("Deve retornar o location da deliveryMap criada", response.getHeaders().getLocation().toString(), is(equalTo(uri.toString())));
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterANullDeliveryMap() {

        //WHEN
        ResponseEntity<String> response = deliveryMapController.create(
                null, UriComponentsBuilder.newInstance(),
                new MockHttpServletRequest(), new MockHttpServletResponse());

        //THEN
        assertThat("Não deve ser criada a deliveryMap esta nula.", response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
        assertThat("Deve retornar o Header preenchido", response.getHeaders(), is(notNullValue()));
    }

    @Test
    public void itShouldReturnStatus201WhenRegisterADeliveryMapViaPOST() throws Exception {
        //GIVEN
        String deliveryMapId = "1x4V";
        URI uri = UriComponentsBuilder.fromPath("http:////localhost/api/mapa/" + deliveryMapId).buildAndExpand(deliveryMapId).toUri();
        when(deliveryMapBusiness.create(isNotNull(DeliveryMap.class))).thenReturn(deliveryMapId);

        String deliveryMapJson = DeliveryMapHelper.deliveryMapJson();

        //WHEN
        ResultActions response = mockMvc.perform(post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJson));

        //THEN
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", uri.toString()));
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterADeliveryMapWithoutNameViaPOST() throws Exception {
        //GIVEN
        String deliveryMapJsonWithoutName = DeliveryMapHelper.deliveryMapJsonWithoutName();

        //WHEN
        ResultActions response = mockMvc.perform(post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJsonWithoutName));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturnStatus400WhenTryRegisterADeliveryMapWithoutRoutesViaPOST() throws Exception {
        //GIVEN
        String deliveryMapJsonWithoutRoutes = DeliveryMapHelper.deliveryMapJsonWithoutRoutes();

        //WHEN
        ResultActions response = mockMvc.perform(post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryMapJsonWithoutRoutes));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTryRegisterAEmptyMap() throws Exception {
        //GIVEN
        String emptyMap = "";

        //WHEN
        ResultActions response = mockMvc.perform(post("/api/mapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyMap));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturnStatus200WhenShowARegisteredMapViaGET() throws Exception {
        //GIVEN
        String deliveryMapId = "1x4V";
        DeliveryMap deliveryMap = DeliveryMapHelper.deliveryMap();
        String deliveryMapJson = DeliveryMapHelper.deliveryMapJson();

        when(deliveryMapBusiness.create(isNotNull(DeliveryMap.class))).thenReturn(deliveryMapId);
        when(deliveryMapBusiness.show(deliveryMapId)).thenReturn(deliveryMap);

        mockMvc.perform(post("/api/mapa").contentType(MediaType.APPLICATION_JSON).content(deliveryMapJson)).andExpect(status().isCreated());

        //WHEN
        ResultActions getResponse = mockMvc.perform(get("/api/mapa/" + deliveryMapId));

        //THEN
        getResponse.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(deliveryMapJson));
    }
}