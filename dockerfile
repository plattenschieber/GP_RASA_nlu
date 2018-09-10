FROM python:3.6

WORKDIR  /etc/chatbot

RUN pip install --upgrade setuptools && pip install scipy sklearn sklearn_crfsuite tensorflow rasa_nlu && mkdir ./models

COPY config.yaml .
COPY models ./models

EXPOSE 5000

ENTRYPOINT exec python -m rasa_nlu.server --path models/ -c config.yaml --pre_load default