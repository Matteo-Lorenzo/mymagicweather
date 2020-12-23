## My Magic Weather

Matteo Lorenzo Bramucci, Agnese Bruglia
<p>
  <img src="https://github.com/Matteo-Lorenzo/progetti/blob/main/banner.png?raw=true">
    <h6 align="center">
      Siamo partiti da qui...
    </h6>
  </img>
</p>

<p>
  <img src="https://github.com/Matteo-Lorenzo/progetti/blob/main/mymagicweather.jpg?raw=true">
    <h6 align="center">
        ...prima evoluzione del nostro pensiero...
      </h6>
  </img>
</p>

# Descrizione generale

Il nostro applicativo è un RESTful Web Service, cioè un sistema software che, comunicando tramite il protocollo HTTP, è in grado di mettersi al servizio di un Client, come ad esempio un'applicazione, un sito web o Postman, e consentire agli utenti che vi si collegano di usufruire delle funzioni che mette a disposizione.

Il progetto implementa un servizio meteo che, a seconda dell’API scelta:
- calcola delle statistiche riguardanti i valori minimi, massimi, media e varianza della quantità di nuvolosità, temperatura e umidità nel periodo e nella località indicati;
- calcola gli andamenti che permettono di visualizzare tutte le informazioni, attuali oppure riguardanti uno storico di dati, relative a nuvolosità, temperatura e umidità nel periodo e nella località indicati, sulla base di un intervallo di campionamento specificato;
- permette di leggere o modificare le configurazioni dinamiche del servizio, ovvero la lista di città di cui si raccolgono i campioni.
&nbsp;

È stato implementato un archivio (**MeteoRepository**) per raccogliere i dati storici, acquisiti nel tempo, tramite chiamate al RESTful service online di OpenWeather.
Tale archivio si basa sulle funzionalità di un repository CRUD (Create, Read, Update, Delete). Viene popolato in maniera automatica grazie all'acquisizione temporizzata, che, a intervalli prefissati, chiama l’API di OpenWeather per acquisire le informazioni riguardanti la nuvolosità, temperatura e umidità per le città preimpostate, relative al momento in cui si effettua la chiamata.

A scopo dimostrativo, durante il periodo di sviluppo e testing dell'applicativo, sono stati raccolti i dati relativi a Montappone, Ancona, Milano, Parigi e Napoli, a partire dal giorno 12/12/2020 alle ore 15:00 al giorno 21/12/2020 alle ore 14:00.

La chiamata all’API di OpenWeather utilizzata nel nostro applicativo ha questa struttura:
http://api.openweathermap.org/data/2.5/weather?q={city%20name}&appid={API%20key}
- city name è il nome della città selezionata
- API key è il codice di accesso al servizio
  - Ad esempio richiamando attraverso il metodo HTTP GET la seguente API:
http://api.openweathermap.org/data/2.5/weather?q=Mountain%20View&appid=123456
si ottiene come risposta il seguente JSON:
```json
  {
  "coord": {
    "lon": -122.08,
    "lat": 37.39
  },
  "weather": [
    {
      "id": 800,
      "main": "Clear",
      "description": "clear sky",
      "icon": "01d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 282.55,
    "feels_like": 281.86,
    "temp_min": 280.37,
    "temp_max": 284.26,
    "pressure": 1023,
    "humidity": 100
  },
  "visibility": 16093,
  "wind": {
    "speed": 1.5,
    "deg": 350
  },
  "clouds": {
    "all": 1
  },
  "dt": 1560350645,
  "sys": {
    "type": 1,
    "id": 5122,
    "message": 0.0139,
    "country": "US",
    "sunrise": 1560343627,
    "sunset": 1560396563
  },
  "timezone": -25200,
  "id": 420006353,
  "name": "Mountain View",
  "cod": 200
  }
```
Significato dei campi della risposta ottenuta da OpenWeather:
<ul>
  <li>
      <code>coord</code>
      <ul>
        <li><code>coord.lon</code> City geo location, longitude</li>
        <li><code>coord.lat</code> City geo location, latitude</li>
      </ul>
  </li>
  <li>
      <code>weather</code> (more info Weather condition codes)
      <ul>
        <li><code>weather.id</code> Weather condition id</li>
        <li><code>weather.main</code> Group of weather parameters (Rain, Snow, Extreme etc.)</li>
        <li><code>weather.description</code> Weather condition within the group. You can get the output in your language. </li>
        <li><code>weather.icon</code> Weather icon id</li>
      </ul>
  </li>
  <li><code>base</code> Internal parameter
  </li>
  <li>
      <code>main</code>
      <ul>
        <li><code>main.temp</code> Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit. </li>
        <li><code>main.feels_like</code> Temperature. This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit. </li>
        <li><code>main.pressure</code> Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa</li>
        <li><code>main.humidity</code> Humidity, %</li>
        <li><code>main.temp_min</code> Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.</li>
        <li><code>main.temp_max</code> Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.</li>
        <li><code>main.sea_level</code> Atmospheric pressure on the sea level, hPa</li>
        <li><code>main.grnd_level</code> Atmospheric pressure on the ground level, hPa</li>
      </ul>
  </li>
  <li>
      <code>wind</code>
      <ul>
        <li><code>wind.speed</code> Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.</li>
        <li><code>wind.deg</code> Wind direction, degrees (meteorological)</li>
        <li><code>wind.gust</code> Wind gust. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour</li>
      </ul>
  </li>
  <li>
      <code>clouds</code>
      <ul>
        <li><code>clouds.all</code> Cloudiness, %</li>
      </ul>
  </li>
  <li>
      <code>rain</code>
      <ul>
        <li><code>rain.1h</code> Rain volume for the last 1 hour, mm</li>
        <li><code>rain.3h</code> Rain volume for the last 3 hours, mm</li>
      </ul>
  </li>
  <li>
      <code>snow</code>
      <ul>
        <li><code>snow.1h</code> Snow volume for the last 1 hour, mm</li>
        <li><code>snow.3h</code> Snow volume for the last 3 hours, mm</li>
      </ul>
  </li>
  <li><code>dt</code> Time of data calculation, unix, UTC
  </li>
  <li>
      <code>sys</code>
      <ul>
        <li><code>sys.type</code> Internal parameter</li>
        <li><code>sys.id</code> Internal parameter</li>
        <li><code>sys.message</code> Internal parameter</li>
        <li><code>sys.country</code> Country code (GB, JP etc.)</li>
        <li><code>sys.sunrise</code> Sunrise time, unix, UTC</li>
        <li><code>sys.sunset</code> Sunset time, unix, UTC</li>
      </ul>
  </li>
  <li><code>timezone</code> Shift in seconds from UTC
  </li>
  <li><code>id</code> City ID
  </li>
  <li><code>name</code> City name
  </li>
  <li><code>cod</code> Internal parameter
  </li>
</ul>


# Finalità del progetto
I possibili utilizzatori di questo servizio sono Aziende il cui business è in qualche modo legato alle condizioni meteo, soprattutto alla presenza o meno di nuvole.
Ad esempio: aziende produttrici di tende e tendaggi da sole, ombrelli e ombrelloni, agricoltori 2.0, parchi a tema, ecc... :-)

Nell'ottica di sviluppare un applicativo che possa avere una sua commercializzazione, si è prestata particolare attenzione alla cura del dettaglio, come ad esempio l'utilizzo diffuso di un oggetto della classe logger al posto del classico "System.out.println()". Questo ha permesso di formattare in maniera molto più precisa e ricca di informazioni l'output su console della descrizione delle diverse operazioni eseguite dal programma.

# Diagrammi UML
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwUseCase.jpg?raw=true">
    <h6 align="center">
        Use Case Diagram
      </h6>
  </img>
</p>

| Caso d'uso | Descrizione |
| --- | ---------- |
| Utente API | E' l'utente del nostro servizio |
| Statistiche | Endpoint del nostro servizio: Chiamata HTTP POST alle API del nostro applicativo specifiche per il calcolo delle "statistiche" |
| Andamenti | Endpoint del nostro servizio: Chiamata HTTP POST alle API del nostro applicativo specifiche per il ritorno di "andamenti" |
| Configurazioni | Endpoint del nostro servizio: Chiamata HTTP GET alle API del nostro applicativo per visualizzare le configurazioni attuali, Chiamata HTTP POST per modificarle |
| Calcolo delle Statistiche | Esecuzione delle statisctiche per una lista di città in un dato periodo utilizzando i campioni forniti dal selettore |
| Selezione dei Campioni reali | Selezione dei dati reali dall'archivio storico necessari al calcolo delle statistiche |
| Archivio dei Campioni reali | Database in memoria popolato dai dati presi da OpenWeather |
| Acquisizione temporizzata dei Campioni reali | Demone che, a periodi di tempo prestabiliti, invoca l'API di OpenWeather popolando l'archivio |
| Produzione degli andamenti Temporali | Produzione degli andamenti di una o più grandezze selezionate per una lista di città, in un dato periodo, con un intervallo dato |
| Campionamento con intervallo richiesto | Seleziona i valori, con l'intervallo di campionamento richiesto, dalla funzione ottenuta attraverso l'interpolatore. Integra il valore campionato con uno score di qualità dello stesso, assegnato in base alla distanza temporale dal dato reale più vicino |
| Interpolazione lineare dei dati reali | Genera una funzione lineare a tratti a partire dalla distribuzione dei campioni selezionati |
| Configuration file | Store su file system di un file JSON contenente le configurazioni |
| Amministratore | Si occupa di redigere e manutenere il file JSON delle configurazioni |
| OpenWeather | Servizio esterno che fornisce le informazioni metereologiche al nostro sistema |

&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwClassDiagram.jpg?raw=true">
    <h6 align="center">
        Class Diagram
      </h6>
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/model.jpg?raw=true">
    <h6 align="center">
        Class Diagram per il package model con l'ereditarietà tra Sample e Campione
      </h6>
      Si è preferito non implementare questa soluzione perchè avrebbe complicato la gestione del CRUD Repository richiedendo l'utilizzo di tavole collegate
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwLetturaConfigurazioni.jpg?raw=true">
    <h6 align="center">
        Sequence Diagram per la lettura delle configurazioni
      </h6>
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwModificaConfigurazioni.jpg?raw=true">
    <h6 align="center">
        Sequence Diagram per la modifica delle configurazioni
      </h6>
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwAndamenti.jpg?raw=true">
    <h6 align="center">
        Sequence Diagram per la richiesta degli andamenti
      </h6>
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwStatistiche.jpg?raw=true">
    <h6 align="center">
        Sequence Diagram per la richiesta delle statistiche
      </h6>
  </img>
</p>
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwRaccoltaDatiDaOpenWeather.jpg?raw=true">
    <h6 align="center">
        Sequence Diagram per la raccolta temporizzata dei campioni da OpenWeather
      </h6>
  </img>
</p>

# Come funziona

Le tre funzionalità principali del nostro applicativo sono: 
- Fornire le statistiche per una o più grandezze in un certo periodo scelto dall’utente;
- Fornire gli andamenti di una o più grandezze in un certo periodo di tempo e con un intervallo di campionamento forniti dall’utente;
- Leggere o modificare le configurazioni dinamiche predefinite.

**1:** La pipeline per fornire le statistiche è la seguente:
- La classe **StatsAPI** raccoglie la richiesta che l’utente ha effettuato tramite metodo HTTP POST controllando che questa sia stata scritta nella maniera corretta, cioè quella illustrata precedentemente nel README, poi si collega alla classe **RequestStats**;
- La classe **RequestStats** in un primo momento effettua il parsing della richiesta dell’utente, accede poi all'archivio interno, tramite **MeteoRepository**, eseguendo la 'SELECT' per il recupero dei campioni. Questi vengono passati alla classe **StatCalculator**;
- **MeteoRepository** è un’interfaccia che permette di utilizzare il database h2 in cui sono archiviati i dati recuperati con cadenza oraria dalla classe **Scheduler** che, chiamando le API del servizio OpenWeather, si collega a questo e salva i dati raccolti nel database;
- La classe **StatCalculator** elabora i dati che le sono stati forniti e calcola valori di massimo, minimo, media e varianza della/e grandezza/e richiesta/e dall’utente nel periodo scelto dallo stesso a partire dall’insieme di valori campionati dallo **Scheduler** nel periodo specificato. Infine ritorna questi valori alla classe **RequestStats**;
- La classe **RequestStats** costruisce una prima versione della risposta e la ritorna alla classe **StatsAPI**;
- La classe **StatsAPI** costruisce la risposta finale e la ritorna all’utente che aveva effettuato la richiesta iniziale.

**2:** La pipeline per fornire gli andamenti è la seguente:
- La classe **TrendsAPI** raccoglie la richiesta che l’utente ha effettuato tramite metodo HTTP POST controllando che questa sia stata scritta nella maniera corretta, cioè quella illustrata precedentemente nel README, poi si collega alla classe **RequestTrends**;
- La classe **RequestTrends** in un primo momento effettua il parsing della richiesta dell’utente, accede poi all'archivio interno, tramite **MeteoRepository**, eseguendo la 'SELECT' per il recupero dei campioni. Questi vengono passati alla classe **Interpolator**;
- **MeteoRepository** è un’interfaccia che permette di utilizzare il database h2 in cui sono archiviati i dati recuperati con cadenza oraria dalla classe **Scheduler** che, chiamando le API del servizio OpenWeather, si collega a questo e salva i dati raccolti nel database;
- La classe **Interpolator** ha il compito, partendo dall’insieme di valori campionati dallo **Scheduler** presi da OpenWeather, di:
  - effettuarne l’interpolazione, così da avere non più un insieme discreto di dati, ma un insieme continuo;
  - effettuarne un sotto-campionamento per isolare i valori richiesti dall’utente, fornendo infine anche la percentuale (“score”) di qualità del dato richiesto. Ciò significa: i valori richiesti dall’utente potrebbero coincidere con quelli che lo **Scheduler** ha salvato nel database con cadenza oraria, dunque in quel caso lo score di qualità sarà massimo, cioè 1,0; se invece i valori richiesti dall’utente non coincidono con i valori campionati dallo **Scheduler**, l’**Interpolator** permette di approssimare i primi a partire dai secondi, assegnandogli anche uno score, che parte da 0,5 (se il punto si trova esattamente nel mezzo dell'intervallo di campionamento) e aumenta fino ad arrivare ad 1,0 proporzionalmente all’avvicinarsi del punto a uno dei due estremi dell’intervallo in cui è compreso. Infine l’**Interpolator** ritorna i dati richiesti alla classe **RequestTrends**.
- La classe **RequestTrends** costruisce una prima versione della risposta e la ritorna alla classe **TrendsAPI**;
- La classe **TrendsAPI** costruisce la risposta finale e la ritorna all’utente che aveva effettuato la richiesta iniziale.

**3:** La pipeline per leggere le configurazioni dinamiche è la seguente:
- La classe **ConfigurationsAPI** raccoglie la richiesta che l’utente ha effettuato tramite metodo HTTP GET, poi si collega alla classe **Configurations**;
- La classe **Configurations** recupera le configurazioni attuali ritornandole come risposta alla classe **ConfigurationsAPI**. Questa classe prevede anche la gestione dell’eventuale assenza di un file di configurazioni: in questo caso vengono generate automaticamente delle configurazioni di default;
- La classe **ConfigurationsAPI** inoltra la risposta all’utente che aveva effettuato la richiesta iniziale.

**4:** La pipeline per la modifica delle configurazioni dinamiche è la seguente:
- La classe **ConfigurationsAPI** raccoglie la richiesta che l’utente ha effettuato tramite metodo HTTP POST controllando che questa sia stata scritta nella maniera corretta, cioè quella illustrata precedentemente nel README, poi effettua il parsing della richiesta di modifica dell’utente e infine la inoltra alla classe **Configurations**;
- La classe **Configurations** prima modifica le configurazioni presenti nel suo stato interno (attributo) e successivamente modifica il file contenente le stesse, la cui path è indicata all’interno di questa classe. Sia negli attributi interni che nel file di testo è tutto espresso in formato JSON. Infine inoltra un messaggio di feedback alla classe **ConfigurationsAPI**, che sarà positivo se la modifica ha avuto successo, oppure negativo nel caso in cui siano state sollevate delle eccezioni, come ad esempio se l’utente ha espresso la richiesta di modifica in un formato non riconosciuto;
- La classe **ConfigurationsAPI** inoltra il feedback all’utente che aveva effettuato la richiesta iniziale.

<ins>Da notare</ins> che l'applicativo, per settare tutti i suoi parametri di funzionamento, si serve di due diversi tipi di configurazioni:
- Configurazioni dinamiche, ovvero quelle che possono essere lette e modificate tramite API durante l'esecuzione del programma diventando subito operative. Di queste si trova una copia all'interno di un attributo della classe **Configurations**, la quale si occupa della lettura e scrittura delle stesse su file di testo così da renderle disponibili al momento di un nuovo avvio del programma. All'interno di queste configurazioni è contenuto l'array in formato JSON delle città utilizzate per la raccolta dati.
- Configurazioni statiche, ovvero quelle scritte all'interno del file application.properties, messo a disposizione da Spring. Questo file viene letto una sola volta all'avvio del programma e contiene le specifiche di carattere generale che indicano la struttura dell'ambiente di esecuzione (ad esempio: posizione del file di database, apikey per collegarsi a OpenWeather, ...).

Per una documentazione dettagliata ed esaustiva riguardo la struttura (package, classi, attributi, metodi) e il funzionamento del codice implementato, si consiglia di consultare la Javadoc messa disposizione.

# Come si usa
Clonando questo repository sul vostro computer e importando nell'IDE Eclipse il progetto MyMagicWeather sarete subito pronti a partire: infatti, nel pacchetto scaricato, oltre all'applicativo sono già presenti i file di configurazione predefiniti e il file di database contenente un cospicuo set di campioni su cui fare le prove. Una volta aperto Eclipse, per avviare il programma, basta selezionare MyMagicWeather nel proprio package explorer e dare il comando Run as -> Spring Boot App. L'avvio dell'applicazione è riconoscibile dalla comparsa del logo di Spring e di molte righe di informazioni scritte in formato logging.
L'applicativo espone i propri Endpoint sulla rete interna all'indirizzo localhost, sulla porta 8080.
Per usufruire delle sue funzionalità potete collegarvi alle rotte messe a disposizione con un'applicazione come Postman.

#### Modo di utilizzo degli endpoint dell'API di MyMagicWeather

- Endpoint per le statistiche
  ***localhost:8080/stats***
  L'API deve essere richiamata con il metodo HTTP POST il cui body è un oggetto JSON con il seguente formato:
  ```json
    {
      "cities": [
         "Milano",
         "Roma"
      ],
      "period": {
        "from": "2020-12-14 00:00:00",
        "to": "2020-12-16 24:00:00"
      },
      "type": "cloudiness"
    }
  ```
      "type" può assumere i valori: [all|cloudiness|temperature|humidity]
  
  Per le statistiche si ottiene come risposta il seguente JSON:
  ```json
  {
    "code": 0,
    "info": "Elapsed time 29ms",
    "time": 29,
    "result": [
        {
            "cityname": "Milano",
            "type": "cloudiness",
            "data": {
              "max": 80.0,
              "min": 30.0,
              "average": 55.0,
              "variance": 100.0,
            }
        },
        {
            "cityname": "Roma",
            "type": "cloudiness",
            "data": {
              "max": 80.0,
              "min": 30.0,
              "average": 55.0,
              "variance": 100.0,
            }
        }
    ]
  }
  ```
      "code": 0 se l'operazione va a buon fine|altro numero in caso di errori
      "info": descrizione dell'operazione|errore
      "time": tempo di elaborazione [ms]

- Endpoint per gli andamenti
  ***localhost:8080/trends***
  L'API deve essere richiamata con il metodo HTTP POST il cui body è un oggetto JSON con il seguente formato:
  ```json
    {
    "cities": [
         "Milano",
         "Roma"
      ],
    "period": {
      "from": "2020-12-14 10:15:00",
      "to": "2020-12-14 13:15:00"
    },
    "type": "cloudiness",
    "interval": 60
    }
  ```
      "type" può assumere i valori: [all|cloudiness|temperature|humidity]
      "interval" è espresso in minuti
  Per gli andamenti si ottiene come risposta il seguente JSON:
  ```json
  {
    "code": 0,
    "info": "Elapsed time 49ms",
    "time": 49,
    "result": [
        {
            "cityname": "Milano",
            "type": "cloudiness",
            "data": [
               {"datetime": "2020-12-14 10:15:00", "value": 10, "score": 0.94},
               {"datetime": "2020-12-14 11:15:00", "value": 12, "score": 0.91},
               {"datetime": "2020-12-14 12:15:00", "value": 11, "score": 0.92},
               {"datetime": "2020-12-14 13:15:00", "value": 14, "score": 0.90}
            ]
        },
        {
            "cityname": "Roma",
            "type": "cloudiness",
            "data": [
               {"datetime": "2020-12-14 10:15:00", "value": 8, "score": 0.89},
               {"datetime": "2020-12-14 11:15:00", "value": 10, "score": 0.93},
               {"datetime": "2020-12-14 12:15:00", "value": 7, "score": 0.91},
               {"datetime": "2020-12-14 13:15:00", "value": 5, "score": 0.95}
            ]
        }
    ]
  }
  ```
      "code": 0 se l'operazione va a buon fine|altro numero in caso di errori
      "info": descrizione dell'operazione|errore
      "time": tempo di elaborazione [ms]
  
- Endpoint per le configurazioni
  ***localhost:8080/config***
  L'API richiamata con il metodo HTTP GET ritorna le configurazioni attuali in formato JSON:
  ```json
    [
      "Milano",
      "Roma"
    ]
  ```
  SE l'API viene richiamata con il metodo HTTP POST, il JSON body contiene le configurazioni da settare (nello stesso formato di come vengono lette):
  ```json
    [
      "Milano",
      "Roma"
    ]
  ```
- Endpoint per consultare il database in maniera diretta
  *Per questa rotta è necessario utilizzare un browser*
  ***localhost:8080/h2***
  <p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/percorsi/percorso1.png?raw=true">
    <align="center">
        Collegandosi alla rotta specificata qui sopra si visualizza una pagina di login come questa, tutti i campi sono già precompilati come mostrato. Per procedere basta premere il tasto Connect
  </img>
  </p>
  &nbsp;
  &nbsp;
  <p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/percorsi/percorso2.png?raw=true">
    <align="center">
        Una volta entrati, cliccare su METEO, la tabella principale del database dove sono archiviati tutti i campioni
  </img>
  </p>
  &nbsp;
  &nbsp;
  <p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/percorsi/percorso3.png?raw=true">
    <align="center">
        Per poter visualizzare tutti i campioni, andare nella barra di controllo in alto alla voce "Max rows" e selezionare All nella tendina che si apre a fianco, altrimenti il numero massimo di righe visualizzabili sarà settato a 1000 di default
  </img>
  </p>
  &nbsp;
  &nbsp;
  <p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/percorsi/percorso4.png?raw=true">
    <align="center">
        Infine, per avere accesso ai dati grezzi raccolti nel database, premere sul tasto Run.
  </img>
  </p>

&nbsp;
Il sistema implementato è in grado di rispondere anche a richieste effettuate in maniera errata, come ad esempio campi del JSON body formattati non correttamente, parti di filtri mancanti, date non valide, ecc. Tale 'robustezza' è stata raggiunta grazie a sollevamento e gestione diffusa di eccezioni nel codice java.

# Strumenti software utilizzati
Per la realizzazione di questo applicativo sono stati utilizzati i seguenti strumenti:
- Il framework [Spring](https://spring.io), implementando nello specifico i moduli:
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Spring Web](https://spring.io/guides/gs/serving-web-content/)
  - [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-devtools)
  - [Spring H2 Database](https://howtodoinjava.com/spring-boot2/h2-database-example/)
- La libreria [JSON.simple](https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm) per la parsificazione, la lettura e la scrittura di oggetti e array in formato JSON
- [Apache Maven](https://maven.apache.org) per la gestione delle dipendenze sia di Spring che di JSONSimple
- [Javadoc](https://www.oracle.com/it/technical-resources/articles/java/javadoc-tool.html) per la generazione automatica della documentazione del codice sorgente scritto in linguaggio Java
- Il framework [JUnit5](https://junit.org/junit5/) per lo unit testing
- L'applicativo [Postman](https://www.postman.com) per richiamare e testare le API esposte dal nostro servizio
- L'IDE [Visual Studio Code](https://code.visualstudio.com) per l'editing e il rendering in tempo reale del documento di markdown per il README
- L'applicativo [Visual Paradigm](https://www.visual-paradigm.com) per la modellazione e la generazione dei diagrammi UML
- I sistemi [Git](https://git-scm.com) e [GitHub](https://github.com) per il versioning del codice
- L'IDE [Eclipse](https://www.eclipse.org/downloads/) per lo sviluppo di tutto l'applicativo scritto in java

Per evidenziare e accentuare la portabilità e il concetto di sviluppo multipiattaforma i due autori hanno utilizzato gli stessi strumenti, uno su sistema MacOs, l'altro su Windows10.

# Autori
- [Matteo Lorenzo Bramucci](https://github.com/Matteo-Lorenzo) - Contributo 50%
- [Agnese Bruglia](https://github.com/AgneseBruglia) - Contributo 50%