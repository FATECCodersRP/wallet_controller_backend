package com.example.wallet_controller.controller.registro;

import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuais;
import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuaisRepository;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentes;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentesRepository;
import com.example.wallet_controller.wallet.registro.Registro;
import com.example.wallet_controller.wallet.registro.RegistroRepository;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuais;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuaisRepository;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentes;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("registro")
public class RegistroController {

    @Autowired
    private EntradasEventuaisRepository entradasEventuaisRepository;

    @Autowired
    private EntradasRecorrentesRepository entradasRecorrentesRepository;

    @Autowired
    private SaidasRecorrentesRepository saidasRecorrentesRepository;

    @Autowired
    private SaidasEventuaisRepository saidasEventuaisRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @PostMapping("registra/entradas/eventuais/{userId}")
    public ResponseEntity<?> registraEntradaEventual(@PathVariable Integer userId) {
        try {
            EntradasEventuais ultimaEntrada = entradasEventuaisRepository.findTopByOrderByIdDesc();
            if (ultimaEntrada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma transação encontrada.");
            }

            Registro novoRegistro = new Registro();
            novoRegistro.setIdUsuario(userId);
            novoRegistro.setIdEntradaEventual(ultimaEntrada.getId());
            novoRegistro.setData(LocalDate.now());

            Registro registroSalvo = registroRepository.save(novoRegistro);

            return ResponseEntity.status(HttpStatus.CREATED).body(registroSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("registra/entradas/recorrentes/{userId}")
    public ResponseEntity<?> registraEntradaRecorrente(@PathVariable Integer userId) {
        try {
            EntradasRecorrentes ultimaEntrada = entradasRecorrentesRepository.findTopByOrderByIdDesc();
            if (ultimaEntrada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma transação encontrada.");
            }

            Registro novoRegistro = new Registro();
            novoRegistro.setIdUsuario(userId);
            novoRegistro.setIdEntradaRecorrente(ultimaEntrada.getId());
            novoRegistro.setData(LocalDate.now());

            Registro registroSalvo = registroRepository.save(novoRegistro);

            return ResponseEntity.status(HttpStatus.CREATED).body(registroSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("registra/saidas/recorrentes/{userId}")
    public ResponseEntity<?> registraSaidaRecorrente(@PathVariable Integer userId) {
        try {
            SaidasRecorrentes ultimaSaida = saidasRecorrentesRepository.findTopByOrderByIdDesc();
            if (ultimaSaida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma transação encontrada.");
            }

            Registro novoRegistro = new Registro();
            novoRegistro.setIdUsuario(userId);
            novoRegistro.setIdSaidaRecorrente(ultimaSaida.getId());
            novoRegistro.setData(LocalDate.now());

            Registro registroSalvo = registroRepository.save(novoRegistro);

            return ResponseEntity.status(HttpStatus.CREATED).body(registroSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("registra/saidas/eventuais/{userId}")
    public ResponseEntity<?> registraSaidaEventual(@PathVariable Integer userId) {
        try {
            SaidasEventuais ultimaSaida = saidasEventuaisRepository.findTopByOrderByIdDesc();
            if (ultimaSaida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma transação encontrada.");
            }

            Registro novoRegistro = new Registro();
            novoRegistro.setIdUsuario(userId);
            novoRegistro.setIdSaidaEventual(ultimaSaida.getId());
            novoRegistro.setData(LocalDate.now());

            Registro registroSalvo = registroRepository.save(novoRegistro);

            return ResponseEntity.status(HttpStatus.CREATED).body(registroSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


