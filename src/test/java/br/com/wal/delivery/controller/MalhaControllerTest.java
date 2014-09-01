package br.com.wal.delivery.controller;

import br.com.wal.delivery.business.MalhaBusiness;
import br.com.wal.delivery.helper.MalhaHelper;
import br.com.wal.delivery.model.Malha;
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
/**
 *
 */
public class MalhaControllerTest {

    @Autowired
    private MalhaController malhaController;
    @Mock
    private MalhaBusiness malhaBusiness;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(malhaController).build();

        setField(malhaController, "malhaBusiness", malhaBusiness);
    }

    @Test
    public void deveRetornarStatus201AoRegistrarAMalhaLogistica() throws IOException {
        //GIVEN
        String idDaMalha = "1x4V";
        URI uri = UriComponentsBuilder.fromPath("/api/malha/" + idDaMalha).buildAndExpand(idDaMalha).toUri();
        Malha malha = MalhaHelper.malha();
        when(malhaBusiness.create(malha)).thenReturn(idDaMalha);

        //WHEN
        ResponseEntity<String> resposta = malhaController.create(
                malha, UriComponentsBuilder.newInstance(),
                new MockHttpServletRequest(), new MockHttpServletResponse());

        //THEN
        assertThat("Deve ser retornado 201 (criado).", resposta.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
        assertThat("Deve retornar o corpo da resposta com null", resposta.getBody(), is(nullValue()));
        assertThat("Deve retornar o Header preenchido", resposta.getHeaders(), is(notNullValue()));
        assertThat("Deve retornar o location da malha criada", resposta.getHeaders().getLocation(), is(equalTo(uri)));
    }

    @Test
    public void deveRetornarStatus400AoTentarRegistrarUmaMalhaNula() {

        //WHEN
        ResponseEntity<String> resposta = malhaController.create(
                null, UriComponentsBuilder.newInstance(),
                new MockHttpServletRequest(), new MockHttpServletResponse());

        //THEN
        assertThat("Não deve ser criada a malha esta nula.", resposta.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
        assertThat("Deve retornar o Header preenchido", resposta.getHeaders(), is(notNullValue()));
    }

    @Test
    public void deveRetornarStatus201AoRegistrarAMalhaLogisticaViaPost() throws Exception {
        //GIVEN
        String malhaJson = MalhaHelper.malhaJson();

        //WHEN

        ResultActions resposta = mockMvc.perform(
                post("/api/malha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malhaJson));

        //THEN
        resposta.andExpect(status().isCreated());
    }

    @Test
    public void deveRetornarStatus400AoTentarRegistrarUmaMalhaSemNome() throws Exception {
        //GIVEN
        String malhaJsonSemNome = MalhaHelper.malhaJsonSemNome();

        //WHEN

        ResultActions resposta = mockMvc.perform(
                post("/api/malha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malhaJsonSemNome));

        //THEN
        resposta.andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarStatus400AoTentarRegistrarUmaMalhaSemTemRotas() throws Exception {
        //GIVEN
        String malhaJsonSemRotas = MalhaHelper.malhaJsonSemRotas();

        //WHEN
        ResultActions resposta = mockMvc.perform(
                post("/api/malha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malhaJsonSemRotas));

        //THEN
        resposta.andExpect(status().isBadRequest());
    }

}