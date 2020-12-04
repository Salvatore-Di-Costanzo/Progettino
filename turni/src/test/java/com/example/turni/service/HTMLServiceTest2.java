package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HTMLServiceTest2 {


    private HTMLService serviceTest;

    @BeforeEach
    public void setUp() {
        serviceTest = new HTMLService();
        serviceTest.repository = mock(TurnoRepo.class);
        serviceTest.feignDependent = mock(FeignDependent.class);
        serviceTest.turnMethod = mock(RealizzaTurni.class);


    }

    @Test
    void getMultipleResponse() {

        List<Response> expectedResult = new ArrayList<>();

        final Response response = new Response("index_d", "cognome", "nome");
        when(serviceTest.feignDependent.getResponse("index_d")).thenReturn(response);

        final List<Response> result = serviceTest.getMultipleResponse("dataInizio", "dataFine");

        assertEquals(expectedResult, result);

    }

    @Test
    void getData() {

        List<String> result = new ArrayList<>();

        List<String> wanted = new ArrayList<>();
        when(serviceTest.repository.queryData("DataInizio", "DataFine")).thenReturn(wanted);

        assertEquals(result, wanted);
    }


    @Test
    void getList() {

        List<Response> expectedResult = new ArrayList<>();


        List<Response> result = new ArrayList<>();
        when(serviceTest.getResponse("data")).thenReturn(result);

        assertEquals(expectedResult, result);

    }

    @Test
    void getDependents() {

        List<Response> expectedResult = new ArrayList<>();
        when(serviceTest.feignDependent.getDependents()).thenReturn(expectedResult);

        List<Response> result = new ArrayList<>();
        when(serviceTest.feignDependent.getDependents()).thenReturn(result);

        assertEquals(expectedResult, result);
    }

    @Test
    void findByKeyword() {

        final List<Response> expectedResult = new ArrayList<>();

        final List<Response> dependents = new ArrayList<>();
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
        when(serviceTest.creaTurni(0, 0)).thenReturn(result);

        assertEquals(expectedResult, result);
    }

    @Test
    void showAllTurns() {

        List<Turno> expectedResult = new ArrayList<>();

        List<Turno> result = new ArrayList<>();
        when(serviceTest.showAllTurns()).thenReturn(result);

        assertEquals(expectedResult, result);
    }

    @Test
    void deleteTurno() {

        serviceTest.deleteTurno("");

        verify(serviceTest.repository).queryDelete("");

    }

    @Test
    void updateTurno() {

        serviceTest.updateTurno("index_d", 0);

        verify(serviceTest.repository).queryUpdate("index_d", 0);
    }
}