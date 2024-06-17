package com.example.wallet_controller.controller.saidas_eventuais;

import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuais;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuaisRepository;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentes;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentesRepository;
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
@RequestMapping("saidas/eventuais")
public class SaidasEventuaisController {

    @Autowired
    private SaidasEventuaisRepository repository;

    @Autowired
    private SaidasRecorrentesRepository saidasRecorrentesRepository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<Object> getAllByUserId(@PathVariable Integer userId) {
        try {
            List<SaidasEventuais> eventuaisList = repository.findAllByIdUsuario(userId);
            List<SaidasRecorrentes> recorrentesList = saidasRecorrentesRepository.findAllByIdUsuario(userId);

            List<Object> combinedList = new ArrayList<>();
            combinedList.addAll(eventuaisList);
            combinedList.addAll(recorrentesList);

            if (combinedList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (Object entry : combinedList) {
                    if (entry instanceof SaidasEventuais) {
                        SaidasEventuais eventuais = (SaidasEventuais) entry;
                        System.out.println("ID: " + eventuais.getId());
                        System.out.println("Descrição: " + eventuais.getDescricao());
                        System.out.println("Valor: " + eventuais.getValor());
                        System.out.println("Frequência: " + eventuais.getFrequencia());
                        System.out.println("Data: " + eventuais.getData());
                        System.out.println("ID do Usuário: " + eventuais.getIdUsuario());
                    } else if (entry instanceof SaidasRecorrentes) {
                        SaidasRecorrentes recorrentes = (SaidasRecorrentes) entry;
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
    public ResponseEntity<List<SaidasEventuais>> getByUserIdAndMonthYear(@PathVariable Integer userId, @PathVariable int year, @PathVariable int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            List<SaidasEventuais> walletList = repository.findByIdUsuarioAndDataBetween(userId, startDate, endDate);
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
    public ResponseEntity<SaidasEventuais> create(@RequestBody SaidasEventuais entradaEventual) {
        try {
            SaidasEventuais savedEntradaEventual = repository.save(entradaEventual);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntradaEventual);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

