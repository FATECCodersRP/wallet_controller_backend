package com.example.wallet_controller.controller.entradas_recorrentes;

import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentes;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("entradas/recorrentes")
public class EntradasRecorrentesController {

    @Autowired
    private EntradasRecorrentesRepository repository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<List<EntradasRecorrentes>> getAllByUserId(@PathVariable Integer userId) {
        try {
            List<EntradasRecorrentes> walletList = repository.findAllByIdUsuario(userId);
            if (walletList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (EntradasRecorrentes wallet : walletList) {
                    System.out.println("ID: " + wallet.getId());
                    System.out.println("Descrição: " + wallet.getDescricao());
                    System.out.println("Valor: " + wallet.getValor());
                    System.out.println("Frequência: " + wallet.getFrequencia());
                    System.out.println("Data: " + wallet.getData());
                    System.out.println("ID do Usuário: " + wallet.getIdUsuario());
                }
                return ResponseEntity.ok().body(walletList);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("geral/{userId}/{year}/{month}")
    public ResponseEntity<List<EntradasRecorrentes>> getByUserIdAndMonthYear(@PathVariable Integer userId, @PathVariable int year, @PathVariable int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            List<EntradasRecorrentes> walletList = repository.findByIdUsuarioAndDataBetween(userId, startDate, endDate);
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
    public ResponseEntity<EntradasRecorrentes> create(@RequestBody EntradasRecorrentes entradasRecorrentes) {
        try {
            EntradasRecorrentes savedEntradasEventuais = repository.save(entradasRecorrentes);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntradasEventuais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}