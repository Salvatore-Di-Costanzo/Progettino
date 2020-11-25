package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HTMLServiceTest2 {


    private HTMLService serviceTest;


    @BeforeEach
    public void setUp(){
        serviceTest = new HTMLService();
        serviceTest.repository = mock(TurnoRepo.class);
        serviceTest.feignDependent = mock(FeignDependent.class);
        serviceTest.turnMethod = mock(RealizzaTurni.class);


    }
    @Test
    void getMultipleResponse() {

        List<Response> expectedResult = List.of(new Response("index_d", "cognome", "nome"));
        when(serviceTest.repository.queryBetween("dataInizio","dataFine")).thenReturn(List.of("value"));

        final Response response = new Response("index_d", "cognome", "nome");
        when(serviceTest.feignDependent.getResponse("index_d")).thenReturn(response);

        final List<Response> result = serviceTest.getMultipleResponse("dataInizio", "dataFine");

        assertEquals(expectedResult.size(), result.size());

    }

    @Test
    void getData() {

        List<String> result = List.of("values");

        List<String> wanted = List.of("values");
        when(serviceTest.repository.queryData("DataInizio","DataFine")).thenReturn(wanted);

        assertEquals(result,wanted);
    }


    @Test
    void getList() {
        List<Response> expectedResult = List.of(new Response("index_d", "cognome", "nome"));
        //when(serviceTest.getResponse("data")).thenReturn(List.of(new Response("index_d", "cognome", "nome")));

        List<Response> result = List.of(new Response("index_d", "cognome", "nome"));
        when(serviceTest.getResponse("data")).thenReturn(result);

        assertEquals(expectedResult,result);
    }

    @Test
    void getDependents() {

        List<Response> expectedResult = List.of(new Response("index_d", "cognome", "nome"));
        //when(serviceTest.feignDependent.getDependents()).thenReturn(List.of(new Response("index_d", "cognome", "nome")));

        List<Response> result = List.of(new Response("index_d", "cognome", "nome"));
        when(serviceTest.feignDependent.getDependents()).thenReturn(result);

        assertEquals(expectedResult,result);
    }

    @Test
    void findByKeyword() {

        final List<Response> expectedResult = List.of(new Response("nome", "cognome", "index_d"));

        final List<Response> dependents = List.of(new Response("nome", "cognome", "index_d"));
        when(serviceTest.feignDependent.findByKeyword("keyword")).thenReturn(dependents);

        final List<Response> result = serviceTest.findByKeyword("keyword");

        assertEquals(expectedResult, result);
    }

    @Test
    void deleteDependent() {

        final Response response = new Response("index_d", "cognome", "nome");
        when(serviceTest.feignDependent.deleteDependent("index_d")).thenReturn(response);

        serviceTest.deleteDependent("index_d");
    }

    @Test
    void postDependent() {

        final Response expectedResult = new Response("index_d", "cognome", "nome");

        serviceTest.postDependent(expectedResult);

        verify(serviceTest.feignDependent).postDependent(new Response("index_d", "cognome", "nome"));
    }

    @Test
    void creaTurni() {

        final String expectedResult = "value";

        final String result = "value";
        when(serviceTest.creaTurni(0,0)).thenReturn(result);

        assertEquals(expectedResult,result);
    }

    @Test
    void showAllTurns() {

        List<Turno> expectedResult = List.of(new Turno(0,"data","index_d",0));

        List<Turno> result = List.of(new Turno(0,"data","index_d",0));
        when(serviceTest.showAllTurns()).thenReturn(result);

        assertEquals(expectedResult,result);
    }

    @Test
    void deleteTurno() {

        serviceTest.deleteTurno(0);

        verify(serviceTest.repository).queryDelete(0);

    }

    @Test
    void queryIdDays() {
        List<Integer> expectedResult = List.of(0);

        List<Integer> result = List.of(0);
        when(serviceTest.repository.selectQueryG()).thenReturn(result);

        assertEquals(expectedResult,result);

    }

    @Test
    void updateTurno() {

        serviceTest.updateTurno("index_d",0);

        verify(serviceTest.repository).queryUpdate("index_d",0);
    }
}