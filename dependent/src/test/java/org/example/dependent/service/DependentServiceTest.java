package org.example.dependent.service;

import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.repository.DependentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class DependentServiceTest {

    private DependentService dependentServiceUnderTest;

    @BeforeEach
    public void setUp(){
        dependentServiceUnderTest = new DependentService();
        dependentServiceUnderTest.repository = mock(DependentRepo.class);
    }


    @Test
    void getDependents() {

        final List<Dependent> expectedResult = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));

        final List<Dependent> dependents = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));

        when(dependentServiceUnderTest.repository.findAll()).thenReturn(dependents);

        final List<Dependent> result = dependentServiceUnderTest.getDependents();

        assertEquals(expectedResult, result);
    }

    @Test
    void getAllIndex() {

        final List<String> result = List.of("value");

        final List<String> list = List.of("value");

        when(dependentServiceUnderTest.repository.queryIndex()).thenReturn(list);

        final List<String> list2 = dependentServiceUnderTest.getAllIndex();

        assertEquals(result,list2);

    }

    @Test
    void postDependent() {

        final Dependent dependent = new Dependent("nome", "cognome", 0.0f, "index_d");
        when(dependentServiceUnderTest.repository.save(new Dependent("nome", "cognome", 0.0f, "index_d"))).thenReturn(dependent);

        dependentServiceUnderTest.postDependent(dependent);

        verify(dependentServiceUnderTest.repository).save(dependent);

    }

    @Test
    void deleteDependentById() {


        dependentServiceUnderTest.deleteDependentById("index_d");

        verify(dependentServiceUnderTest.repository).queryDelete("index_d");
    }

    @Test
    void getStringDependent() {

        final List<Dependent> dipendente = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));

        when(dependentServiceUnderTest.repository.querySearch("index_d")).thenReturn(dipendente);

        String result = dependentServiceUnderTest.getStringDependent("index_d");

        String uscita =
                " - Matricola : index_d " +
                "- Cognome : cognome " +
                "- Nome : nome";


        assertEquals(uscita,result);
    }

    @Test
    void getResponse() {

        final Response response = new Response("index_d", "cognome", "nome");

        final List<Dependent> dependents = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));

        when(dependentServiceUnderTest.repository.querySearch("index_d")).thenReturn(dependents);

        final Response result = dependentServiceUnderTest.getResponse("index_d");


        assertEquals(response, result);
    }

    @Test
    void findByKeyword() {


        final List<Dependent> expectedResult = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));

        final List<Dependent> dependents = List.of(new Dependent("nome", "cognome", 0.0f, "index_d"));
        when(dependentServiceUnderTest.repository.findByKeyword("keyword")).thenReturn(dependents);

        final List<Dependent> result = dependentServiceUnderTest.findByKeyword("keyword");

        assertEquals(expectedResult, result);
    }
}