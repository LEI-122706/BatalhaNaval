# Release checklist

## Testing Checklists

### S1 Release checklist
* [passed] C1 Reports @aribeiro
    * Gerar relatórios desempenho
    * Validar integridade dados
    * Confirmar diretoria correta


### S2 Automated tests checklist
* [passed] C2 Unit Tests @jsilva
    * Executar testes unitários
    * Verificar anotações Allure
    * Validar fluxos principais


## Unit tests

### S3 Ship test cases
* [passed] C3 BargeTest @jsilva
  tags: unit-tests, test-cases
    * Criar objeto Barge
    * Validar capacidade carga
    * Testar limite excedido

* [passed] C4 CaravelTest @jsilva
  tags: unit-tests, test-cases
    * Instanciar Caravel
    * Simular navegação adversa
    * Calcular velocidade correta

* [passed] C5 CarrackTest @aribeiro
  tags: unit-tests, test-cases
    * Criar Carrack decks
    * Verificar número decks
    * Validar remoção inválida

* [failed] C6 FrigateTest @aribeiro
  tags: unit-tests, test-cases
    * Criar Frigate armada
    * Testar disparo canhões (Falhou: NullPointerException)
    * Confirmar estado combate

* [passed] C7 GalleonTest @jsilva
  tags: unit-tests, test-cases
    * Inicializar Galleon
    * Atribuir tripulação
    * Calcular alcance vento


### S4 Task test case
* [skipped] C8 TaskTest @aribeiro
  tags: unit-tests, test-cases
    * (Skipped: Funcionalidade pendente de refatoração)
    * Criar nova tarefa
    * Marcar como concluída
    * Validar dependências