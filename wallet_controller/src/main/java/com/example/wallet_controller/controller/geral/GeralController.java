package com.example.wallet_controller.controller.geral;

import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuaisRepository;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuaisRepository;
import com.example.wallet_controller.wallet.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("soma")
public class GeralController {

    @Autowired
    private EntradasEventuaisRepository entradasEventuaisRepository;

    @Autowired
    private SaidasEventuaisRepository saidasEventuaisRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping("entradas/{userId}/{mes}/{ano}")
    public ResponseEntity<Object> getTotalEntradas(@PathVariable Integer userId,
                                                   @PathVariable Integer mes,
                                                   @PathVariable Integer ano) {
        try {
            Double totalEntradas = entradasEventuaisRepository.getTotalEntradasByMesAndAno(userId, mes, ano);
            if (totalEntradas == null) {
                return ResponseEntity.noContent().build();
            } else {
                Map<String, Double> response = new HashMap<>();
                response.put("totalEntradas", totalEntradas);
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("saidas/{userId}/{mes}/{ano}")
    public ResponseEntity<Object> getTotalSaidas(@PathVariable Integer userId,
                                                 @PathVariable Integer mes,
                                                 @PathVariable Integer ano) {
        try {
            Double totalSaidas = saidasEventuaisRepository.getTotalSaidasByMesAndAno(userId, mes, ano);
            if (totalSaidas == null) {
                return ResponseEntity.noContent().build();
            } else {
                Map<String, Double> response = new HashMap<>();
                response.put("totalSaidas", totalSaidas);
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("saldo/{userId}/{mes}/{ano}")
    public ResponseEntity<Object> getSaldo(@PathVariable Integer userId,
                                           @PathVariable Integer mes,
                                           @PathVariable Integer ano) {
        try {
            Double totalEntradas = entradasEventuaisRepository.getTotalEntradasByMesAndAno(userId, mes, ano);
            Double totalSaidas = saidasEventuaisRepository.getTotalSaidasByMesAndAno(userId, mes, ano);

            if (totalEntradas == null) totalEntradas = 0.0;
            if (totalSaidas == null) totalSaidas = 0.0;

            Double saldo = totalEntradas - totalSaidas;

            Map<String, Double> response = new HashMap<>();
            response.put("saldo", saldo);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}