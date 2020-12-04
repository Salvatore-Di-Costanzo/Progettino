package org.example.dependent.repository;

import org.example.dependent.pojo.Dependent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface DependentRepo extends JpaRepository<Dependent, Integer> {


    @Query(value ="select * from Dependent d where d.nome like %:keyword% " +
            "or d.cognome like %:keyword%",nativeQuery = true)
    List<Dependent> findByKeyword(@Param("keyword")String keyword);

    @Query(value = "select index_d from Dependent d")
    List<String> queryIndex();

    @Transactional
    @Modifying
    @Query(value = "delete from Dependent d where index_d like :index_d")
    void queryDelete(String index_d);

    @Query(value = "from Dependent d where d.index_d like :matricola")
            List<Dependent> querySearch(@Param("matricola")String index_d);

    @Query(value ="select index_d from Dependent")
    List<String> queryGenerate();

    @Query(value = "select index_d from Dependent where (nome like :nome and cognome like :cognome)")
    String queryGetIndexD(@Param("nome")String nome,@Param("cognome")String cognome);


}
