#!/usr/bin/env python
# coding: utf-8

import sys
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
stderr = sys.stderr
sys.stderr = open(os.devnull, 'w')
import requests 
import numpy as np
import random
import time
import tensorflow as tf
from keras.models import Sequential
from keras.layers import Dense, Activation, Dropout
from keras.optimizers import SGD, Adam, RMSprop
import pickle
tf.keras.backend.clear_session
from collections import deque



#Create Environment
#State - CPU, MEM, #of pods, latency, bandwith (5 metrics)
class DB_Cluster:
    def __init__(self, cpu, mem, pod, latency, bandwidth):
        #get_data_from_DB (microserviceid, timestamp)
        self.cpu = int(cpu)
        self.mem = int(mem)
        self.pod = int(pod)
        self.latency = int(latency)
        self.bandwidth = int(bandwidth)
        self.state = [self.cpu, self.mem, self.pod, self.latency, self.bandwidth]
        #self.state = np.asarray([self.cpu, self.mem, self.pod])
    def update(self, action):
        old_pod = self.pod
        if action == 0:
            pass
        elif action == 1:
            self.pod= self.pod + 1
        elif action == 2:
            self.pod = self.pod - 1
        new_state, reward = self.random_updates(old_pod)
        return new_state, reward


    def random_updates(self,old_pod):
        self.state[0] = ((self.state[0]  * old_pod / self.pod))
        self.state[1] = ((self.state[1] * old_pod / self.pod))
        self.state[2] = self.pod
        self.state[3] = ((self.state[3] * old_pod / self.pod))
        self.state[4] = ((self.state[4] * self.pod / old_pod))
        #Get new state from DB
        reward = self.calculate_reward()
        return self.state, reward

    def calculate_reward(self):
        cpu_scores = 0
        mem_scores = 0
        latency_scores = 0
        bandwith_scores = 0
        pod_scores = 30 - (self.pod * 4) # Less allocated resources is better but getting overload is the worst.
        if (self.state[0]) < (95): cpu_scores += 15 #CPU Usage below 95% is good
        if (self.state[1]) < (95): mem_scores += 15 #Mem Usage below 95% is good
        if (self.state[2]) < 1000: # Latency below 1000 ms is really good
            latency_scores += 15
        elif (self.state[2]) < 3000: # Latency below 3000 ms is ok
            latency_scores += 10
        if (self.state[3]) > 50: bandwith_scores += 15 #Available bandwidth > 50 mbps is good
        reward = cpu_scores + mem_scores + latency_scores + bandwith_scores
        return reward


# In[42]:


#Agent
class LoadPrediction:
    def __init__(self, learning_rate, discount, exploration_rate, tau, iterations, batch_size):
        self.learning_rate = learning_rate
        self.discount = discount
        self.exploration_rate = exploration_rate # Initial exploration rate
        self.exploration_delta = 1.0 / iterations # Shift from exploration to explotation
        self.tau = tau
        self.batch_size = batch_size
        # Input has five neurons, each represents a single metric
        self.input_count = 5
        # Output is three neurons, each represents Q-value for action (do nothing, spawn a pod, remove a pod)
        self.output_count = 3
        self.model = self.define_model()
        self.target_model = self.define_model()
        self.memory  = deque(maxlen=2000)
    def switch_to_exploit(epsilon):
        self.exploration_rate = epsilon

    def define_model(self):
        #Tensorflow Model
        #input is 5 metrics
        #input layer
        model = Sequential()
        model.add(Dense(self.input_count, input_dim= self.input_count, kernel_initializer='glorot_uniform', activation="linear"))
        #hidden layer
        model.add(Dense(15, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(30, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(60, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(90, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(90, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(60, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(30, activation='relu', kernel_initializer='glorot_uniform'))
        model.add(Dense(15, activation='relu', kernel_initializer='glorot_uniform'))

        #Model output layer
        model.add(Dense(self.output_count, kernel_initializer='glorot_uniform', activation='linear'))

        #Ideal Target Values
        #self.target_output = tf.Variable(np.random.random([1, self.output_count]))

        #loss function and optimizer
        opt = Adam(lr=0.0001)
        model.compile(loss='mean_squared_error', optimizer=opt)
        return model

    #Get the next action
    def next_action(self, state):
        if random.random() > self.exploration_rate:
            self.exploration_rate = self.exploration_rate - self.exploration_delta
            return self.best_action(state)
        else:
            return self.random_action()

    # do a random action
    def random_action(self):
        return random.randint(0,2)

    def best_action(self, state):
        return np.argmax(self.model.predict(np.reshape(state,(self.input_count,1)).T))

    def remember(self, old_state, action, reward, new_state):
        self.memory.append([old_state, action, reward, new_state])

    def replay(self):
        batch_size = 32
        if len(self.memory) < batch_size:
            return
        samples = random.sample(self.memory, batch_size)
        for sample in samples:
            state, action, reward, new_state = sample
            state = (np.reshape(state,(self.input_count,1)).T)
            new_state = (np.reshape(new_state,(self.input_count,1)).T)
            target = self.target_model.predict(state)
            Q_future = max(self.target_model.predict(new_state)[0])
            target[0][action] = reward + Q_future * self.discount
            self.model.fit(state, target, epochs=1, verbose=0)

    def target_train(self):
        weights = self.model.get_weights()
        target_weights = self.target_model.get_weights()
        for i in range(len(target_weights)):
            target_weights[i] = weights[i] * self.tau + target_weights[i] * (1 - self.tau)
        self.target_model.set_weights(target_weights)
        return self.target_model.get_weights(), self.model.get_weights()


def save_model(agent, model_name):
    with open(model_name, 'wb') as output:
        pickle.dump(agent, output)


# In[ ]:
def update(cpu, mem, pod, latency, bandwidth, cpu2, mem2, pod2, latency2, bandwidth2, action):
    #Load Model
    #print ("MicroserviceId:%s, timestamp:%s" %(microserviceid, timestamp))
    model_name = 'Load_Prediction_Model.pkl'
    agent = pickle.load(open( model_name, 'rb'))
    #initialize Environment
    environment = DB_Cluster(cpu, mem, pod, latency, bandwidth)
    old_state = environment.state
    # This line exists for debugging and testing
    #action = agent.next_action(old_state)
    result_environment = DB_Cluster(cpu2, mem2, pod2, latency2, bandwidth2)
    new_state = environment.state
    action_pred = agent.next_action(old_state)

    # Sending prediction to Container Management System
    #r = requests.post("http://127.0.0.1:5000/cms", json={'microserviceid': str(microserviceid), 'LPSresult': str(action)})
    #time.sleep(300) # Wait a predetermined time, default 5 minutes

    # Get updates from DB
    reward = result_environment.calculate_reward()
    agent.remember(old_state, action, reward, new_state)
    agent.replay()
    target, model = agent.target_train()
    # Safe the updated model
    ## model_name = 'Load_Prediction_Model_result.pkl'
    save_model(agent, model_name)
    return ("Model Updated")

# Prediction with microservice id and timestamp
print(update(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6], sys.argv[7], sys.argv[8], sys.argv[9], sys.argv[10], sys.argv[11]))





