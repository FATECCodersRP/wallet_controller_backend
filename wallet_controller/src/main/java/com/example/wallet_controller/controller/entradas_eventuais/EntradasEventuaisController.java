package com.example.wallet_controller.controller.entradas_eventuais;

import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuais;
import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuaisRepository;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentes;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentesRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("entradas/eventuais")
@CrossOrigin(origins = "http://localhost:3000")
public class EntradasEventuaisController {

    @Autowired
    private EntradasEventuaisRepository eventuaisRepository;

    @Autowired
    private EntradasRecorrentesRepository recorrentesRepository;

    @GetMapping("geral/{userId}")
    public ResponseEntity<Object> getAllByUserId(@PathVariable Integer userId) {
        try {
            // Chamada ajustada para utilizar a view
            List<EntradasEventuais> eventuaisList = eventuaisRepository.findAllByUserId(userId);
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

    @Autowired
    private EntityManager entityManager;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody EntradasEventuais entradaEventual) {
        try {
            // Verifica se todos os campos necessários estão presentes
            if (entradaEventual.getDescricao() == null || entradaEventual.getValor() == null ||
                    entradaEventual.getFrequencia() == null || entradaEventual.getData() == null ||
                    entradaEventual.getIdUsuario() == null) {
                return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
            }

            // Chama a procedure para inserir a entrada eventual
            BigDecimal newId = (BigDecimal) entityManager
                    .createNativeQuery("EXEC sp_insert_entrada_eventual :descricao, :valor, :frequencia, :data, :idUsuario")
                    .setParameter("descricao", entradaEventual.getDescricao())
                    .setParameter("valor", entradaEventual.getValor())
                    .setParameter("frequencia", entradaEventual.getFrequencia())
                    .setParameter("data", entradaEventual.getData())
                    .setParameter("idUsuario", entradaEventual.getIdUsuario())
                    .getSingleResult(); // Espera um único valor (o ID gerado, no caso, BigDecimal)

            // Se o retorno for BigDecimal, converta para Integer (caso necessário)
            Integer newIdInt = newId.intValue();  // Converte BigDecimal para Integer, caso necessário

            // Retorna o ID recém-gerado como resposta
            return ResponseEntity.status(HttpStatus.CREATED).body("ID gerado: " + newIdInt);
        } catch (Exception e) {
            // Log de erro para diagnóstico detalhado
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }


}