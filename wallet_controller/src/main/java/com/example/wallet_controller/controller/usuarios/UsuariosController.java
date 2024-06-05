package com.example.wallet_controller.controller.usuarios;

import com.example.wallet_controller.error.UsuarioException;
import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuais;
import com.example.wallet_controller.wallet.entradasEventuais.EntradasEventuaisRepository;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentes;
import com.example.wallet_controller.wallet.entradasRecorrentes.EntradasRecorrentesRepository;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuais;
import com.example.wallet_controller.wallet.saidasEventuais.SaidasEventuaisRepository;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentes;
import com.example.wallet_controller.wallet.saidasRecorrentes.SaidasRecorrentesRepository;
import com.example.wallet_controller.wallet.usuarios.Usuarios;
import com.example.wallet_controller.wallet.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository repository;
    @Autowired
    private EntradasEventuaisRepository entradasEventuaisRepository;
    @Autowired
    private EntradasRecorrentesRepository entradasRecorrentesRepository;
    @Autowired
    private SaidasEventuaisRepository saidasEventuaisRepository;
    @Autowired
    private SaidasRecorrentesRepository saidasRecorrentesRepository;

    @GetMapping
    public ResponseEntity<List<Usuarios>> getAll() {
        try {
            List<Usuarios> usuarioList = repository.findAll();
            if (usuarioList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                for (Usuarios usuario : usuarioList) {
                    System.out.println("ID: " + usuario.getId());
                    System.out.println("Nome: " + usuario.getNome());
                    System.out.println("Email: " + usuario.getEmail());
                    System.out.println("Senha: " + usuario.getSenha());
                    System.out.println("ADM: " + usuario.getAdm());
                }
                return ResponseEntity.ok().body(usuarioList);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getById(@PathVariable Integer id) {
        try {
            Optional<Usuarios> usuario = repository.findById(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok().body(usuario.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getByNome(@PathVariable String nome) {
        try {
            List<Usuarios> usuarios = repository.findByNome(nome);
            if (usuarios.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else if (usuarios.size() == 1) {
                Usuarios usuario = usuarios.get(0);
                return ResponseEntity.ok().body(usuario);
            } else {
                return ResponseEntity.ok().body(usuarios);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody Usuarios novoUsuario) {
        try {
            Optional<Usuarios> usuarioExistente = repository.findByEmail(novoUsuario.getEmail());

            if (usuarioExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este email.");
            }

            // Se não existir um usuário com o mesmo email, prosseguir com a inserção
            Usuarios usuarioInserido = repository.save(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioInserido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dadosLogin) {
        try {
            String email = dadosLogin.get("email");
            String senha = dadosLogin.get("senha");

            if (email == null || senha == null) {
                return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
            }

            Optional<Usuarios> usuarioExistente = repository.findByEmail(email);

            if (usuarioExistente.isPresent()) {
                Usuarios usuario = usuarioExistente.get();
                // Verifica se a senha fornecida está correta
                if (usuario.getSenha().equals(senha)) {
                    return ResponseEntity.ok(usuario);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer login.");
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            System.out.println("Iniciando exclusão do usuário com ID: " + id);
            Optional<Usuarios> usuarioOptional = repository.findById(id);

            if (usuarioOptional.isEmpty()) {
                System.out.println("Usuário não encontrado com ID: " + id);
                return ResponseEntity.notFound().build();
            }

            Usuarios usuario = usuarioOptional.get();

            // Capturando informações sobre o usuário antes de excluí-lo
            String usuarioDeletadoInfo = "Usuário deletado: " + usuario.toString();

            // Excluindo entradas_eventuais associadas ao usuário
            System.out.println("Excluindo entradas_eventuais associadas ao usuário com ID: " + usuario.getId());
            List<EntradasEventuais> entradasEventuaisDeletadas = entradasEventuaisRepository.deleteByIdUsuario(usuario.getId());

            // Excluindo entradas_recorrentes associadas ao usuário
            System.out.println("Excluindo entradas_recorrentes associadas ao usuário com ID: " + usuario.getId());
            List<EntradasRecorrentes> entradasRecorrentesDeletadas = entradasRecorrentesRepository.deleteByIdUsuario(usuario.getId());

            // Excluindo saidas_eventuais associadas ao usuário
            System.out.println("Excluindo saidas_eventuais associadas ao usuário com ID: " + usuario.getId());
            List<SaidasEventuais> saidasEventuaisDeletadas = saidasEventuaisRepository.deleteByIdUsuario(usuario.getId());

            // Excluindo saidas_recorrentes associadas ao usuário
            System.out.println("Excluindo saidas_recorrentes associadas ao usuário com ID: " + usuario.getId());
            List<SaidasRecorrentes> saidasRecorrentesDeletadas = saidasRecorrentesRepository.deleteByIdUsuario(usuario.getId());

            // Removendo o usuário e salvando suas informações antes de excluí-lo completamente
            repository.delete(usuario);

            System.out.println("Usuário excluído com sucesso.");

            // Montando a resposta com as informações deletadas
            Map<String, Object> response = new HashMap<>();

            // Criando objeto para representar o usuário deletado
            Map<String, Object> usuarioDeletado = new HashMap<>();
            usuarioDeletado.put("id", usuario.getId());
            usuarioDeletado.put("nome", usuario.getNome());
            usuarioDeletado.put("email", usuario.getEmail());
            usuarioDeletado.put("senha", usuario.getSenha());
            response.put("usuarioDeletado", usuarioDeletado);

            response.put("entradasEventuaisDeletadas", entradasEventuaisDeletadas);
            response.put("entradasRecorrentesDeletadas", entradasRecorrentesDeletadas);
            response.put("saidasEventuaisDeletadas", saidasEventuaisDeletadas);
            response.put("saidasRecorrentesDeletadas", saidasRecorrentesDeletadas);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a exclusão do usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> update(@PathVariable Integer id, @RequestBody Usuarios usuarioDetails) {
        try {
            Optional<Usuarios> usuario = repository.findById(id);
            if (usuario.isPresent()) {
                Usuarios updatedUsuario = usuario.get();
                updatedUsuario.setNome(usuarioDetails.getNome());
                updatedUsuario.setEmail(usuarioDetails.getEmail());
                updatedUsuario.setSenha(usuarioDetails.getSenha());
                updatedUsuario.setAdm(usuarioDetails.getAdm());
                repository.save(updatedUsuario);
                return ResponseEntity.ok().body(updatedUsuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}