package com.escola.util.config;

import com.escola.util.controller.help.PageableHelp;
import com.escola.util.controller.help.SortInput;
import com.escola.util.controller.help.SortOrder;
import com.escola.util.model.entity.Municipio;
import com.escola.util.model.request.MunicipioRequest;
import com.escola.util.service.MunicipioService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    MunicipioService municipioService;
    PageableHelp pageableHelp;

    @Override
    public void run(String... args) throws IOException {
        log.info("Iniciando a verificação de dados iniciais...");
        carregarMunicipios();
        log.info("Verificação de dados iniciais concluída.");
    }

    private void carregarMunicipios() throws IOException {
        log.info("Carregar municipios");
        SortInput sortByCodigio = new SortInput("codigo", SortOrder.asc);
        Pageable pageable = pageableHelp.getPageable(Optional.of(0), Optional.of(1), Optional.of(List.of(sortByCodigio)));
        Optional<Page<Municipio>> byFiltro = municipioService.findByFiltro("", pageable);
        if (byFiltro.isPresent() && byFiltro.get().isEmpty()) {
            Optional<List<MunicipioRequest>> allJason = municipioService.findAllJason();
            allJason.ifPresent(municipios -> municipios.forEach(entity -> municipioService.salvar(entity)));
        }

    }

}