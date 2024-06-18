package com.example.wallet_controller.controller.saidas_recorrentes;

import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentes;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("saidas/recorrentes")
@CrossOrigin(origins = "http://localhost:3000")
public class SaidasRecorrentesController {

    @Autowired
    private SaidasRecorrentesRepository repository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<List<SaidasRecorrentes>> getAllByUserId(@PathVariable Integer userId) {
        try {
            List<SaidasRecorrentes> walletList = repository.findAllByIdUsuario(userId);
            if (walletList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (SaidasRecorrentes wallet : walletList) {
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
    public ResponseEntity<List<SaidasRecorrentes>> getByUserIdAndMonthYear(@PathVariable Integer userId, @PathVariable int year, @PathVariable int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            List<SaidasRecorrentes> walletList = repository.findByIdUsuarioAndDataBetween(userId, startDate, endDate);
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
    public ResponseEntity<SaidasRecorrentes> create(@RequestBody SaidasRecorrentes entradaEventual) {
        try {
            SaidasRecorrentes savedEntradaEventual = repository.save(entradaEventual);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntradaEventual);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
