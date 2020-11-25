package org.example.dependent.service;

import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.repository.DependentRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class DependentServiceTest {

    private DependentService dependentServiceUnderTest;
    private Dependent dependentTest;

    @Spy
    private List<Dependent> dependents = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        dependentServiceUnderTest = new DependentService();
        dependentServiceUnderTest.repository = mock(DependentRepo.class);
        dependentTest = mock(Dependent.class);
    }


    @Test
    void getDependents() {

        final List<Dependent> expectedResult = new ArrayList<>();

        when(dependentServiceUnderTest.repository.findAll()).thenReturn(dependents);

        final List<Dependent> result = dependentServiceUnderTest.getDependents();

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    void getAllIndex() {

        final List<String> result = new ArrayList<>();

        final List<String> list = new ArrayList<>();

        when(dependentServiceUnderTest.repository.queryIndex()).thenReturn(list);

        final List<String> list2 = dependentServiceUnderTest.getAllIndex();

        assertEquals(result, list2);

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

        dependentTest = new Dependent("nome", "cognome", 0.0f, "index_d");

        dependents.add(dependentTest);

        when(dependentServiceUnderTest.repository.querySearch("index_d")).thenReturn(dependents);

        String result = dependentServiceUnderTest.getStringDependent("index_d");

        String uscita =
                " - Matricola : index_d " +
                        "- Cognome : cognome " +
                        "- Nome : nome";

        assertEquals(uscita, result);
    }


    @Test
    void testGetResponse() {
        Dependent dependent = new Dependent("index_d", "cognome", "nome");
        final Response response = new Response("index_d", "cognome", "nome");

        dependents.add(dependent);
        when(dependentServiceUnderTest.repository.querySearch("index_d")).thenReturn(dependents);

        final Response result = new Response(dependents.get(0).getIndex_d(),
                dependents.get(0).getCognome(),
                dependents.get(0).getNome());

        assertEquals(response, result);
    }


    @Test
    void findByKeyword() {


        final List<Dependent> expectedResult = new ArrayList<>();


        when(dependentServiceUnderTest.repository.findByKeyword("keyword")).thenReturn(dependents);

        final List<Dependent> result = dependentServiceUnderTest.findByKeyword("keyword");

        assertEquals(expectedResult, result);
    }
}