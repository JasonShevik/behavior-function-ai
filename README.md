# Continuous Behavior AI
This is a framework for creating [Reinforcement Learning](https://en.wikipedia.org/wiki/Reinforcement_learning) agents to output behavior functions, capable of desribing nearly any behavior over a specified time period.
A [Neural Network](https://en.wikipedia.org/wiki/Neural_network) will trained to output the coefficients for the Sine-Cosine form of multiple [Fourier Series'](https://en.wikipedia.org/wiki/Fourier_series#Common_forms_of_the_Fourier_series) enabling it to approximate any collection of continuous one-dimensional behaviors over a specified period of time.
For example, an agent designed to drive a vehicle with 3 binary actions—turn the steering wheel, press the gass, and press the brakes—would output the coefficients for 3 different Fourier series' which describe smooth and continuous behaviors for all 3 actions. A Fourier series with enough terms can approximate *any* smooth, continuously differentiable function for a finite time period.

This project is in the early stages. I originally planned to write it in Clojure, but I have decided to switch the project over to Python. I would still like to eventually implement it in Clojure.

## Table of contents
- Training
- Processing the output

### Training
The model will be trained using reinforcement learning, but this training will be done in phases. The model will be trained starting with only two or three output nodes per series to make learning easier. As the model plateaus in performance, additional coefficients will be added, allowing the model to slowly learn more complex behaviors.

### Processing the output
Each additional output node of a network significantly increases the complexity of the model, so it is necessary to limit the number of outputs when a loss in precision is acceptable. To avoid 'bumpiness' when using a low number of output nodes, a smoothing function such as a running average will be used.
