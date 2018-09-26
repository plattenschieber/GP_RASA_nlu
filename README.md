# Rasa-NLU
Projekt zum erstellen eines Modells, welches natürliche Sprache erkennt und diese in intents umwandelt.

## Dateien
* *intents* enthält die Trainingsdaten für alle Intents
* *config.yaml* beschreibt die Konfiguration des NLU
* *models* enthält die generierten Modelle
* *tests* Enthält alle Testscripte
* *train_nlu.py* Script zum Trainieren des NLU mit allen in *intents* befindlichen Dateien


## Getting Started

Es reicht den *intents* Ordner mit neuen Intents zu füllen und danach das Training zu starten.
```bash
python src/train_nlu.py
```
Zum lokalen starten eines http-server kann folgender Befehl verwendet werden:
```bash
python -m rasa_nlu.server --path models/ -c config.yaml
```
Eine Nachricht kann an den Server mit curl geschickt werden.
```bash
curl -XPOST localhost:5000/parse -d '{"q":"hallo"}'
```
Und Informationen hat der Server auf folgenden adressen:
* /config
* /status

### Installation

Zur Installation empfiehlt sich den offiziellen Anweisungen zu folgen, diese sind unter [NLU Installation](http://www.rasa.com/docs/nlu/installation/) zu finden.
Zusätzlich steht einen requirements.txt File bereit. diese kann installiert werden, so werden alle benötigten Packete direkt installiert.
```bash
pip install -r requirements.txt

```
Zusätzlich muss für spacy noch das deutsche Sprachpaket installiert werden mit
```bash
python -m spacy download de
```

## Testen

Zum Testen müssen im Ordner *tests*, Tests erstellt werden. Es empfiehlt sich für jeden Intent ein eigenes Testfile anzulegen. Wichtig dabei ist, dass die Files mit *test_* beginnen müssen.
```bash
py.test
```
## Docker
Diesem Projekt liegt eine Dockerfile und ein Docker-Compose bei, diese stellen das Projekt als Docker-Container zu Verfügung.
Um das Image zu bauen und zu starten müssen die folgenden Befehle ausgeführt werden.

```bash
docker build -t docker.nexus.gpchatbot.archi-lab.io/chatbot/nlu .
docker-compose -p gpb -f docker/docker-compose.yaml up -d
```
