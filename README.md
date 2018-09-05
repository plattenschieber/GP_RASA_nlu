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
python train_nlu.py
```

### Installation

Zur Installation empfiehlt sich den offiziellen Anweisungen zu folgen, diese sind unter [NLU Installation](http://www.rasa.com/docs/nlu/installation/) zu finden.

## Testen

Zum Testen müssen im Ordner *tests*, Tests erstellt werden. Es empfiehlt sich für jeden Intent ein eigenes Testfile anzulegen. Wichtig dabei ist, dass die Files mit *test_* beginnen müssen.
```bash
py.test
```