# -*- coding: utf-8 -*-
"""
Created on Sat Apr 17 10:17:28 2021

@author: Andrew Buttery

Generate a series of plots showing performance of khop operations
across three graph representations and two graph generations.
"""

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from num2words import num2words


# Load the data.
df_all = pd.read_csv('khop_results_all.csv')

# Extract and group data for plotting.
df_avr_results = df_all.groupby(['generator', 'khop', 'method', 'config']).mean()

# Set up various lists and variables for iteration.  
xlabel = ['500v 2d', '500v 8d', '500v 16d', '5000v 2d', '5000v 8d', '5000v 16d', '10000v 2d', '10000v 8d', '10000v 16d']
method_name = ["Adjacency List", "Adjacency Matrix", "Incidence Matrix"]
method_code = ["adjlist", "adjmat", "incmat"]
g = ["Erdos Renyi", "Scale Free"]
width = 0.35

# Generate charts.
for method in range(0, 3):
    for khop in range(2,5):
        df_temp = df_avr_results.loc[(g[0], khop, method_code[method])]
        df_temp_2 = df_avr_results.loc[(g[1], khop, method_code[method])]
        
        fig, ax = plt.subplots()
        
        x = np.arange(len(xlabel))  # The label locations.
        ax.bar(x - width/2, df_temp['result'].values, width)
        ax.bar(x + width/2, df_temp_2['result'].values, width)
        
        ax.set_xticks(x)
        ax.set_xticklabels(xlabel, rotation=45, fontsize=8)
        plt.title(method_name[method] + " - Erdos Renyi v Scale Free - khop of " + num2words(khop).capitalize())
        plt.legend(["Erdos Renyi", "Scale Free"])
        plt.ylabel("Seconds")
        plt.xlabel("Graph Size (Vertice / Degrees)")
        
        save_path = "charts/khop_by_generator/" + method_code[method] + "_" + str(khop) + "_chart"
        plt.savefig(save_path, bbox_inches='tight')
        # plt.show()

 

