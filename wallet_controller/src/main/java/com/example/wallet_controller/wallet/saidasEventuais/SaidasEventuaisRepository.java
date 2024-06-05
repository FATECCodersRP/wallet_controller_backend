package com.example.wallet_controller.wallet.saidasEventuais;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaidasEventuaisRepository extends JpaRepository<SaidasEventuais, Integer> {
    List<SaidasEventuais> findAllByIdUsuario(Integer idUsuario);

    List<SaidasEventuais> deleteByIdUsuario(Integer idUsuario);

    List<SaidasEventuais> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

}