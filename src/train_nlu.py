from rasa_nlu import config
from rasa_nlu.model import Trainer
from rasa_nlu.training_data import load_data


def train(data='./intents', config_file='config.yaml', model_dir='models/', project='default'):
    """ Trains the nlu on given config and data

    Parameters:
    ----------
    data: str
          path to training data
    config_file: str
                 path to config file
    model_dir: str
               path ot output model directory
    project: str
             name of the project, needed for model generation

    """
    training_data = load_data(data)
    trainer = Trainer(config.load(config_file))
    trainer.train(training_data)
    trainer.persist(
        model_dir, project_name=project)


train(project="damage_report_1.0.0")
