FROM python:3.6

WORKDIR  /etc/chatbot

COPY requirements.txt .
RUN pip install --upgrade setuptools && pip install sklearn sklearn_crfsuite tensorflow rasa_nlu && mkdir ./models

COPY config.yaml .
COPY models ./models

EXPOSE 5000

ENTRYPOINT exec python -m rasa_nlu.server --path models/ -c config.yaml --pre_load default