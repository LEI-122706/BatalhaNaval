# Release checklist

## Testing Checklists

### S1 Release checklist
* [passed] C1 Reports @tiagocandeias
    * Gerar relatórios desempenho
    * Validar integridade dados
    * Confirmar diretoria correta


### S2 Automated tests checklist
* [passed] C2 Unit Tests @tiagocandeias
    * Executar testes unitários
    * Verificar anotações Allure
    * Validar fluxos principais


## Unit tests

### S3 Ship test cases
* [passed] C3 BargeTest @tiagocandeias
    tags: unit-tests, test-cases
    * Criar objeto Barge
    * Validar capacidade carga
    * Testar limite excedido

* [passed] C4 CaravelTest @tiagocandeias
    tags: unit-tests, test-cases
    * Instanciar Caravel
    * Simular navegação adversa
    * Calcular velocidade correta

* [passed] C5 CarrackTest @tiagocandeias
    tags: unit-tests, test-cases
    * Criar Carrack decks
    * Verificar número decks
    * Validar remoção inválida

* [passed] C6 FrigateTest @tiagocandeias
    tags: unit-tests, test-cases
    * Criar Frigate armada
    * Testar disparo canhões
    * Confirmar estado combate

* [passed] C7 GalleonTest @tiagocandeias
    tags: unit-tests, test-cases
    * Inicializar Galleon
    * Atribuir tripulação
    * Calcular alcance vento


### S4 Task test case
* [passed] C8 TaskTest @tiagocandeias
    tags: unit-tests, test-cases
    * Criar nova tarefa
    * Marcar como concluída
    * Validar dependências


