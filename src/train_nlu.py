from rasa_nlu.training_data import load_data
from rasa_nlu.model import Trainer
from rasa_nlu import config
import datetime

def train(data = './intents', config_file = 'config.yaml', model_dir = 'models/', project = 'default'):
    training_data = load_data(data)
    trainer = Trainer(config.load(config_file))
    trainer.train(training_data)
    model_directory = trainer.persist(
        model_dir, project_name=project)

train(project="damage_report_1.0.0")
