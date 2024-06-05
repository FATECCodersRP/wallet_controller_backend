package com.example.wallet_controller.wallet.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.util.List;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    List<Usuarios> findByNome(String nome);

    Optional<Usuarios> findByEmail(String email);

    @Query("SELECT COALESCE(SUM(ee.valor), 0) + COALESCE(SUM(er.valor), 0) " +
            "- COALESCE(SUM(se.valor), 0) - COALESCE(SUM(sr.valor), 0) " +
            "FROM entradas_eventuais ee " +
            "LEFT JOIN entradas_recorrentes er ON ee.idUsuario = er.idUsuario " +
            "LEFT JOIN saidas_eventuais se ON ee.idUsuario = se.idUsuario " +
            "LEFT JOIN saidas_recorrentes sr ON ee.idUsuario = sr.idUsuario " +
            "WHERE ee.idUsuario = :userId " +
            "AND MONTH(ee.data) = :mes " +
            "AND YEAR(ee.data) = :ano")
    Double getSaldoByMesAndAno(@Param("userId") Integer userId,
                               @Param("mes") Integer mes,
                               @Param("ano") Integer ano);

}
