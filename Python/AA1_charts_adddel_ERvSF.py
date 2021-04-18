# -*- coding: utf-8 -*-
"""
Created on Sat Apr 17 10:17:28 2021

@author: Andrew Buttery

Generate a series of plots showing performance of vertex and edge addition 
and deletion with three graph representations and two graph generations.

"""

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


# Load the data.
df_all = pd.read_csv('add_delete_results_all.csv')

# Extract and group data for plotting.
df_avr_results = df_all.groupby(['generator', 'method', 'operation', 'type', 'config']).mean()
    
# Set up various lists and variables for iteration.
xlabel = ['500v 2d', '500v 8d', '500v 16d', '5000v 2d', '5000v 8d', '5000v 16d', '10000v 2d', '10000v 8d', '10000v 16d']
method_name = ["Adjacency List", "Adjacency Matrix", "Incident Matrix"]
method_code = ["adjlist", "adjmat", "incmat"]
width = 0.35

# Generate charts. This was the first one I did - I got better.
for method in range(0, 3):
     for type in ["vertex", "edge"]:
        for operator in ["addition", "deletion"]:
            df_temp = df_avr_results.filter(like='Erdos', axis=0)
            df_temp = df_temp.filter(like=method_code[method], axis=0)
            df_temp = df_temp.filter(like=type, axis=0)
            df_temp = df_temp.filter(like=operator, axis=0)
            
            df_temp_2 = df_avr_results.filter(like='Scale', axis=0)
            df_temp_2 = df_temp_2.filter(like=method_code[method], axis=0)
            df_temp_2 = df_temp_2.filter(like=type, axis=0)
            df_temp_2 = df_temp_2.filter(like=operator, axis=0)
            
            fig, ax = plt.subplots()
            x = np.arange(len(xlabel))  # The label locations.
            
            ax.bar(x - width/2, df_temp['result'].values, width)
            ax.bar(x + width/2, df_temp_2['result'].values, width)
            ax.set_xticks(x)
            ax.set_xticklabels(xlabel, rotation=45, fontsize=8)
            plt.title(method_name[method] + " - Erdos Renyi v Scale Free " + type.capitalize() + " " + operator.capitalize())
            plt.legend(["Erdos Renyi", "Scale Free"])
            plt.ylabel("Seconds")
            plt.xlabel("Graph Size (Vertice / Degrees)")
            
            save_path = "charts/adddel_by_generator/" + method_code[method] + "_" + type + "_" + operator + "_chart"
            plt.savefig(save_path, bbox_inches='tight')
            # plt.show()
 

