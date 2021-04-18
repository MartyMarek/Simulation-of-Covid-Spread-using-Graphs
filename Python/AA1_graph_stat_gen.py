# -*- coding: utf-8 -*-
"""
Created on Sat Apr 17 16:48:25 2021

@author: Andrew Buttery

Pull in all the generated graphs and assemble stats for report.
"""

from igraph import *
import pandas as pd

# Set up various lists and variables for iteration.
graph_stat_list = []

# Read in the graph files and calculate stats. 
for gen in ["er", "sf"]:
    for s in range(1,28):
        g = load("graphs/" + gen + str(s) +".net")
        # Work out it's sequence number (i.e. set of three graphs).
        config = int((s-1)/3)
        degree_cat = config % 3
        if degree_cat == 0:
            degrees = 2
        elif degree_cat == 1:
            degrees = 8
        else:
            degrees = 16
        
        # Generator type.
        if gen == "er":
            generator = "Erdos Renyi"
        else:
            generator = "Scale Free"

        # Number and precentage of isolated vertices.
        isloated = g.vs.select(_degree_eq=0)
        i = 0
        for v in isloated:
            i += 1
        i_pct = (i/g.vcount())*100

        graph_stat_list.append([gen + str(s) +".net", str(g.vcount()), str(g.ecount()), str(config), str(degrees), generator, str(i), str(i_pct)])            

# Write out the stats!
deg_file = open('graph_stat.csv','w')
deg_file.write("graph_ID,vertices,edges,config,degrees,generator,isolated,pct_isolated\n")
for element in graph_stat_list:
     deg_file.write(element[0] + "," + element[1] + "," + element[2] + "," + element[3] + "," + element[4] + "," + element[5] + "," + element[6] + "," + element[7] +"\n")
deg_file.close()          
