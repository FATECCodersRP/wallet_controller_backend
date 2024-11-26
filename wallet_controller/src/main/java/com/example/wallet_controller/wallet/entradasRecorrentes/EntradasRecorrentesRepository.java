package com.example.wallet_controller.wallet.entradasRecorrentes;

import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuais;
import com.example.wallet_controller.wallet.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntradasRecorrentesRepository extends JpaRepository<EntradasRecorrentes, Integer> {

    @Query(value = "SELECT * FROM vw_eventuais WHERE id_usuario = :userId", nativeQuery = true)
    List<EntradasEventuais> findAllByUserId(@Param("userId") Integer userId);

    EntradasRecorrentes findTopByOrderByIdDesc();

    List<EntradasRecorrentes> findAllByIdUsuario(Integer idUsuario);

    List<EntradasRecorrentes> deleteByIdUsuario(Integer idUsuario);

    List<EntradasRecorrentes> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

}