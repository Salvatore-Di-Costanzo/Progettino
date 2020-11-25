package com.example.turni.repository;


import com.example.turni.pojo.Turno;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;


public interface TurnoRepo extends JpaRepository<Turno, Integer> {

    @Transactional
    @Modifying
    @Query("update Turno set index_d = :index_d where index_g = :index_g")
    void queryUpdate(@Param("index_d")String index_d , @Param("index_g") int index_g);

    @Query(value = "select index_g from Turno")
    List<Integer> selectQueryG();

    @Query(value = "select max(index_t) from Turno")
    Query queryMax();

    @Query(value ="from Turno t where t.id = (select max(id) from Turno)")
    List<Turno> queryTurno();

    @Query(value ="select index_t from Turno group by index_t")
    List<Integer> queryGenerate();

    @Query(value ="select index_d from Turno where data like :data")
    List<String> queryGetData(@Param("data") String data);

    @Transactional
    @Modifying
    @Query(value = "delete from Turno where index_t = :index_t")
    void queryDelete(int index_t);

    @Query(value = "select index_d from Turno where index_t =:index_t")
    List<String> queryCheck(@Param("index_t") int index_t);

    @Query(value = "select index_d from Turno where data BETWEEN :dataInizio and :dataFine")
    List<String> queryBetween(@Param("dataInizio")String dataInizio, @Param("dataFine")String dataFine);

    @Query(value = "select data from Turno where (data BETWEEN :dataInizio and :dataFine)")
    List<String> queryData(@Param("dataInizio")String dataInizio, @Param("dataFine")String dataFine);



}
