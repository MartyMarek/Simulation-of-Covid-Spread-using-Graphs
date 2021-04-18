# -*- coding: utf-8 -*-
"""
Created on Sun Apr 18 13:41:29 2021

@author: Andrew Buttery

Generate a matrix of plots showing performance of graph represenatations in 
simulations.

"""

import pandas as pd
from matplotlib import pyplot as plt
import numpy as np


# Load the data.
df_sim = pd.read_excel('Simulation Data (Tidy).xlsx')

# Some summary stats.
df_sim_overall_avr = df_sim[['Graph Type', 'running time', 'total infections reached']].groupby(['Graph Type']).mean()
df_sim_overall_max = df_sim[['Graph Type', 'running time', 'total infections reached']].groupby(['Graph Type']).max()
df_sim_overall_min = df_sim[['Graph Type', 'running time', 'total infections reached']].groupby(['Graph Type']).min()

# Extract and group data for plotting.
df_chart = df_sim[['Graph Type', 'Graph Representation', 'num of vertices', 'number of degrees', 'running time']]
df_chart = df_chart.groupby(['Graph Type', 'number of degrees', 'num of vertices', 'Graph Representation' ]).mean()

# Set up various lists and variables for iteration.
generator = ["Erdos Renyi", "Scale Free"]
method_name = ["Adjacency List", "Adjacency Matrix", "Incidence Matrix"]
graph_size = [500, 5000, 10000]
degree_labels = ['2d', '8d', '16d']
degrees = [2, 8, 16]
x = np.arange(len(method_name))  # The label locations.

# Generate charts.
for g in generator:
    fig, axes = plt.subplots(nrows=3, ncols=3, sharey='row', figsize=(12, 8))
    
    for ax, col in zip(axes[2], degree_labels):
          ax.set_xlabel(col)
    
    for ax, row in zip(axes[:,0], graph_size):
        ax.set_ylabel(row, rotation=90)
    
    for mc in range(1, 4):
        for mr in range(1, 4):
            sp = axes[mc-1, mr-1]
            df_temp = df_chart.loc[(g, degrees[mr-1], graph_size[mc-1])]
            sp.bar(x, df_temp['running time'].values, color=['orange', 'blue', 'green'])        
            sp.set_xticks(x)
            sp.set_xticklabels(method_name, fontsize=8)
    
    plt.suptitle(g + " - average time across all runs.")
    plt.tight_layout()
    
    plt.savefig(g + "_avr_time_sim_matrix_chart", bbox_inches='tight')
    #plt.show()

