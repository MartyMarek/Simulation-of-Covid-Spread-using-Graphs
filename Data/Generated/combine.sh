#!/usr/bin/bash

cat k1list.out k2list.out k3list.out k4list.out k5list.out k6list.out k7list.out k8list.out k9list.out k10list.out k11list.out k12list.out k13list.out k14list.out k15list.out k16list.out k17list.out k18list.out k19list.out k20list.out k21list.out k22list.out k23list.out k24list.out k25list.out k26list.out k27list.out > klistresult.out

cat k1mat.out k2mat.out k3mat.out k4mat.out k5mat.out k6mat.out k7mat.out k8mat.out k9mat.out k10mat.out k11mat.out k12mat.out k13mat.out k14mat.out k15mat.out k16mat.out k17mat.out k18mat.out k19mat.out k20mat.out k21mat.out k22mat.out k23mat.out k24mat.out k25mat.out k26mat.out k27mat.out > kmatresult.out

cat k1inc.out k2inc.out k3inc.out k4inc.out k5inc.out k6inc.out k7inc.out k8inc.out k9inc.out k10inc.out k11inc.out k12inc.out k13inc.out k14inc.out k15inc.out k16inc.out k17inc.out k18inc.out k19inc.out k20inc.out k21inc.out k22inc.out k23inc.out k24inc.out k25inc.out k26inc.out k27inc.out > kincresult.out

unix2dos klistresult.out klistresult.txt
unix2dos kmatresult.out kmatresult.txt
unix2dos kincresult.out kincresult.txt
