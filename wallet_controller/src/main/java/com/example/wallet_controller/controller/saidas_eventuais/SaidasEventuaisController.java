package com.example.wallet_controller.controller.saidas_eventuais;

import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuais;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuaisRepository;
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
import java.util.List;

@RestController
@RequestMapping("saidas/eventuais")
public class SaidasEventuaisController {

    @Autowired
    private SaidasEventuaisRepository repository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<List<SaidasEventuais>> getAllByUserId(@PathVariable Integer userId) {
        try {
            List<SaidasEventuais> walletList = repository.findAllByIdUsuario(userId);
            if (walletList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (SaidasEventuais wallet : walletList) {
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

