FROM rasa/rasa_nlu:latest-full

WORKDIR  /etc/chatbot

RUN mkdir ./models
COPY . /nlu
WORKDIR /nlu
RUN python ./src/train_nlu.py

EXPOSE 5000

ENTRYPOINT exec python -m rasa_nlu.server --path models/ -c config.yaml --pre_load all