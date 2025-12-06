# ♟️ Sistema de Jogo de Xadrez

Este projeto consiste na implementação de um **Sistema de Jogo de Xadrez** completo, desenvolvido como um projeto de estudos de **Programação Orientada a Objetos (POO)** em **Java**. O objetivo principal é aplicar e consolidar conceitos avançados de POO e estruturas de dados na construção de um sistema funcional e bem estruturado.

---

## 🚀 Funcionalidades Implementadas

O sistema implementa todas as regras básicas e especiais do jogo, garantindo uma experiência completa no console.

| **Categoria** | **Funcionalidades** |
| :--- | :--- |
| **Movimentação** | Movimento de todas as peças (Rei, Rainha, Torre, Bispo, Cavalo, Peão). |
| **Regras** | Lógica completa de **Xeque** e **Xeque-Mate**. |
| **Movimentos Especiais** | Implementação de **Roque (Castling)**, **En Passant** e **Promoção** de Peão. |
| **Controle de Jogo** | Alternância de turnos, contagem de movimentos e rastreamento de peças capturadas. |
| **Interface** | Exibição do tabuleiro no console com cores para distinguir peças e indicar movimentos possíveis. |
| **Defesa** | Tratamento de exceções (`BoardException`, `ChessException`) para garantir a integridade e a programação defensiva. |

---

## 💻 Tecnologias e Conceitos de POO

O projeto foi desenvolvido em **Java** e aplica diversos princípios de POO e estruturas de dados.

### Princípios de POO Aplicados

O design segue uma arquitetura baseada em **Camadas (Layers Pattern)**, separando:

- **Lógica do Tabuleiro** (`Board`)
- **Lógica do Xadrez** (`Chess`)
- **Interface do Usuário** (`UI`)

| **Conceito** | **Aplicação** |
| :--- | :--- |
| **Encapsulamento** | Uso de modificadores de acesso e métodos *getters* e *setters*. |
| **Herança e Polimorfismo** | Classes de peças (`Rook`, `King`, etc.) herdando de `ChessPiece` e sobrescrevendo métodos. |
| **Associações** | Relações entre `Board` e `Piece`. |
| **Exceções** | Criação de exceções personalizadas para validação de movimentos. |
| **Abstração** | Uso de classes e métodos abstratos, como `Piece.possibleMoves()`. |
| **Estruturas de Dados** | Matrizes para representar o tabuleiro e listas para rastrear peças e capturas. |

---

## ⚙️ Como Configurar e Rodar o Projeto

O projeto foi compilado com **Apache Maven** e empacotado em um **JAR executável (shaded JAR)**, incluindo todas as dependências.

### ✅ Pré-requisitos

- **Java Development Kit (JDK)** instalado.

### ▶️ Execução

1. **Baixe o arquivo JAR** (por exemplo, `JOGO-DE-XADREZ-1.0-SNAPSHOT-shaded.jar`) na pasta **Application exe** do repositório.
2. **Execute o jogo** no terminal dentro de Application exe:
   ```bash
   java -jar JOGO-DE-XADREZ-1.0-SNAPSHOT-shaded.jar
   ```
**Divirta-se jogando!** ♟️

---

### 🔨 Compilação a partir do Código-Fonte (Opcional)

Se preferir compilar o projeto manualmente:

1. **Pré-requisitos adicionais:**
   - **Git** instalado.
   - **Apache Maven** instalado.

2. **Clone o repositório:**
   ```bash
   git clone https://github.com/LF-ALUCARD/JOGO-DE-XADREZ.git
   cd JOGO-DE-XADREZ
   ```

3. **Compile o projeto com Maven:**
   ```bash
   mvn clean package
   ```
   O arquivo JAR será gerado no diretório `target/`.

---
