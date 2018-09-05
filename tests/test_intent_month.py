from rasa_nlu.model import Metadata, Interpreter
import pytest
import os
path = "./models/chatbot/" + next(os.walk('./models/chatbot'))[1][0] + "/"
interpreter = Interpreter.load(path)


def get_intent_name(text):
    return interpreter.parse(text)['intent']['name']


def test_only_month():
    assert get_intent_name("januar") == "month"
    assert get_intent_name("april") == "month"
    assert get_intent_name("13") != "month"


def test_month_with_text():
    assert get_intent_name("Im Monat januar") == "month"
    assert get_intent_name("BLUB BLA BLIE") != "month"


def test_month_entity():
    intent = interpreter.parse("november")
    assert intent['intent']['name'] == "month"
    assert intent['entities'][0]['value'] == "november"


def test_month_confidence():
    intent = interpreter.parse("juli")
    assert intent['intent']['name'] == "month"
    assert intent['intent']['confidence'] > 0.75
