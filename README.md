# 🏦 Banque gestion - Gestion des Transactions Bancaires

Application bancaire pour la gestion des clients, comptes et transactions avec détection automatique des anomalies.

---

## 📌 Description

Système développé pour **Banque Al Baraka** permettant de :
- Gérer les clients et leurs comptes (courant/épargne)
- Enregistrer et analyser les transactions
- Détecter les anomalies financières
- Générer des rapports statistiques

---

## 🛠️ Technologies

- **Java 17** (Records, Sealed Classes, Stream API)
- **JDBC** (PostgreSQL)
- **Architecture en couches** (Entity, DAO, Service, UI)

---

## 📦 Installation

##### Étape 1 : Cloner le projet
```bash
git clone https://github.com/WissamDouskary/bankapp-v2.git
cd banque-al-baraka
```

##### Étape 2 : Créer la base de données

**MySQL :**
```sql
CREATE DATABASE bankappv2;
USE bankappv2;
```

**PostgreSQL :**
```sql
CREATE DATABASE banque_al_baraka;
\c banque_al_baraka
```

##### Étape 3 : Créer les tables
```sql
CREATE TABLE Client (
    id VARCHAR(36) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Compte (
    id VARCHAR(36) PRIMARY KEY,
    numero INT UNIQUE NOT NULL,
    solde DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    idClient VARCHAR(36) NOT NULL,
    typeCompte VARCHAR(20) NOT NULL CHECK (typeCompte IN ('COURANT', 'EPARGNE')),
    decouvertAutorise DECIMAL(10, 2),
    tauxInteret DECIMAL(5, 2),
    FOREIGN KEY (idClient) REFERENCES Client(id) ON DELETE CASCADE
);

CREATE TABLE Transaction (
    id VARCHAR(36) PRIMARY KEY,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    montant DECIMAL(15, 2) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('VERSEMENT', 'RETRAIT', 'VIREMENT')),
    lieu VARCHAR(100),
    idCompte VARCHAR(36) NOT NULL,
    recieverId VARCHAR(36),
    FOREIGN KEY (idCompte) REFERENCES Compte(id) ON DELETE CASCADE
);

CREATE INDEX idx_compte_client ON Compte(idClient);
CREATE INDEX idx_transaction_compte ON Transaction(idCompte);
CREATE INDEX idx_transaction_date ON Transaction(date);
```

##### Étape 4 : Configurer la connexion

Créer `db.properties` :
```properties

# PostgreSQL (alternative)
# db.url=jdbc:postgresql://localhost:5432/banque_al_baraka
# db.user=postgres
# db.password=votre_mot_de_passe
# db.driver=org.postgresql.Driver
```

##### Étape 5 : Compiler et exécuter
```bash
# Compiler
javac -d bin src/**/*.java

# Créer le JAR
jar cfm BanqueAlBaraka.jar MANIFEST.MF -C bin .

# Exécuter
java -jar BanqueAlBaraka.jar
```

---

## 📂 Structure du Projet

```
src/
├── entities/
│   ├── Client.java              # Record : id, nom, email
│   ├── Compte.java              # Sealed class abstraite
│   ├── CompteCourant.java       # Compte avec découvert
│   ├── CompteEpargne.java       # Compte avec taux d'intérêt
│   ├── Transaction.java         # Record : id, date, montant, type, lieu
│   └── enums/
│       └── TransactionType.java # VERSEMENT, RETRAIT, VIREMENT
│
├── dao/
│   ├── ClientDAO.java           # CRUD Client
│   ├── CompteDAO.java           # CRUD Compte
│   └── TransactionDAO.java      # CRUD Transaction
│   └── impl
│       └──  ClientDAOimlp.java
│       └── CompteDAOimpl.java
│       └── TransactionDAOimpl.java
│
├── services/
│   ├── ClientService.java       # Logique métier clients
│   ├── CompteService.java       # Logique métier comptes
│   ├── TransactionService.java  # Logique métier transactions
│   └── RapportService.java      # Génération de rapports
│
├── ui/
│   └── Menu.java                # Interface console
│
└── config/
    ├── DatabaseConnection.java  # Connexion JDBC
```

---

## ✨ Fonctionnalités

### 👥 Gestion des Clients
- Créer, modifier, supprimer un client
- Rechercher par ID ou nom
- Lister tous les clients

### 💳 Gestion des Comptes
- Créer un compte courant (avec découvert autorisé)
- Créer un compte épargne (avec taux d'intérêt)
- Mettre à jour les paramètres
- Calculer les intérêts automatiquement

### 💰 Gestion des Transactions
- Enregistrer versements, retraits, virements
- Consulter l'historique par compte
- Filtrer par montant, type, date, lieu
- Calculer statistiques et moyennes

### 🔍 Détection d'Anomalies
- Transactions > 10 000 €
- Transactions dans des lieux inhabituels
- Fréquence excessive d'opérations
- Comptes inactifs

### 📊 Rapports
- Top 5 clients par solde
- Rapport mensuel des transactions
- Volume total par type
- Comptes inactifs

---

## 🎯 Modèle de Données

### Client (Record)
```java
public record Client(String id, String nom, String email) {
    public Client(String nom, String email) {
        this(UUID.randomUUID().toString(), nom, email);
    }
}
```

### Compte (Sealed Class)
```java
public sealed abstract class Compte permits CompteCourant, CompteEpargne {
    private String id;
    private int numero;
    private double solde;
    private String idClient;
    private String typeCompte;
    // Getters, setters, constructors...
}
```

### CompteCourant
```java
public final class CompteCourant extends Compte {
    private double decouvertAutorise;
    // Constructors, getters, setters...
}
```

### CompteEpargne
```java
public final class CompteEpargne extends Compte {
    private double tauxInteret;
    
    public void appliquerInteret() {
        double interet = getSolde() * tauxInteret / 100;
        setSolde(getSolde() + interet);
    }
}
```

### Transaction (Record)
```java
public record Transaction(
    String id, 
    LocalDateTime date, 
    double montant, 
    TransactionType type, 
    String lieu, 
    String idCompte, 
    String recieverId
) {
    public Transaction(double montant, TransactionType type, 
                      String lieu, String idCompte, String recieverId) {
        this(UUID.randomUUID().toString(), LocalDateTime.now(), 
             montant, type, lieu, idCompte, recieverId);
    }
}
```

---

## 🚀 Utilisation

##### Lancer l'application
```bash
java -jar bankapp-v2.jar
```

##### Menu Principal
```
===== BANQUE MENU =====
1. Gestion des clients
2. Gestion des comptes
3. Gestion des transactions
4. Analyses et rapports
5. Alertes
0. Quitter
Votre choix: 
```

---
# 🏦 Banque app - Gestion des Transactions Bancaires

Application bancaire pour la gestion des clients, comptes et transactions avec détection automatique des anomalies.

---

## 📌 Description

Système développé pour **Banque App** permettant de :
- Gérer les clients et leurs comptes (courant/épargne)
- Enregistrer et analyser les transactions
- Détecter les anomalies financières
- Générer des rapports statistiques

---

## 🛠️ Technologies

- **Java 17** (Records, Sealed Classes, Stream API)
- **JDBC** (MySQL ou PostgreSQL)
- **Architecture en couches** (Entity, DAO, Service, UI)

---

## 📦 Installation

##### Étape 1 : Cloner le projet
```bash
git clone https://github.com/votre-username/banque-al-baraka.git
cd banque-al-baraka
```

##### Étape 2 : Créer la base de données

**MySQL :**
```sql
CREATE DATABASE banque_al_baraka;
USE banque_al_baraka;
```

**PostgreSQL :**
```sql
CREATE DATABASE banque_al_baraka;
\c banque_al_baraka
```

##### Étape 3 : Créer les tables
```sql
CREATE TABLE Client (
    id VARCHAR(36) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Compte (
    id VARCHAR(36) PRIMARY KEY,
    numero INT UNIQUE NOT NULL,
    solde DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    idClient VARCHAR(36) NOT NULL,
    typeCompte VARCHAR(20) NOT NULL CHECK (typeCompte IN ('COURANT', 'EPARGNE')),
    decouvertAutorise DECIMAL(10, 2),
    tauxInteret DECIMAL(5, 2),
    FOREIGN KEY (idClient) REFERENCES Client(id) ON DELETE CASCADE
);

CREATE TABLE Transaction (
    id VARCHAR(36) PRIMARY KEY,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    montant DECIMAL(15, 2) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('VERSEMENT', 'RETRAIT', 'VIREMENT')),
    lieu VARCHAR(100),
    idCompte VARCHAR(36) NOT NULL,
    recieverId VARCHAR(36),
    FOREIGN KEY (idCompte) REFERENCES Compte(id) ON DELETE CASCADE
);

CREATE INDEX idx_compte_client ON Compte(idClient);
CREATE INDEX idx_transaction_compte ON Transaction(idCompte);
CREATE INDEX idx_transaction_date ON Transaction(date);
```

##### Étape 4 : Configurer la connexion

Créer `db.properties` :
```properties
# MySQL
db.url=jdbc:mysql://localhost:3306/banque_al_baraka
db.user=root
db.password=votre_mot_de_passe
db.driver=com.mysql.cj.jdbc.Driver

# PostgreSQL (alternative)
# db.url=jdbc:postgresql://localhost:5432/banque_al_baraka
# db.user=postgres
# db.password=votre_mot_de_passe
# db.driver=org.postgresql.Driver
```

##### Étape 5 : Compiler et exécuter
```bash
# Compiler
javac -d bin src/**/*.java

# Créer le JAR
jar cfm BanqueAlBaraka.jar MANIFEST.MF -C bin .

# Exécuter
java -jar BanqueAlBaraka.jar
```

---

## 📂 Structure du Projet

```
src/
├── entities/
│   ├── Client.java              # Record : id, nom, email
│   ├── Compte.java              # Sealed class abstraite
│   ├── CompteCourant.java       # Compte avec découvert
│   ├── CompteEpargne.java       # Compte avec taux d'intérêt
│   ├── Transaction.java         # Record : id, date, montant, type, lieu
│   └── enums/
│       └── TransactionType.java # VERSEMENT, RETRAIT, VIREMENT
│
├── dao/
│   ├── ClientDAO.java           # CRUD Client
│   ├── CompteDAO.java           # CRUD Compte
│   └── TransactionDAO.java      # CRUD Transaction
│
├── services/
│   ├── ClientService.java       # Logique métier clients
│   ├── CompteService.java       # Logique métier comptes
│   ├── TransactionService.java  # Logique métier transactions
│   └── RapportService.java      # Génération de rapports
│
├── ui/
│   └── Menu.java                # Interface console
│
└── utils/
    ├── DatabaseConnection.java  # Connexion JDBC
    ├── DateUtils.java           # Gestion des dates
    └── ValidationUtils.java     # Validation des saisies
```

---

## ✨ Fonctionnalités

### 👥 Gestion des Clients
- Créer, modifier, supprimer un client
- Rechercher par ID ou nom
- Lister tous les clients

### 💳 Gestion des Comptes
- Créer un compte courant (avec découvert autorisé)
- Créer un compte épargne (avec taux d'intérêt)
- Mettre à jour les paramètres
- Calculer les intérêts automatiquement

### 💰 Gestion des Transactions
- Enregistrer versements, retraits, virements
- Consulter l'historique par compte
- Filtrer par montant, type, date, lieu
- Calculer statistiques et moyennes

### 🔍 Détection d'Anomalies
- Transactions > 10 000 €
- Transactions dans des lieux inhabituels
- Fréquence excessive d'opérations
- Comptes inactifs

### 📊 Rapports
- Top 5 clients par solde
- Rapport mensuel des transactions
- Volume total par type
- Comptes inactifs

---

## 🎯 Modèle de Données

### Client (Record)
```java
public record Client(String id, String nom, String email) {
    public Client(String nom, String email) {
        this(UUID.randomUUID().toString(), nom, email);
    }
}
```

### Compte (Sealed Class)
```java
public sealed abstract class Compte permits CompteCourant, CompteEpargne {
    private String id;
    private int numero;
    private double solde;
    private String idClient;
    private String typeCompte;
    // Getters, setters, constructors...
}
```

### CompteCourant
```java
public final class CompteCourant extends Compte {
    private double decouvertAutorise;
    // Constructors, getters, setters...
}
```

### CompteEpargne
```java
public final class CompteEpargne extends Compte {
    private double tauxInteret;
    
    public void appliquerInteret() {
        double interet = getSolde() * tauxInteret / 100;
        setSolde(getSolde() + interet);
    }
}
```

### Transaction (Record)
```java
public record Transaction(
    String id, 
    LocalDateTime date, 
    double montant, 
    TransactionType type, 
    String lieu, 
    String idCompte, 
    String recieverId
) {
    public Transaction(double montant, TransactionType type, 
                      String lieu, String idCompte, String recieverId) {
        this(UUID.randomUUID().toString(), LocalDateTime.now(), 
             montant, type, lieu, idCompte, recieverId);
    }
}
```

---

## 🚀 Utilisation

##### Lancer l'application
```bash
java -jar BanqueAlBaraka.jar
```

##### Menu Principal
```
1. Gestion des clients
2. Gestion des comptes  
3. Gestion des transactions
4. Rapports et analyses
5. Détection d'anomalies
6. Quitter
```

## 👨‍💻 Auteur

Développé par **Wissam** pour **Banque App**

---

## 📄 License

MIT License
## 📋 Prérequis

- Java 17+
- MPostgreSQL 13+
- Git

---
==