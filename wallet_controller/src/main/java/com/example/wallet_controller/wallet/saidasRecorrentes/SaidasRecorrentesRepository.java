package com.example.wallet_controller.wallet.saidasRecorrentes;

import com.example.wallet_controller.wallet.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaidasRecorrentesRepository extends JpaRepository<SaidasRecorrentes, Integer> {
    List<SaidasRecorrentes> findAllByIdUsuario(Integer idUsuario);

    List<SaidasRecorrentes> deleteByIdUsuario(Integer idUsuario);

    List<SaidasRecorrentes> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

}
