FROM python:3.6

WORKDIR  /etc/chatbot

RUN pip install --upgrade setuptools
RUN pip install scipy sklearn sklearn_crfsuite tensorflow spacy rasa_nlu
RUN python -m spacy download de_core_news_sm
RUN python -m spacy link de_core_news_sm de
RUN mkdir ./models

COPY . /nlu
WORKDIR /nlu
RUN python ./src/train_nlu.py

EXPOSE 5000

ENTRYPOINT exec python -m rasa_nlu.server --path models/ -c config.yaml --pre_load default