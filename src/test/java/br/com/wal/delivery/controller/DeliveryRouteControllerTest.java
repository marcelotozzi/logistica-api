package br.com.wal.delivery.controller;

import br.com.wal.delivery.helper.QueryResultHelper;
import br.com.wal.delivery.helper.QueryRouteHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DeliveryRouteControllerTest {

    @Autowired
    private DeliveryRouteController deliveryRouteController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(deliveryRouteController).build();
    }

    /**
     * Deve retornar a melhor rota entre A e D com o custo calculado.
     *
     * @throws Exception
     */
    @Test
    public void itShouldFindTheBestRouteBetweenAPointAndDPoint() throws Exception {
        //GIVEN
        String queryRoute = QueryRouteHelper.routeAToDJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRoute));

        //THEN
        String queryRouteResult = QueryResultHelper.resultAToDJSON();

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(queryRouteResult));
    }

    @Test
    public void itShouldFindTheBestRouteBetweenBPointAndEPoint() throws Exception {
        //GIVEN
        String queryRoute = QueryRouteHelper.routeBToEJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRoute));

        //THEN
        String queryRouteResult = QueryResultHelper.resultBToEJSON();

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(queryRouteResult));
    }

    @Test
    public void itShouldFindTheBestRouteBetweenBPointAndAPoint() throws Exception {
        //GIVEN
        String queryRoute = QueryRouteHelper.routeBToAJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRoute));

        //THEN
        String queryRouteResult = QueryResultHelper.resultBToAJSON();

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(queryRouteResult));
    }

    @Test
    public void itShouldFindTheBestRouteBetweenCPointAndBPoint() throws Exception {
        //GIVEN
        String queryRoute = QueryRouteHelper.routeCToBJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRoute));

        //THEN
        String queryRouteResult = QueryResultHelper.resultCToBJSON();

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(queryRouteResult));
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithEmptyQuery() throws Exception {
        //GIVEN
        String emptyQueryRoute = "";

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyQueryRoute));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutMapName() throws Exception {
        //GIVEN
        String queryRouteWithoutMapName = QueryRouteHelper.routeWithoutMapNameJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutMapName));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutOrigin() throws Exception {
        //GIVEN
        String queryRouteWithoutOrigin = QueryRouteHelper.routeWithoutOriginJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutOrigin));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutDestination() throws Exception {
        //GIVEN
        String queryRouteWithoutDestination = QueryRouteHelper.routeWithoutDestinationJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutDestination));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutAutonomy() throws Exception {
        //GIVEN
        String queryRouteWithoutAutonomy = QueryRouteHelper.routeWithoutAutonomyJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutAutonomy));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutLiter() throws Exception {
        //GIVEN
        String queryRouteWithoutLiter = QueryRouteHelper.routeWithoutLiterJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                post("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutLiter));

        //THEN
        response.andExpect(status().isBadRequest());
    }
}