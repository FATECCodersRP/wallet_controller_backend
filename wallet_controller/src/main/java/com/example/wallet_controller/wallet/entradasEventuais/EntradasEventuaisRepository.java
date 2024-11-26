package com.example.wallet_controller.wallet.entradasEventuais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntradasEventuaisRepository extends JpaRepository<EntradasEventuais, Integer> {
    List<EntradasEventuais> findAllByIdUsuario(Integer idUsuario);

    EntradasEventuais findTopByOrderByIdDesc();

    List<EntradasEventuais> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

    List<EntradasEventuais> deleteByIdUsuario(Integer idUsuario);

    @Query("SELECT COALESCE(SUM(ee.valor), 0) + COALESCE(SUM(er.valor), 0) " +
            "FROM entradas_eventuais ee " +
            "LEFT JOIN entradas_recorrentes er ON ee.idUsuario = er.idUsuario " +
            "WHERE ee.idUsuario = :userId " +
            "AND MONTH(ee.data) = :mes " +
            "AND YEAR(ee.data) = :ano")
    Double getTotalEntradasByMesAndAno(@Param("userId") Integer userId,
                                       @Param("mes") Integer mes,
                                       @Param("ano") Integer ano);

    @Query(value = "SELECT * FROM vw_eventuais WHERE id_usuario = :userId", nativeQuery = true)
    List<EntradasEventuais> findAllByUserId(@Param("userId") Integer userId);


}
