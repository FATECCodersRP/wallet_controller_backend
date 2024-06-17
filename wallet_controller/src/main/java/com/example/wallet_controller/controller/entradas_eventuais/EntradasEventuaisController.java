package com.example.wallet_controller.controller.entradas_eventuais;

import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuais;
import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuaisRepository;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentes;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("entradas/eventuais")
public class EntradasEventuaisController {

    @Autowired
    private EntradasEventuaisRepository eventuaisRepository;

    @Autowired
    private EntradasRecorrentesRepository recorrentesRepository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<Object> getAllByUserId(@PathVariable Integer userId) {
        try {
            List<EntradasEventuais> eventuaisList = eventuaisRepository.findAllByIdUsuario(userId);
            List<EntradasRecorrentes> recorrentesList = recorrentesRepository.findAllByIdUsuario(userId);

            List<Object> combinedList = new ArrayList<>();
            combinedList.addAll(eventuaisList);
            combinedList.addAll(recorrentesList);

            if (combinedList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (Object entry : combinedList) {
                    if (entry instanceof EntradasEventuais) {
                        EntradasEventuais eventuais = (EntradasEventuais) entry;
                        System.out.println("ID: " + eventuais.getId());
                        System.out.println("Descrição: " + eventuais.getDescricao());
                        System.out.println("Valor: " + eventuais.getValor());
                        System.out.println("Frequência: " + eventuais.getFrequencia());
                        System.out.println("Data: " + eventuais.getData());
                        System.out.println("ID do Usuário: " + eventuais.getIdUsuario());
                    } else if (entry instanceof EntradasRecorrentes) {
                        EntradasRecorrentes recorrentes = (EntradasRecorrentes) entry;
                        System.out.println("ID: " + recorrentes.getId());
                        System.out.println("Descrição: " + recorrentes.getDescricao());
                        System.out.println("Valor: " + recorrentes.getValor());
                        System.out.println("Frequência: " + recorrentes.getFrequencia());
                        System.out.println("Data: " + recorrentes.getData());
                        System.out.println("ID do Usuário: " + recorrentes.getIdUsuario());
                    }
                }
                return ResponseEntity.ok().body(combinedList);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("geral/{userId}/{year}/{month}")
    public ResponseEntity<List<EntradasEventuais>> getByUserIdAndMonthYear(@PathVariable Integer userId, @PathVariable int year, @PathVariable int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            List<EntradasEventuais> walletList = eventuaisRepository.findByIdUsuarioAndDataBetween(userId, startDate, endDate);
            if (walletList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(walletList);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<EntradasEventuais> create(@RequestBody EntradasEventuais entradaEventual) {
        try {
            EntradasEventuais savedEntradaEventual = eventuaisRepository.save(entradaEventual);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntradaEventual);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}