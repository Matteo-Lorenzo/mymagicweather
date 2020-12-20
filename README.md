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
Il progetto consiste nell’implementazione di un servizio meteo che, a seconda dell’API scelta:
- generi delle statistiche riguardanti i valori minimi, massimi, media e varianza della quantità di nuvolosità nel periodo e nella località indicati;
- visualizzi tutte le informazioni, attuali oppure riguardanti uno storico di dati, relative alla nuvolosità nel periodo e nella località indicati;

# Funzionamento interno
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwUseCase.jpg?raw=true">
    <h6 align="center">
        Interazioni tra i diversi agenti che costituiscono il sistema
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

#### Modo di utilizzo degli endpoint dell'API di MyMagicWeather

- Endpoint per le statistiche
  http://mymagicweather/stats
  L'API deve essere richiamata con il metodo HTTP POST il cui body è un oggetto JSON con il seguente formato:
  ```json
    {
      "cities": [
         "Milano",
         "Roma"
      ],
      "period": {
        "from": "2020-12-02T09:00:00",
        "to": "2020-12-05T09:00:00"
      },
      "type": "cloudiness" // [all|cloudiness|temperature|humidity]
    }
  ```
  Per le statistiche si ottiene come risposta il seguente JSON:
  ```json
  {
    "code": 0, // 0 se l'operazione va a buon fine|altro numero se vengono riscontrati errori
    "info": "Required time: 429 milliseconds", //oppure stringa con descrizione dell'errore riscontrato
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

- Endpoint per gli andamenti
  http://mymagicweather/trends
  L'API deve essere richiamata con il metodo HTTP POST il cui body è un oggetto JSON con il seguente formato:
  ```json
    {
    "cities": [
         "Milano",
         "Roma"
      ],
    "period": {
      "from": "2020-12-02T10:15:00",
      "to": "2020-12-02T13:15:00"
    },
    "type": "cloudiness", // [all|cloudiness|temperature|humidity]
    "interval": 60 // minutes
    }
  ```
  Per gli andamenti si ottiene come risposta il seguente JSON:
  ```json
  {
    "code": 0, // 0 se l'operazione va a buon fine|altro numero se vengono riscontrati errori
    "info": "Required time: 329 milliseconds", //oppure stringa con descrizione dell'errore riscontrato
    "result": [
        {
            "cityname": "Milano",
            "type": "cloudiness",
            "data": [
               {"datetime": "2020-12-02T10:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-02T11:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-02T12:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-02T13:15:00", "value": 10, "score": "A"}
            ]
        },
        {
            "cityname": "Roma",
            "type": "cloudiness",
            "data": [
               {"datetime": "2020-12-10T10:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-10T11:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-10T12:15:00", "value": 10, "score": "A"},
               {"datetime": "2020-12-10T13:15:00", "value": 10, "score": "A"}
            ]
        }
    ]
  }
  ```
  
- Endpoint per le configurazioni
  http://mymagicweather/config
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

È stato implementato un archivio per raccogliere i dati storici, acquisiti nel tempo, tramite chiamate al data source online di OpenWeather.
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

L'archivio viene popolato in maniera automatica grazie all'acquisizione temporizzata, che, a intervalli prefissati, chiama l’API di OpenWeather per acquisire le informazioni riguardanti la nuvolosità per le città preimpostate, relative al momento in cui si effettua la chiamata.

# Finalità del progetto
I possibili utilizzatori di questo servizio sono Aziende il cui business è in qualche modo legato alle condizioni meteo, soprattutto alla presenza o meno di nuvole.
Ad esempio: aziende produttrici di tende e tendaggi da sole, ombrelli e ombrelloni, agricoltori 2.0, parchi a tema, ecc... :-)

# Diagrammi UML
<p>
  <img src="https://github.com/Matteo-Lorenzo/mymagicweather/blob/main/mmwUseCase.jpg?raw=true">
    <h6 align="center">
        Use Case Diagram
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
//TO DO

# Strumenti software utilizzati
//TO DO
