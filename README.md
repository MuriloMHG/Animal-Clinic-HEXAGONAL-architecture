# Animal-Clinic-HEXAGONAL-architecture
A animal clinic software testing my skills in software design using hexagonal architecture
# Sistema de Gerenciamento de Clínica Veterinária

Projeto desenvolvido em Java puro para demonstrar o uso de Arquitetura Hexagonal, também conhecida como Ports and Adapters.

## Objetivo

Modelar os principais casos de uso de uma clínica veterinária mantendo o domínio desacoplado da infraestrutura e da apresentação.

O pacote `com.clinica.dominio` não importa classes dos pacotes `infraestrutura` ou `apresentacao`.

## Estrutura do projeto

```txt
src/
└── com/
    └── clinica/
        ├── dominio/
        │   ├── modelo/
        │   ├── excecao/
        │   ├── porta/
        │   │   ├── entrada/
        │   │   └── saida/
        │   └── servico/
        ├── infraestrutura/
        │   └── adaptador/
        │       ├── persistencia/
        │       └── notificacao/
        ├── apresentacao/
        └── test/
```

## Decisões arquiteturais

### Domínio

O domínio contém as entidades principais do sistema:

- `Animal`
- `Veterinario`
- `Consulta`
- `TipoConsulta`
- `SituacaoConsulta`
- `SituacaoVeterinario`

As regras de estado ficam dentro das próprias entidades. Por exemplo, `Consulta` controla as transições entre `AGENDADA`, `REALIZADA` e `CANCELADA`, enquanto `Veterinario` controla sua disponibilidade.

### Portas de entrada

A porta de entrada `PortaAgendaConsulta` representa os casos de uso expostos pelo domínio:

- Agendar consulta
- Realizar consulta
- Cancelar consulta
- Obter histórico de um animal
- Obter agenda de um veterinário

A implementação concreta dessa porta é `ServicoAgendaConsulta`.

### Portas de saída

As portas de saída representam dependências externas que o domínio precisa usar sem conhecer suas implementações:

- `PortaAnimalRepositorio`
- `PortaVeterinarioRepositorio`
- `PortaConsultaRepositorio`
- `PortaNotificacaoTutor`

O serviço de domínio depende apenas dessas interfaces.

### Adaptadores

Os adaptadores implementam as portas de saída:

- `AnimalRepositorioMemoria`
- `VeterinarioRepositorioMemoria`
- `ConsultaRepositorioMemoria`
- `NotificacaoConsole`
- `NotificacaoCsv`

A troca entre `NotificacaoConsole` e `NotificacaoCsv` acontece em `Main.java`, sem alteração no domínio.

## Como compilar e executar

A partir da pasta `src`, execute:

```bash
find . -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out com.clinica.apresentacao.Main
```

No Windows PowerShell, dentro da pasta `src`, você pode usar:

```powershell
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
java -cp out com.clinica.apresentacao.Main
```

## Como executar os testes opcionais

A partir da pasta `src`, execute:

```bash
java -ea -cp out com.clinica.test.TestesDominio
```

No PowerShell:

```powershell
java -ea -cp out com.clinica.test.TestesDominio
```
## Observação

O arquivo `notificacoes.csv` é criado automaticamente ao executar o programa, quando o adaptador `NotificacaoCsv` for utilizado.
