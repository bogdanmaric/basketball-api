# Basketball API

API za obradu i prikaz osnovne i napredne statistike košarkaša, na osnovu njihovih rezultat sa utakmicama.

## Sadržaj

- [Uvod](#uvod)
- [Funkcionalnost](#funkcionalnost)
- [Početak](#početak)
  - [Opis okruženja](#opis-okruženja)
  - [Kako uraditi build](#kako-uraditi-build)
  - [Kako pokrenuti projekat](#kako-pokrenuti-projekat)
- [Korišćenje](#korišćenje)

## Uvod

Na osnovu odluke svetske košarkaške organizacije (FIBA), kako bi pomogli afričkim selekcijama da dođu do boljih rezultata na sveckim prvenstvima u košarci, kreiran je softver za praćenje učinka košarkaša. Time je kreiran Basketball API projekat koji omogućava korisniku da dobije osnovne i napredne statističke podatke o određenom igraču.

## Funkcionalnost

Vraća osnovne i napredne statističke podatke o konkretnom igraču, na osnovu zahtevanog imena. Osnovni i napredni statistički podaci koje korisnik dobija od API kao odgovor su:
  - prosek pogođenih slobodnih bacanja
  - prosek upućenih slobodnih bacanja
  - prosek uspešnosti slobodnih bacanja, u procentima
  - prosek pogođenih šuteva za dva poena
  - prosek upućenih šuteva za dva poena
  - prosek uspešnosti šuta za dva poena, u procentima
  - prosek pogođenih šuteva za tri poena
  - prosek upućenih šuteva za tri poena
  - prosek uspešnosti šuta za tri poena, u procentima
  - prosek poena
  - prosek skokova
  - prosek blokada
  - prosek asistencija
  - prosek oduzetih lopti
  - prosek izgubljenih lopti
  - prosečan indeks uspešnosti
  - prosek efektivnog šuta iz igre, u procentima
  - prosek stvarnog šuta, u procentima
  - prosek Holingerovog odnosa asistencija, u procentima

# Početak

Da bi ste započeli da projektom Basketball-api, potrebno je ispratite korake u nastavku.

## Opis okruženja

- Java JDK 19 ili novije
- Apache Maven 3.1.5 ili novije
- Spring Boot 3.1.5 ili novije

## Kako uraditi build

1. Klonirati repozitorijum: `git clone https://github.com/bogdanmaric/basketball-api.git`
2. Korišćenjem terminala, uđite u direktorijum projekta: `cd basketball-api`
3. Pokrenuti build: `mvn clean install`

## Kako pokrenuti projekat

Nakon izvršenja build projekta, izvršiti sledeću komandu u konzoli:  
`java -jar target/basketball-api-0.0.1-SNAPSHOT.jar`

## Lista tehnologija

Tehnologije koje su korišćene u projektu su:
- Spring Boot: radni okvir za kreiranje samostalne Spring aplikacije
- Maven: alat za izgradnju projekta i upravljanje zavisnicama
- H2 baza podataka: in-memory baza podataka.
- Lambok: biblioteka zasnovana na anotacijama za kraće pisanje getera, setera i konstruktora
- OpenCSV: biblioteka za lakše upravljanje CSV fajlovima. Pojednostavljuje proce čitanja/pisanja CSV podataka.
- Jakarta JSON API (JSON Processing): koristi se za rad sa JSON podacima u projektu.
- Yasson: biblioteka koja implementira Jakarta JSON Binding API. Konvertovanje Java objekta u/iz JSON formata.

# Korišćenje

- Pokrenuti aplikaciju, kako bi učitala sve statističke podatke igrača iz csv fajla i unela u H2 bazu podataka.
- U pregledaču upisati putanju: `http://localhost:8080/stats/player/{ime_igrača}`. Umesto {ime_igrača} napisati naziv igrača, na primer:
  `http://localhost:8080/stats/player/Luyanda Yohance`
- Nakon potvrde putanje, dobijamo odgovor u json formatu.


