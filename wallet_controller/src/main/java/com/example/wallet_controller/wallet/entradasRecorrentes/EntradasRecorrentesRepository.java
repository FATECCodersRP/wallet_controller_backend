package com.example.wallet_controller.wallet.entradasRecorrentes;

import com.example.wallet_controller.wallet.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntradasRecorrentesRepository extends JpaRepository<EntradasRecorrentes, Integer> {

    List<EntradasRecorrentes> findAllByIdUsuario(Integer idUsuario);

    List<EntradasRecorrentes> deleteByIdUsuario(Integer idUsuario);

    List<EntradasRecorrentes> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);
}