package com.example.wallet_controller.wallet.entradasEventuais;

import com.example.wallet_controller.wallet.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntradasEventuaisRepository extends JpaRepository<EntradasEventuais, Integer> {
    List<EntradasEventuais> findAllByIdUsuario(Integer idUsuario);

    List<EntradasEventuais> findByIdUsuarioAndDataBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);

    List<EntradasEventuais> deleteByIdUsuario(Integer idUsuario);

}
