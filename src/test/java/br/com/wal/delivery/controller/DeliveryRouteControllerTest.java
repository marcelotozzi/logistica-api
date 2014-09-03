package br.com.wal.delivery.controller;

import br.com.wal.delivery.helper.QueryHelper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
     * query:
     * {
     * "mapa": "SP",
     * "origem": "A",
     * "destino": "D",
     * "autonomia": 10,
     * "litro": 2.50
     * }
     * <p/>
     * resultado:
     * {
     * "rota":["A","B","D"],"custo":6.25
     * }
     *
     * @throws Exception
     */
    @Test
    public void itShouldSearchBestRouteViaGET() throws Exception {
        //GIVEN
        String queryRoute = QueryHelper.queryRouteJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRoute));

        //THEN
        String queryRouteResult = QueryHelper.queryRouteResultJSON();

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
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyQueryRoute));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutMapName() throws Exception {
        //GIVEN
        String queryRouteWithoutMapName = QueryHelper.queryRouteWithoutMapNameJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutMapName));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutOrigin() throws Exception {
        //GIVEN
        String queryRouteWithoutOrigin = QueryHelper.queryRouteWithoutOriginJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutOrigin));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutDestination() throws Exception {
        //GIVEN
        String queryRouteWithoutDestination = QueryHelper.queryRouteWithoutDestinationJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutDestination));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutAutonomy() throws Exception {
        //GIVEN
        String queryRouteWithoutAutonomy = QueryHelper.queryRouteWithoutAutonomyJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutAutonomy));

        //THEN
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldThrows404ErrorWhenTrySearchWithQueryWithoutLiter() throws Exception {
        //GIVEN
        String queryRouteWithoutLiter = QueryHelper.queryRouteWithoutLiterJSON();

        //WHEN
        ResultActions response = mockMvc.perform(
                get("/api/rota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(queryRouteWithoutLiter));

        //THEN
        response.andExpect(status().isBadRequest());
    }
}