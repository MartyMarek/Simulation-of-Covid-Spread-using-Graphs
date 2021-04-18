# -*- coding: utf-8 -*-
"""
Created on Sat Apr 10 11:52:13 2021

@author: Andrew Buttery

Generate Erdos Renyi graphs based on a range of sizes and probabilities.
"""

from igraph import *


# Set up various lists and variables for iteration.
vertices = [500, 5000, 10000]
probabilty = [0.0001, 0.0008, 0.0016] 
degree_list = []

# Generate graphs.
for v in vertices:
    for p in probabilty:
        for r in range(1, 4):
            print("Generatating Erdos Renyi graph with " + str(v) + " vertices and " + str(p) + " probability of an edge.")
            g = Graph.Erdos_Renyi(v, p)
            
            # Calculate the degrees (average number of edges).
            deg_sum = 0
            for d in range(v):
                deg_sum += g.vs[d].degree()
            deg_avr = deg_sum / v

            # print("Total vertices : " + str(v) + " Total edges : " + str(deg_sum) + " Degree:" + str(deg_avr))
            g.write("ER-" + str(v) + "-" + str(p) + ".in", format="pajek")
            
            # For some reason igraph does not save the verex labels. Create new file adding labels.
            with open("ER-" + str(v) + "-" + str(p) + ".in") as f_old, open("ER-" + str(v) + "v-" + str(p) + "n-" + str(r) + ".NET", "w") as f_new:
                for line in f_old:
                    f_new.write(line)
                    if '*Vertices' in line:
                        for n in range(1, v+1): 
                            f_new.write(str(n) + '\t"v' + str(n) + '"\n')

            degree_list.append(["ER-" + str(v) + "v-" + str(p) + "n-" + str(r) + ".NET", str(v), str(p), str(deg_sum), str(deg_avr)])

# Write out a .csv with stats of the generated graphs.
# TODO: Include isolated vertex count.
deg_file = open('ER-degrees.csv','w')
deg_file.write("graph_file, vertices, probability, edges, degree\n")
for element in degree_list:
     deg_file.write(element[0] + ", " + element[1] + ", " + element[2] + ", " + element[3] + ", " + element[4] + "\n")
deg_file.close()          
