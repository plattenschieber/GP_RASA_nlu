from rasa_nlu.model import Metadata, Interpreter
import pytest
import os
path = "./models/default/" + next(os.walk('./models/default'))[1][0] + "/"
interpreter = Interpreter.load(path)


def get_intent_name(text):
    return interpreter.parse(text)['intent']['name']


def test_greet():
    assert get_intent_name("hi") == "greet"
    assert get_intent_name("Hallo") == "greet"
