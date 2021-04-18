# -*- coding: utf-8 -*-
"""
Created on Sat Apr 17 10:17:28 2021

@author: Andrew Buttery

Generate a series of plots showing performance of khop operations
across three graph representations and two graph generations.
"""

import pandas as pd
import matplotlib.pyplot as plt
from num2words import num2words
import numpy as np


# Load the data.
df_all = pd.read_csv('khop_results_all.csv')

# Extract and group data for plotting.
df_avr_results = df_all.groupby(['generator', 'khop', 'config', 'method']).mean()
df_avr_results.index.get_level_values(0)

# Set up various lists and variables for iteration.  
xlabel = ['500v 2d', '500v 8d', '500v 16d', '5000v 2d', '5000v 8d', '5000v 16d', '10000v 2d', '10000v 8d', '10000v 16d']
method_name = ["Adjacency List", "Adjacency Matrix", "Incident Matrix"]
method_code = ["adjlist", "adjmat", "incmat"]
generator = ["Erdos Renyi", "Scale Free"]
width = 0.28

# Generate charts.
for g in generator:
    for khop in range(2,5):
        df_temp = df_avr_results.loc[(g, khop)]
        
        fig, ax = plt.subplots()
        x = np.arange(len(xlabel))  # The label locations.
        
        for method in range(0, 3):
            df_method = df_temp.filter(like=method_code[method], axis=0)
            ax.bar(x-width+(width*method), df_method['result'].values, width)
        
        ax.set_xticks(x)
        ax.set_xticklabels(xlabel, rotation=45, fontsize=8)
        plt.title(g + " - khop of " + num2words(khop).capitalize())
        plt.legend(method_name)
        plt.ylabel("Seconds")
        plt.xlabel("Graph Size (Vertice / Degrees)")
        
        save_path = "charts/khop_by_method/" + g + "_" + str(khop) + "_chart"        
        plt.savefig(save_path, bbox_inches='tight')
        # plt.show()
