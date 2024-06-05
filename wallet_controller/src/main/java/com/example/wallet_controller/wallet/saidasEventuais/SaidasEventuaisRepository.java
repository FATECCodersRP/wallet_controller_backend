package com.example.wallet_controller.wallet.saidasEventuais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface SaidasEventuaisRepository extends JpaRepository<SaidasEventuais, Integer> {
    List<SaidasEventuais> findAllByIdUsuario(Integer idUsuario);

    List<SaidasEventuais> deleteByIdUsuario(Integer idUsuario);

    List<SaidasEventuais> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(se.valor), 0) + COALESCE(SUM(sr.valor), 0) " +
            "FROM saidas_eventuais se " +
            "LEFT JOIN saidas_recorrentes sr ON se.idUsuario = sr.idUsuario " +
            "WHERE se.idUsuario = :userId " +
            "AND MONTH(se.data) = :mes " +
            "AND YEAR(se.data) = :ano")
    Double getTotalSaidasByMesAndAno(@Param("userId") Integer userId,
                                     @Param("mes") Integer mes,
                                     @Param("ano") Integer ano);

}