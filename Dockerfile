FROM rasa/rasa_nlu:latest-full

WORKDIR  /etc/chatbot
# Create model dir
RUN mkdir ./models

# Copy the whole project to image
COPY . /nlu

# train the model
WORKDIR /nlu
RUN python ./src/train_nlu.py

EXPOSE 5000

ENTRYPOINT exec python -m rasa_nlu.server --path models/ -c config.yaml --pre_load all