package com.escola.util.service.impl;

import com.escola.util.model.request.FileRequest;
import com.escola.util.service.FileStorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/**
 * Implementação do serviço para salvar arquivos a partir de uma string Base64.
 * Simula o comportamento do microserviço de armazenamento.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    String uploadDir;

    @Override
    public Mono<String> saveFile(FileRequest request) {
        try {
            // 1. Remove o prefixo "data:..." da string Base64, se existir
            String contentBase64 = request.contentFileBase64();
            if (contentBase64.contains(",")) {
                contentBase64 = contentBase64.substring(contentBase64.indexOf(",") + 1);
            }

            // 2. Decodifica a string Base64 em um array de bytes
            byte[] fileBytes = Base64.getDecoder().decode(contentBase64);

            // 3. Cria o diretório de destino se ele não existir
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 4. Gera um nome de arquivo único usando UUID
            String uniqueFileName = UUID.randomUUID().toString();
            Path filePath = uploadPath.resolve(uniqueFileName);

            // 5. Escreve os bytes no arquivo
            Files.write(filePath, fileBytes);

            // 6. Retorna o nome do aquivo.
            return Mono.just(uniqueFileName);
        } catch (IOException | IllegalArgumentException ex) {
            // Lida com erros de I/O ou decodificação de Base64 inválida
            return Mono.error(new RuntimeException("Não foi possível salvar o arquivo.", ex));
        }
    }

    /**
     * Encontra um arquivo pelo seu nome único, converte-o para Base64 e retorna a string.
     *
     * @param uniqueFileName O nome único do arquivo.
     * @return Um Mono com a string Base64 do arquivo.
     */
    @Override
    public Mono<String> getFileAsBase64(String uniqueFileName) {
        try {
            // Constrói o caminho completo para o arquivo
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);

            // Verifica se o arquivo existe
            if (!Files.exists(filePath)) {
                return Mono.error(new RuntimeException("Arquivo não encontrado: " + uniqueFileName));
            }

            // Lê todos os bytes do arquivo
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Converte os bytes para uma string Base64
            String base64String = Base64.getEncoder().encodeToString(fileBytes);

            return Mono.just(base64String);
        } catch (IOException ex) {
            // Lida com erros de I/O ao ler o arquivo
            return Mono.error(new RuntimeException("Não foi possível ler o arquivo: " + uniqueFileName, ex));
        }
    }

    /**
     * Exclui um arquivo do sistema de arquivos.
     *
     * @param uniqueFileName O nome único do arquivo a ser excluído.
     * @return Um Mono que emite 'true' se o arquivo foi excluído com sucesso, 'false' se não foi encontrado.
     */
    @Override
    public Mono<Boolean> deleteFile(String uniqueFileName) {
        try {
            // Constrói o caminho completo para o arquivo
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);

            // Verifica se o arquivo existe
            if (Files.exists(filePath)) {
                // Exclui o arquivo
                Files.delete(filePath);
                return Mono.just(true);
            } else {
                // O arquivo não existe, então retorna 'false'
                return Mono.just(false);
            }
        } catch (IOException ex) {
            // Lida com erros de I/O ao excluir o arquivo
            return Mono.error(new RuntimeException("Não foi possível excluir o arquivo: " + uniqueFileName, ex));
        }
    }
}
